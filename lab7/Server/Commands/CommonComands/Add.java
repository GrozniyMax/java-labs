package Commands.CommonComands;

import CollectionWrappers.CollectionItem;
import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.Description;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;
import CommonClasses.Exceptions.FunctionFailedException;
import CommonClasses.Interaction.UserAuthData;
import DataBase.DBController;
import Exceptions.NoResultOfSQL;
import Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Add extends BaseCommand implements Description {
    Logger logger = Main.logger;
    /**
     * @see BaseCommand
     */
    public Add() {
        super("add",
                "добавляет новый элемент в коллекцию");
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
    public List<String> execute(ParametresBundle parametresBundle, UserAuthData authData) throws IllegalArgumentException, FunctionFailedException {

        List<CollectionItem> collection = parametresBundle.collectionManager().getList();
        MyCollection c = parametresBundle.collectionManager().getCollection();
        Flat object = parametresBundle.data().readObject();
        object.changeID();
        try {
            var item = new CollectionItem(object,authData.login());
            DBController.getInstance().insertFlat(item);
            c.setList(Stream.concat(collection.stream(),Stream.of(item))
                    .collect(Collectors.toCollection(
                            LinkedList<CollectionItem>::new
                    )));
            return new LinkedList<String>();
        } catch (SQLException e){
            logger.warning("SQL mistake:" + e.getMessage());
            throw new FunctionFailedException("Some SQL error");
        }

    }

    /**
     * @see Command#getCommandDescription()
     */
    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name, UserParams.OBJECT);
    }
}
