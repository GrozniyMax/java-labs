package Commands.AdminCommands;

import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Exceptions.FunctionFailedException;
import CommonClasses.Interaction.UserAuthData;
import DataBase.DBController;
import Main.Main;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


public class Save extends BaseCommand {

    Logger logger = Main.logger;
    /**
     * @see BaseCommand
     */
    public Save() {
        super("save",
                "сохраняет коллекцию в файл");
    }


    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }

    /**
     * @return
     * @see Command#execute(ParametresBundle, UserAuthData)
     */
    @Override
    public List<String> execute(ParametresBundle parametresBundle, UserAuthData authData) throws FunctionFailedException {
        LinkedList<String> result = new LinkedList<>();
        try {
            DBController.getInstance().saveCollection(parametresBundle.collectionManager().getCollection());
            result.add("Коллекция сохранена в файл");
        } finally {
            return result;
        }

    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
