package Managers;

import CommonClasses.Commands.CommandDescription;
import CommonClasses.Interaction.Roles;
import CommonClasses.Interaction.ServerResponse;
import CommonClasses.Interaction.UserCommandRequest;
import CommonClasses.Interaction.UserRequest;
import CommonClasses.Utils.IOUtils;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Класс для взаимодействия с сервером по протоколу UDP
 */
public class UDPManager {

    /**
     * Константы
     * HOSTNAME - имя хоста
     * SERVER_PORT - порт сервера
     * waitingInMillis - время ожидания ответа
     * serverAddress - адрес сервера
     */
    static final String HOSTNAME = "localhost";
    static final int SERVER_PORT=8080;
    private final InetSocketAddress serverAddress;
    private int port=8081;
    private DatagramSocket clientSocket;
    private final Roles clientRole;

    private static final int waitingInMillis=3*1000;

    /**
     * Конструктор
     * @param role - роль клиента
     */
    public UDPManager(Roles role) {
        try {
            this.clientRole = role;
            this.serverAddress = new InetSocketAddress(InetAddress.getLocalHost(),8080);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        boolean clientSocketSet = false;
        while (!clientSocketSet){
            try {
                this.clientSocket = new DatagramSocket(null);
                this.port = clientSocket.getLocalPort();
                clientSocket.connect(serverAddress);
                clientSocket.setSoTimeout(waitingInMillis);
                clientSocketSet = true;
            } catch (SocketException e) {
                System.err.println(e.getMessage());
                port++;
            }
        }
    }

    /**
     * Отправляет приветствие серверу
     */
    private void sayHelloToServer(){
        byte[] request= IOUtils.toByteArray(new UserCommandRequest(this.clientRole));
        DatagramPacket helloPackage = new DatagramPacket(request, request.length, this.serverAddress);
        try {
            this.clientSocket.send(helloPackage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Приветствие отправлено");
    }

    /**
     * Читает из ответа сервера список команд
     * @return список команд
     */
    private LinkedList<CommandDescription> readCommands(){

        byte[] buffer = new byte[16384];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.serverAddress);
        try {
            //TODO add timeout execution
            boolean recieved = false;
            LinkedList<CommandDescription> result = new LinkedList<>();
            //System.out.println("Начал ждать команды");
            while (!recieved){
                this.clientSocket.receive(packet);
                result = IOUtils.<LinkedList<CommandDescription>>fromByteArray(packet.getData());
                if (result.size()>0) {
                    recieved = true;
                }
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает список команд
     * @return список команд
     */
    public LinkedList<CommandDescription> getCommands(){
        sayHelloToServer();
        //wait maybe
        return readCommands();
    }

    /**
     * Отправляет команду серверу
     * @param request - запрос пользователя
     */
    public void sendUserRequest(UserRequest request){
        byte[] buffer = IOUtils.toByteArray(request);
        try {
            this.clientSocket.send(new DatagramPacket(buffer,buffer.length));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает ответ от сервера
     * @return ответ сервера
     * @throws RuntimeException если не удалось получить ответ
     */
    public ServerResponse getServerResponce(){
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        //TODO add timeout check
        final long startTime = System.currentTimeMillis();
        try {
            ServerResponse response = null;
            while (true){
                this.clientSocket.receive(packet);
                response = IOUtils.<ServerResponse>fromByteArray(packet.getData());
                if (response!=null) {
                    return response;
                }

            }
        } catch (IOException e) {throw new RuntimeException(e);
        }
    }

}
