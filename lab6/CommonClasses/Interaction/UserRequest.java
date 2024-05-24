package CommonClasses.Interaction;

import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserData;

import java.io.Serializable;

/**
 * Класс, представляющий запрос пользователя
 */
public record UserRequest(CommandDescription command, UserData data) implements Serializable {
    public UserRequest(CommandDescription command, UserData data) {
        this.command = command;
        this.data = data;
    }

    public UserRequest(CommandDescription command) {
        this(command, null);
    }

}
