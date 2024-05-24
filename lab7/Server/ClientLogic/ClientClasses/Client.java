package ClientLogic.ClientClasses;

import CommonClasses.Commands.CommandDescription;
import CommonClasses.Interaction.ServerResponse;
import CommonClasses.Interaction.UserAuthData;
import CommonClasses.Interaction.UserRequest;
import CommonClasses.Utils.IDManager;
import Exceptions.ExitCommandException;
import Managers.CommandManager;
import Main.Main;
import Net.ConnectionStatus;

import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Класс реализующий клиента
 */
public class Client {
    static final Logger logger = Main.logger;
    static long lastID=0;
    protected Long ID;
    protected ConnectionStatus status;
    protected CommandManager manager;
    protected final long creationTimeInMillis = System.currentTimeMillis();
    protected SocketAddress address;

    protected UserAuthData authData;


    /**
     * Конструктор
     * @param manager - менеджер команд
     * @param address - адрес клиента
     */
    public Client(CommandManager manager,SocketAddress address) {
        this.address = address;
        logger.info("Registered new "+this);
        this.status = ConnectionStatus.CONNECTION_STARTED;
        this.manager = manager;
    }

    /**
     * Метод для выполнения команды
     * @param request - запрос
     * @return - ответ
     * @throws ExitCommandException - если команда завершает работу
     */
    public ServerResponse executeCommand(UserRequest request) throws ExitCommandException {
        logger.info(this+" started executing command"+request.command().comandName());
        status = ConnectionStatus.USER_COMMAND_SENT;
        return manager.handle(request);
    }

    /**
     * Метод для получения доступных команд
     * @return - доступные команды
     */
    public LinkedList<CommandDescription> getAvailableCommands(){
        return manager.getCommands();
    }

    /**
     * Метод для закрытия соединения
     * @return - статус
     */
    public void closeConnection(){
        logger.info(this +" finished session");
    }
    /**
     * Метод для закрытия соединения
     * @param reason - причина
     * @return - статус
     */
    public void closeConnection(String reason){
        logger.info(this +" finished session due to "+reason);
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID=" + ID +
                ", address=" + address +
                '}';
    }

    /**
     * Метод для получения времени создания
     * @return - время создания
     */
    public long getCreationTime() {
        return creationTimeInMillis;
    }

    public SocketAddress getAddress() {
        return address;
    }
}
