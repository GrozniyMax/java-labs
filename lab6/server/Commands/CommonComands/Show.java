package Commands.CommonComands;

import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Show extends BaseCommand {
    /**
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
                    .sorted(Comparator.comparing(Flat::getCreationDate)).map(Flat::toString).collect(Collectors.toList());
        }
        return output;
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
