package CommonClasses.Commands;

import java.io.Serializable;

/**
 * Перечисление для параметров команды вводимых пользователем
 */
public enum UserParams implements Serializable {
    NONE,
    ARGUMENT,
    OBJECT,
    BOTH;

}
