package ClientLogic.ClientClasses;

import Commands.AdminCommands.Save;
import Commands.AdminCommands.ShutDownServer;
import Managers.CommandManager;

import java.net.SocketAddress;

/**
 * Класс реализующий админа
 */
public class Admin extends Client {


    public Admin(CommandManager manager, SocketAddress address) {
        super(manager, address);
        this.manager.register(new Save());
        this.manager.register(new ShutDownServer());
        //TODO add command to shutdown server
    }

    @Override
    public String toString() {
        return "Admin{" +
                "ID=" + ID +
                ", address=" + address +
                '}';
    }
}
