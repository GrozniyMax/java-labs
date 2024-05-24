package Commands.CommonComands;

import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveLower extends BaseCommand {
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
     * @see Command#execute(ParametresBundle)
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle) {
        Flat compareObject = parametresBundle.data().readObject();
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        collection.setList(
                collection.getList().stream().filter((o)->!(o.compareTo(compareObject) == -1))
                        .collect(Collectors.toCollection(LinkedList::new))
        );
        return new LinkedList<>();
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.OBJECT);
    }
}
