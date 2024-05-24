package Commands.CommonComands;

import CollectionWrappers.CollectionItem;
import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.ArgumentParsers.IDParser;
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
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Update extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public Update() {
        super("update",
                "обновляет данные элемента коллекции по id");
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
    public LinkedList<String> execute(ParametresBundle parametresBundle, UserAuthData authData) throws FunctionFailedException {
        try {
            Long id = BaseCommand.<Long>getTypedFunctionArgument(parametresBundle);
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();
            MyCollection collection = parametresBundle.collectionManager().getCollection();
            if (!(collection.getList().stream().map((o)->o.getItem().getId()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).containsKey(id))){
                throw new IllegalArgumentException("Элемента с таким id нет в коллекции");
            }
            Flat object = parametresBundle.data().readObject();
            object.setId(id);
            CollectionItem item = new CollectionItem(object,authData.login());

            if (!collection.getList().get(id.intValue()).getOwnerLogin().equals(authData.login())){
                logger.info("Permission denied");
                throw new FunctionFailedException("Permission denied");
            }else {
                try {
                    DBController.getInstance().updateItemByID(id,item);
                    parametresBundle.collectionManager().getCollection().setList(DBController.getInstance().loadCollection().getList());
                } catch (SQLException e) {
                    logger.info("Can not update object");

                }
            }
            return new LinkedList<String>();
        } catch ( NumberFormatException e){
            throw new IllegalArgumentException("Некорректно введенный id");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(" Указанный id вне чем количество элементов массива");
        }

    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.BOTH,
                new IDParser());
    }
}
