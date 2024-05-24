package Commands.CommonComands;

import CollectionWrappers.CollectionItem;
import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.ArgumentParsers.ViewParser;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;
import CommonClasses.Entities.View;
import CommonClasses.Interaction.UserAuthData;
import DataBase.DBController;
import Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RemoveAllByView extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public RemoveAllByView() {
        super("remove_all_by_view",
                "удаляет все элементы, view оторых совпадает с введенным");
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
    public List<String> execute(ParametresBundle parametresBundle, UserAuthData authData) {
        View view = BaseCommand.<View>getTypedFunctionArgument(parametresBundle);
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        LinkedList<CollectionItem> newCollection = collection.getList().stream().filter((o)->{
            if (!authData.login().equals(o.getOwnerLogin())) return true;
            if (!o.getItem().getView().equals(view)) {
                logger.info("Permission denied");
                return true;
            }
            try {
                DBController.getInstance().removeByID(o.getItem().getId());
                return false;
            } catch (SQLException e) {
                return true;
            }
        }).collect(Collectors.toCollection(LinkedList::new));

        collection.setList(newCollection);
        return new LinkedList<>();
    }

    /**
     * @see Command#getCommandDescription()
     */
    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.ARGUMENT,
                new ViewParser());
    }
}
