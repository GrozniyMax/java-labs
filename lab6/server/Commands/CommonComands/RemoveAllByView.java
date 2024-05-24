package Commands.CommonComands;

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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveAllByView extends BaseCommand {

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
     * @see Command#execute(ParametresBundle)
     */
    @Override
    public List<String> execute(ParametresBundle parametresBundle) {
        View view = BaseCommand.<View>getTypedFunctionArgument(parametresBundle);
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        collection.setList(collection.getList().stream().filter((o)->{
            if (!o.getView().equals(view)) return true;
            Flat.freeID(o.getId());
            return false;
        }).collect(Collectors.toCollection(LinkedList::new)));
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
