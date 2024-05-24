package CommonClasses.Interaction;

import java.io.Serializable;

/**
 * Класс, представляющий запрос команд пользователем
 */
public record UserAuthData(String login, byte[] passwd) implements Serializable {
}
