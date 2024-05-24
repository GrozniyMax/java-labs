package CommonClasses.Commands;

import CommonClasses.ArgumentParsers.AbstractArgumentParser;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Класс описывающий команду
 */
public record CommandDescription (String comandName, UserParams userParams, AbstractArgumentParser argumentParseFunction) implements Serializable {

    /**
     * Конструктор для создания описания команды
     * @param comandName имя команды
     * @param userParams параметры команды
     * @param argumentParseFunction функция парсинга аргументов
     */
    public CommandDescription(String comandName, UserParams userParams, AbstractArgumentParser argumentParseFunction) {
        this.comandName = comandName;
        this.userParams = userParams;
        this.argumentParseFunction = argumentParseFunction;
    }

    /**
     * Конструктор для создания описания команды
     * @param comandName имя команды
     * @param userParams параметры команды
     */
    public CommandDescription(String comandName, UserParams userParams) {
        this(comandName, userParams, null);
    }
}
