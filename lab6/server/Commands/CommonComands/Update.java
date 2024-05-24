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

import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Update extends BaseCommand {
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
     * @see Command#execute(ParametresBundle)
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle) {
        try {
            Long id = BaseCommand.<Long>getTypedFunctionArgument(parametresBundle);
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();
            MyCollection collection = parametresBundle.collectionManager().getCollection();
            if (!(collection.getList().stream().map((o)->o.getId()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).containsKey(id))){
                throw new IllegalArgumentException("Элемента с таким id нет в коллекции");
            }
            Flat object = parametresBundle.data().readObject();
            object.setId(id);
            collection.setList(
                    Stream.concat(
                            collection.getList().stream().filter((o)->!(o.getId().equals(id))),
                            Stream.of(object)
                    ).collect(Collectors.toCollection(LinkedList::new))
            );
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
