package Commands.CommonComands;

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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class RemoveById extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public RemoveById() {
        super("remove_by_id","удаляет объект по id");
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
    public List<String> execute(ParametresBundle parametresBundle) throws FunctionFailedException {

        try {
            Long id = BaseCommand.<Long>getTypedFunctionArgument(parametresBundle);
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();
            MyCollection collection = parametresBundle.collectionManager().getCollection();
            Set<Long> usedIDs = collection.getList().stream().map((obj)->obj.getId()).distinct().collect(Collectors.toSet());
            if (!usedIDs.contains(id)) throw new FunctionFailedException("Нет элемента с таким id");
            collection.setList(
                    collection.getList().stream().filter((obj)->{
                        if (!(obj.getId().equals(id))) return true;
                        Flat.freeID(obj.getId());
                        return false;
                    }).collect(Collectors.toCollection(LinkedList::new))
            );
        } catch (IndexOutOfBoundsException e) {
            throw new FunctionFailedException("id больше чем количество элементов массива");
        }
        return new LinkedList<>();
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.ARGUMENT,
                new IDParser());
    }
}
