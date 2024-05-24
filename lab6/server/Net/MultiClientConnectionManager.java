package Net;





import ClientLogic.ClientClasses.Client;
import ClientLogic.ClientFactory;
import CommonClasses.Interaction.ServerResponse;
import CommonClasses.Interaction.UserCommandRequest;
import CommonClasses.Interaction.UserRequest;
import CommonClasses.Utils.IOUtils;
import Exceptions.ExitCommandException;
import Main.Main;
import Managers.CommandManager;
import Managers.JsonManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.Timer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс, управляющий подключениями клиентов
 */
public class MultiClientConnectionManager {
    static final Logger logger = Main.logger;
    private CommandManager baseCommandManager;
    private DatagramChannel serverChannel;
    private ClientFactory factory;

    private Map<SocketAddress, Client> clients = new HashMap<>();
    private
    static final int PORT = 8080;

    static final int checkEveryMillis = 15*60*1000;


    /**
     * Конструктор
     * @param commandManager - менеджер команд
     */
    public MultiClientConnectionManager(CommandManager commandManager) {
        try {
            this.baseCommandManager = commandManager;
            InetSocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(),8080);
            this.serverChannel = DatagramChannel.open().bind(s);
            this.serverChannel.configureBlocking(true);
            this.factory = new ClientFactory(baseCommandManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    public void run(){
//        logger.info("Started waiting for packages");
//        Timer t = new Timer();
//        t.scheduleAtFixedRate(
//                new TimerTask() {
//                    @Override
//                    public void run() {
//                        try {
//                            JsonManager.dump(baseCommandManager.getCollectionManager().getCollection());
//                            logger.info("Saved collection to JSON");
//                        } catch (FileNotFoundException e) {
//                            logger.warning("Unabble to save collection");
//                        }
//                        clearClients(System.currentTimeMillis());
//                    }
//                },
//                checkEveryMillis,
//                checkEveryMillis
//        );
//
//        while (true) {
//            ByteBuffer readBuffer = ByteBuffer.allocate(2048);
//
//            SocketAddress currentClientAddr = null;
//            try {
//                currentClientAddr = serverChannel.receive(readBuffer);
//                logger.info("Recieved packet from "+currentClientAddr.toString());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            try {
//                if (!clients.containsKey(currentClientAddr)) {
//                    Client currentClient = new Client(baseCommandManager.inheritate(),currentClientAddr);
//                    clients.put(currentClientAddr,currentClient);
//                    ByteBuffer writeBuffer = ByteBuffer.wrap(IOUtils.toByteArray(currentClient.getAvailableCommands()));
//                    serverChannel.send(writeBuffer,currentClientAddr);
//                    logger.info("Sent commands to "+currentClient);
//                }else {
//                    Client currentClient = clients.get(currentClientAddr);
//                    UserRequest request = IOUtils.<UserRequest>fromByteArray(readBuffer.array());
//                    byte[] serverResponse = IOUtils.toByteArray(currentClient.executeCommand(request));
//                    serverChannel.send(ByteBuffer.wrap(serverResponse),currentClientAddr);
//                    logger.info("Sent responce to"+currentClient);
//                }
//            } catch (IOException e) {
//                logger.info("Unable to send data");
//            } catch (ExitCommandException e) {
//                clients.get(currentClientAddr).closeConnection();
//            }
//        }
//    }


    /**
     * Запускает менеджер
     */
    public void run(){
        //TODO add localization
        logger.info("Started waiting for packages");
        Timer t = new Timer();
        t.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            JsonManager.dump(baseCommandManager.getCollectionManager().getCollection());
                            logger.info("Saved collection to JSON");
                        } catch (FileNotFoundException e) {
                            logger.warning("Unabble to save collection");
                        }
                        clearClients(System.currentTimeMillis());
                    }
                },
                checkEveryMillis,
                checkEveryMillis
        );

        while (true) {
            ByteBuffer readBuffer = ByteBuffer.allocate(2048);

            SocketAddress currentClientAddr = null;
            try {
                currentClientAddr = serverChannel.receive(readBuffer);
                logger.info("Recieved packet from "+currentClientAddr.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Object readObj = IOUtils.fromByteArray(readBuffer.array());
                if (readObj.getClass().equals(UserCommandRequest.class)) {
                    newClient(currentClientAddr,(UserCommandRequest) readObj);
                } else if (readObj.getClass().equals(UserRequest.class)) {
                    if (!clients.containsKey(currentClientAddr)){
                        disconnectedClient(currentClientAddr);
                    }else {
                        activeClient(currentClientAddr,
                                (UserRequest) readObj);
                    }
                }else {
                    logger.warning("Unable to read request: Unknown request type");
                }
            } catch (IOException e) {
                logger.info("Unable to send data");
            }
        }
    }

    /**
     * Обрабатывает нового клиента
     * @param address - адрес клиента
     * @param userCommandRequest - запрос клиента
     * @throws IOException - если не получилось отправить данные
     */
    private void newClient(SocketAddress address,UserCommandRequest userCommandRequest) throws IOException {
        Client client = factory.createClient(userCommandRequest.roles(),
                address);
        this.clients.put(address,client);
        this.serverChannel.send(ByteBuffer.wrap(IOUtils.toByteArray(client.getAvailableCommands())),
                address);
    }

    /**
     * Обрабатывает отключенного клиента
     * @param address - адрес клиента
     * @throws IOException - если не получилось отправить данные
     */
    private void disconnectedClient(SocketAddress address) throws IOException {
        final ServerResponse response = new ServerResponse(new RuntimeException("Вы были отключены от сервера. Пожалуйста, переподключитесь."));
        serverChannel.send(ByteBuffer.wrap(IOUtils.toByteArray(response)),
                address);
    }

    /**
     * Обрабатывает активного клиента
     * @param address - адрес клиента
     * @param request - запрос клиента
     * @throws IOException - если не получилось отправить данные
     */
    private void activeClient(SocketAddress address,UserRequest request) throws IOException {
        try {
            ServerResponse response = clients.get(address).executeCommand(request);
            serverChannel.send(ByteBuffer.wrap(IOUtils.toByteArray(response)),
                    address);
        } catch (ExitCommandException e) {
            clients.get(address).closeConnection();
            clients.remove(address);
        }
    }

    /**
     * Очищает список клиентов
     * @param timeInMillis - время в миллисекундах
     */
    private void clearClients(long timeInMillis){
        clients = clients.entrySet().stream().filter(
                new Predicate<Map.Entry<SocketAddress, Client>>() {
                    @Override
                    public boolean test(Map.Entry<SocketAddress, Client> socketAddressClientEntry) {
                        if (timeInMillis - socketAddressClientEntry.getValue().getCreationTime()>checkEveryMillis){
                            socketAddressClientEntry.getValue().closeConnection("unactivity");
                            return false;
                        }
                        return true;
                    }
                }
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
