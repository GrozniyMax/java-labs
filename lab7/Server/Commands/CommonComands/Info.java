package Commands.CommonComands;

import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Interaction.UserAuthData;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Info extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public Info() {
        super("info","выводит информацию о коллекции");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }

    /**
     * @see Command#execute(ParametresBundle, UserAuthData) ()
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, UserAuthData authData) {
        return Stream.of(
                "ТИП КОЛЛЕКЦИИ: "+parametresBundle.collectionManager().getList().getClass().getSimpleName(),
                "КОЛИЧЕСТВО ЭЛЕМЕНТОВ: "+ parametresBundle.collectionManager().getList().size()).collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
