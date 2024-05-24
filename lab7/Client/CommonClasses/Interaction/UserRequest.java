package CommonClasses.Interaction;

import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserData;

import java.io.Serializable;

/**
 * Класс, представляющий запрос пользователя
 */
public record UserRequest(CommandDescription command, UserData data, UserAuthData authData) implements Serializable {
    public UserRequest(CommandDescription command, UserData data, UserAuthData authData) {
        this.command = command;
        this.data = data;
        this.authData = authData;
    }

    public UserRequest(CommandDescription command, UserAuthData authData) {
        this(command, null, authData);
    }

}
