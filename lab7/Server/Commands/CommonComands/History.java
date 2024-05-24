package Commands.CommonComands;

import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Interaction.UserAuthData;

import java.util.LinkedList;


public class History extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public History() {
        super("history",
                "выводит последние 5 команд");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }

    /**
     * @see Command#execute(ParametresBundle, UserAuthData)
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, UserAuthData authData) {
        return new LinkedList<>(parametresBundle.collectionManager().getHistory());
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
