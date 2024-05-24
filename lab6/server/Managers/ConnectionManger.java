package Managers;

import CommonClasses.Interaction.ServerResponse;
import CommonClasses.Interaction.UserCommandRequest;
import CommonClasses.Interaction.UserRequest;
import CommonClasses.Utils.IOUtils;
import Exceptions.ExitCommandException;
import Main.Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class created for handling user connection.
 * <p>Is legacy now</p>
 * @see Net.MultiClientConnectionManager # for actual connection handling
 */
public class ConnectionManger {
    static final Logger logger = Main.logger;

    //TODO ADD LOGGING
    //TODO ADD chuncks
    //TODO TEST

    private Selector selector;
    private CommandManager commandManager;
    private DatagramChannel channel;
    private SocketAddress currentClientAddress;
    private
    static final int PORT = 8080;

    static final int delay = 10000000;

    public ConnectionManger(CommandManager commandManager) {
        try {
            this.commandManager = commandManager;
            InetSocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(),8080);
            this.channel = DatagramChannel.open().bind(s);
            this.selector = Selector.open();
            this.channel.configureBlocking(false);
            this.channel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void waitForConnection() {
        logger.info("waiting for new connection");
        byte[] buffer = new byte[2048];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        boolean reiceved = false;
        Object message;
        SocketAddress currentSenderAddr;
        try {
            while (!reiceved) {
                currentSenderAddr = this.channel.receive(byteBuffer);
                message = IOUtils.fromByteArray(buffer);
                if (message.getClass().equals(UserCommandRequest.class)) {
                    this.currentClientAddress = currentSenderAddr;
                    logger.info("Registered new connection"+
                    currentSenderAddr.toString());
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAvailableCommands() {
        try {
            Thread.sleep(1000);
            this.channel.connect(currentClientAddress);
            byte[] buffer = IOUtils.toByteArray(commandManager.getCommands());
            this.channel.write(ByteBuffer.wrap(buffer));
            logger.info("Sent available commands");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private UserRequest receiveCommand() {
        byte[] buffer = new byte[2048];
        try {
            boolean received = false;
            UserRequest request = null;
            while (!received) {
                //TODO ADD timeout timer for closing connection
                this.channel.receive(ByteBuffer.wrap(buffer));
                request = IOUtils.<UserRequest>fromByteArray(buffer);
                if (request != null) {
                    logger.info("Recieved user request");
                    return request;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void sendServerResponce(ServerResponse toSend){
        ByteBuffer buffer = ByteBuffer.wrap(IOUtils.toByteArray(toSend));
        try {
            this.channel.send(buffer,this.currentClientAddress);
            logger.info("Sent server responce to command");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleUserRequest() throws ExitCommandException {
        logger.info("Waiting for new request");
        ServerResponse response= commandManager.handle(receiveCommand());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendServerResponce(response);
    }

    private void closeConnection(){
        try {
            this.channel.disconnect();
            this.channel.close();
            InetSocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(),8080);
            this.channel = DatagramChannel.open().bind(s);
            logger.info("Closed connection");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        boolean exit = false;
        while (true){
            waitForConnection();
            sendAvailableCommands();
            try {
                while (true){
                    handleUserRequest();
                }
            }catch (ExitCommandException e){
                logger.info("Caught exit command");
                closeConnection();
            }
        }

    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
