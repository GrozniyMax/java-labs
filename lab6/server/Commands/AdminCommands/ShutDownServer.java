package Commands.AdminCommands;

import Commands.BaseCommand;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Exceptions.FunctionFailedException;
import Exceptions.ExitCommandException;

import java.util.LinkedList;
import java.util.List;

public class ShutDownServer extends BaseCommand {

    public ShutDownServer() {
        super("shut_down_server", "завершает работу сервера");
    }

    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.NONE;
    }

    @Override
    public List<String> execute(ParametresBundle arguments) throws FunctionFailedException, ExitCommandException {
        System.exit(0);
        return new LinkedList<>();
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
