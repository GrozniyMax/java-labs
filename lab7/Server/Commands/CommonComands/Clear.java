package Commands.CommonComands;

import CollectionWrappers.CollectionItem;
import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;
import CommonClasses.Interaction.UserAuthData;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class Clear extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public Clear() {
        super("clear",
                "очищает коллекцию");
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
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        Stream<CollectionItem> stream = Stream.empty();
        collection.setList(stream.collect(Collectors.toCollection(LinkedList<CollectionItem>::new)));
        return new LinkedList<>();
    }

    /**
     * @see Command#getCommandDescription()
     */
    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
