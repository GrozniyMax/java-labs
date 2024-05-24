package CommonClasses.Interaction;

import java.io.Serializable;

/**
 * Класс, представляющий запрос команд пользователем
 */
public record UserCommandRequest(Roles roles) implements Serializable {
}
