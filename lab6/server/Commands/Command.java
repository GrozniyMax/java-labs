package Commands;

import Commands.Parametres.Description;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Exceptions.FunctionFailedException;
import Exceptions.ExitCommandException;

import java.io.Serializable;
import java.util.List;

public interface Command extends Description {
    /**
     * Получить имя команды
     * @return имя команды
     */
     String getName();

    /**
     * Получить описание команды
     * @return описание команды
     */
     String getDescription();

     ServerParams getRequiredParametres();

    /**
     * Выполнить команду
     * @param arguments - аргументы команды
     * @return строки которые нужно вывести пользователю
     * @throws FunctionFailedException если команда завершилась неудачей
     * @throws ExitCommandException если команда завершилась выходом из программы
     */
     List<String> execute(ParametresBundle arguments) throws FunctionFailedException, ExitCommandException;


}
