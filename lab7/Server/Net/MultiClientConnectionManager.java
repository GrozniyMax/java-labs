package Net;


import ClientLogic.ClientClasses.Client;
import ClientLogic.ClientFactory;
import CommonClasses.Exceptions.FunctionFailedException;
import CommonClasses.Interaction.*;
import CommonClasses.Utils.IOUtils;
import CommonClasses.Utils.Pair;
import DataBase.DBController;
import Exceptions.ExitCommandException;
import Exceptions.NoAutentificationException;
import Main.Main;
import Managers.CommandManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Класс, управляющий подключениями клиентов
 */
public class MultiClientConnectionManager {
    static final Logger logger = Main.logger;
    private CommandManager baseCommandManager;
    private DatagramChannel serverChannel;
    private ClientFactory factory;

    private Map<SocketAddress, Client> clients = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Pair<Client,Serializable>> reponcesToSend = new ConcurrentLinkedQueue<>();
    static final int PORT = 8080;

    static final int checkEveryMillis = 15 * 60 * 1000;

    static final long cleanEveryMillis = 60*60*1000;


    /**
     * Конструктор
     *
     * @param commandManager - менеджер команд
     */
    public MultiClientConnectionManager(CommandManager commandManager) {
        try {
            this.baseCommandManager = commandManager;
            InetSocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
            this.serverChannel = DatagramChannel.open().bind(s);
            this.serverChannel.configureBlocking(false);
            this.factory = new ClientFactory(baseCommandManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Обработчик нового пользователя(Того, который регистрируется)
     */
    private class NewUserHandle implements Callable<Object>{

        private Client client;
        private UserAuthData authData;

        public NewUserHandle(UserAuthData request, SocketAddress currentAddr) {
            client = factory.createClient(currentAddr);
            authData = request;
        }


        @Override
        public Boolean call() throws Exception {
            try {
                DBController.getInstance().insertUser(authData);
                clients.put(client.getAddress(),client);
                return reponcesToSend.add(new Pair<>(client,client.getAvailableCommands()));
            }catch (Exception e){
                //TODO specify exception
                return reponcesToSend.add(new Pair<>(client,new ServerResponse(new FunctionFailedException(e.getMessage()))));
            }
        }
    }

    /**
     * Запускает менеджер
     */
    public void run() {
        Thread console = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        if (reader.ready()) {
                            String adminCommand = reader.readLine();
                            if (adminCommand.strip().equals("save")) {
                                logger.info("Read command: save from admin");
                            } else if (adminCommand.strip().equals("exit")) {
                                System.exit(0);
                            }
                        }
                    } catch (IOException e) {
                        logger.warning("Unable to save collention");
                    }
                }
            }
        });
        console.start();


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DBController.getInstance().cleanDatabase();
                logger.info("Cleaned collection");
            }
        },
                cleanEveryMillis,
                cleanEveryMillis);


        //TODO add localization
        logger.info("Started waiting for packages");

        ExecutorService cachedTreadPool = Executors.newCachedThreadPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ByteBuffer readBuffer = ByteBuffer.allocate(4096);
        SocketAddress currentClientAddr = null;
        while (true) {
            try {
                readBuffer.clear();
                currentClientAddr = serverChannel.receive(readBuffer);
                if (currentClientAddr!=null){
                    Object readObj = IOUtils.fromByteArray(readBuffer.array());
                    logger.info("Recieved packet from " + currentClientAddr.toString());
                    if (readObj.getClass().equals(UserAuthData.class)) {
                        cachedTreadPool.submit(new LoginingUser((UserAuthData) readObj,currentClientAddr));
                    } else if (readObj.getClass().equals(UserRequest.class)) {
                        cachedTreadPool.submit(new RequestHande((UserRequest) readObj,currentClientAddr));
                    } else if (readObj.getClass().equals(NewUserRegistry.class)) {
                        cachedTreadPool.submit(new NewUserHandle(((NewUserRegistry) readObj).authData(), currentClientAddr));
                    } else if (readObj.getClass().equals(UpdateRecord.class)) {
                        cachedTreadPool.submit(new UpdateHandle(((UpdateRecord) readObj).data(), currentClientAddr, ((UpdateRecord) readObj)));
                    }else {
                        logger.warning("Unable to read request: Unknown request type");
                    }
                }
                if (!reponcesToSend.isEmpty()){
                    forkJoinPool.invoke(new SendingData());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendChunks(Serializable object, SocketAddress socketAddress){
        IOUtils.Chunck[] chuncks= IOUtils.getChunks(object);
        for(int i=0;i<chuncks.length;i++){
            try {
                serverChannel.send(ByteBuffer.wrap(IOUtils.toByteArray(chuncks[i])),socketAddress);
                Thread.sleep(100);
                logger.info("Sending data %d/%d".formatted(i+1,chuncks[i].length())+" to "+socketAddress.toString());
            } catch (IOException e) {
                logger.info("Missing chunk %d to client %s".formatted(i,socketAddress.toString()));
            }catch (InterruptedException e){
                logger.warning("Unterrupting of thread is not allowed");
            }
        }
    }
    private class UpdateHandle implements Callable<Object>{

        private Client client;
        private UserAuthData authData;
        private Long ID;

        public UpdateHandle(UserAuthData request, SocketAddress currentAddr, UpdateRecord record) {
            client = factory.createClient(currentAddr);
            authData = request;
            ID = record.ID();
        }


        @Override
        public Boolean call() throws Exception {
            try {
                logger.info("Executing Command Update");
                if (!clients.containsKey(client.getAddress())) {
                    clients.put(client.getAddress(), client);
                }
                    var item = DBController.getInstance().getItemByID(ID);
                    if (item.getOwnerLogin().equals(authData.login())) {
                        logger.info("Permission cheched");
                        return reponcesToSend.add(new Pair<>(client, new ServerResponse(true)));
                    }
                    return reponcesToSend.add(new Pair<>(client, new ServerResponse(false)));
                } catch (Exception e) {
                    //TODO specify exception
                    return reponcesToSend.add(new Pair<>(client, new ServerResponse(new FunctionFailedException(e.getMessage()))));
                }

        }
    }

    private class SendingData extends RecursiveAction {

        @Override
        protected void compute() {
            var pair = reponcesToSend.poll();
            if (!reponcesToSend.isEmpty()) {
                new SendingData().fork();
            }
            if (pair == null) return;
            Client client = pair.getKey();
            sendChunks(pair.getValue(), client.getAddress());
        }

    }


        /**
        Обработчик клиента, который логиниться
         */
        private class LoginingUser implements Callable<Object>{
            private Client client;
            private UserAuthData authData;

            public LoginingUser(UserAuthData request, SocketAddress currentAddr) {
                client = factory.createClient(currentAddr);
                authData = request;
            }


            @Override
            public Boolean call() throws Exception {
                try {
                    DBController.getInstance().chechAuth(authData);
                    logger.info("succesfuly loggined user");
                    clients.put(client.getAddress(),client);
                    return reponcesToSend.add(new Pair<>(client,client.getAvailableCommands()));
                }catch (NoAutentificationException | SQLException e){
                    logger.info("Mistake in logined user handling"+e.getMessage());
                    return reponcesToSend.add(new Pair<>(client,new ServerResponse(new FunctionFailedException("Wrong login or Password"))));
                }
            }
        }

        /**
        Обработчик запроса на исполнение команды
         */
        private class RequestHande implements Callable<Boolean>{

            Client client;
            UserRequest request;
            public RequestHande(UserRequest request, SocketAddress currentAddr) {
                this.request = request;
                client = factory.createClient(currentAddr);

            }


            @Override
            public Boolean call() throws Exception {
                try {
                    if (!clients.containsKey(client.getAddress())){
                        clients.put(client.getAddress(),client);
                    }
                    return reponcesToSend.add(new Pair<>(client,client.executeCommand(request)));
                } catch (ExitCommandException e) {
                    throw new RuntimeException(e);
                }
            }
        }



}

