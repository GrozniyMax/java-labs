package ClientLogic;

import ClientLogic.ClientClasses.Admin;
import ClientLogic.ClientClasses.Client;
import CommonClasses.Interaction.Roles;
import Managers.CommandManager;

import java.net.SocketAddress;

/**
 * Фабрика клиентов
 */
public class ClientFactory {

    /**
     * "Базовый" менеджер команд
     */
    CommandManager manager;

    /**
     * Конструктор
     * @param manager - менеджер команд
     */
    public ClientFactory(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Метод для создания клиента
     * @param role - роль
     * @param address - адрес
     * @return - клиент
     */
    public Client createClient(Roles role, SocketAddress address){
        switch (role){
            case CLIENT -> {
                return new Client(manager.inheritate(),
                        address);
            }
            case ADMIN -> {
                return new Admin(manager.inheritate(),
                        address);
            }
            default -> {
                return null;
            }
        }
    }
}
