package Commands.CommonComands;

import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;
import CommonClasses.Interaction.UserAuthData;
import DataBase.DBController;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RemoveFirst extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public RemoveFirst() {
        super("remove_first",
                "удаляет первый элемент");
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
    public List<String> execute(ParametresBundle parametresBundle, UserAuthData authData) {
        try {
            DBController.getInstance().removeByID(0L);
            if (parametresBundle.collectionManager().getCollection().getList().get(0).getOwnerLogin().equals(authData.login())) parametresBundle.collectionManager().getList().remove(0);
        } catch (SQLException e) {
            //do nothing
        }
        return new LinkedList<>();
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
