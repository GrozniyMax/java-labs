package Commands.Parametres;

import CollectionWrappers.CollectionManager;
import Commands.Command;
import CommonClasses.Commands.UserData;

import java.util.Collection;

/**
 * Класс для передачи параметров в команды
 */
public record ParametresBundle(CollectionManager collectionManager, UserData data,Collection<Command> commands) {

    public ParametresBundle(Collection<Command> commands) {
        this(null, null, commands);
    }

    public ParametresBundle(CollectionManager collectionManager) {
        this(collectionManager, null, null);
    }

    public ParametresBundle(CollectionManager collectionManager, UserData date) {
        this(collectionManager, date, null);
    }

    public ParametresBundle() {
        this(null,null,null);
    }

}
