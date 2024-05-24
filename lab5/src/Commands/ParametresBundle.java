package Commands;

import CollectionWrappers.CollectionManager;
import Input.InputManager;
import Managers.CommandManager;

import java.util.Collection;
import java.util.List;

/**
 * Класс, хранящий в себе все параметры, необходимые для выполнения команды
 */
public record ParametresBundle(CollectionManager collectionManager, String argument, InputManager reader, Collection<Command> commands) {

    public ParametresBundle(CollectionManager collectionManager) {
        this(collectionManager, null, null,null);
    }

    public ParametresBundle(CollectionManager collectionManager, String argument) {
        this(collectionManager, argument, null,null);
    }

    public ParametresBundle(CollectionManager collectionManager, String argument, InputManager reader) {
        this(collectionManager,argument,reader,null);
    }
    public ParametresBundle(Collection<Command> commands) {
        this(null, null, null,commands);
    }

    public ParametresBundle(CollectionManager collectionManager, InputManager reader) {
        this(collectionManager, null, reader, null);
    }

    public ParametresBundle() {
        this(null,null,null,null);
    }
}
