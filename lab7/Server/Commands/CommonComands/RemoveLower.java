package Commands.CommonComands;

import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;
import CommonClasses.Interaction.UserAuthData;
import DataBase.DBController;
import Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RemoveLower extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public RemoveLower() {
        super("remove_lover",
                "удаляет все элементы меньше заявленного");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER;
    }

    /**
     * @see Command#execute(ParametresBundle, UserAuthData)
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, UserAuthData authData) {
        Flat compareObject = parametresBundle.data().readObject();
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        collection.setList(
                collection.getList().stream().filter((o)->{
                    if (!o.getOwnerLogin().equals(authData.login())) return true;
                    if (!(o.getItem().compareTo(compareObject) != -1)) return true;
                    try {
                        DBController.getInstance().removeByID(o.getItem().getId());
                        return false;
                    } catch (SQLException e) {
                        return true;
                    }}).collect(Collectors.toCollection(LinkedList::new))
        );
        return new LinkedList<>();
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.OBJECT);
    }
}
