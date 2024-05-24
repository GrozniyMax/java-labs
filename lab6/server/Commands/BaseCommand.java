package Commands;

import Commands.Command;
import Commands.Parametres.Description;
import Commands.Parametres.ParametresBundle;

/**
 * Клаcc реализующий базовую команду
 */
public abstract class BaseCommand implements Command {


    /**
     * Описание команды
     */
    protected final String description;
    /**
     * Имя команды
     */
    protected final String name;


    /**
     * Конструктор
     * @param name - имя
     * @param description - описание
     */
    protected BaseCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Получить описание команды
     * @return - описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Получить имя команды
     * @return - имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Получить аргумент в необходимом типе
     * @return - описание команды
     */
    public static <T> T getTypedFunctionArgument(ParametresBundle parametresBundle){
        return (T) parametresBundle.data().fuctionArgument();
    }
}
