package Commands;

import CollectionWrappers.MyCollection;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Команда "show" - выводит все элементы коллекции
 */
public class Show extends BaseCommand{
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public Show() {
        super("show","выводит все элементы коллекции");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public List<String> execute(ParametresBundle parametresBundle) {
        List<String> output= new LinkedList<>();
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        if (parametresBundle.collectionManager().getList().size()==0){
            output.add("Коллекция пустая");
        }
        else {
            output = collection.getList().stream()
                    .sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate())).map(Flat::toString).collect(Collectors.toList());
        }
        return output;
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
