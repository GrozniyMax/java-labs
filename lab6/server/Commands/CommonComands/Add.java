package Commands.CommonComands;

import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.Description;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Flat;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Add extends BaseCommand implements Description {
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
     * @see Command#execute(ParametresBundle)
     */
    public List<String> execute(ParametresBundle parametresBundle) throws IllegalArgumentException{

        List<Flat> collection = parametresBundle.collectionManager().getList();
        MyCollection c = parametresBundle.collectionManager().getCollection();
        Flat object = parametresBundle.data().readObject();
        c.setList(Stream.concat(collection.stream(),Stream.of(object))
                .collect(Collectors.toCollection(
                        LinkedList<Flat>::new
                )));
        return new LinkedList<String>();
    }

    /**
     * @see Command#getCommandDescription()
     */
    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name, UserParams.OBJECT);
    }
}
