package CommonClasses.Interaction;

import java.io.Serializable;

/**
 * Класс, представляющий запрос команд пользователем
 */
public record UserCommandRequest(UserAuthData data) implements Serializable {
}
