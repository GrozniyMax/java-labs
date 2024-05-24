package Commands.CommonComands;

import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Interaction.UserAuthData;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupCountingByCreationDate extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public GroupCountingByCreationDate() {
        super("group_counting_by_creation_date",
                "группирует элементы коллекции по значению поля creationDate, выводит количество элементов в каждой группе");
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
        MyCollection collection = parametresBundle.collectionManager().getCollection();

         return collection.getList().stream().map((f)->f.getItem().getCreationDate())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map((o)->"Объектов с датой создания "+o.getKey().toString()+": "+o.getValue())
                .collect(Collectors.toCollection(LinkedList::new));


    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
