package Commands;

import CollectionWrappers.CollectionManager;
import Exceptions.FunctionFailedException;
import Input.BaseInputManager;
import Input.InputManager;
import Input.ScriptInputManager;
import Managers.CommandManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Класс команды выполнения скрипта
 */
public class ExecuteScript extends BaseCommand{
    static Set<String> executedFiles = new HashSet<>();

    /**
     * Конструктор класса команды
     * @see BaseCommand
     */
    public ExecuteScript() {
        super("execute_script",
                "выполняет скрипт записанный в файл(путь передается в виде аргумента функции)");
    }
    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public RequiredParametres getRequiredParametres() {
        return RequiredParametres.ARGUMENT;
    }
    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public void execute(ParametresBundle parametresBundle) {
        File scriptFile = new File(parametresBundle.argument().strip());

        if (executedFiles.contains(scriptFile.getAbsolutePath())){
            System.err.println("Вы создали бесконечную рекурсию. Поэтому выполнение прикращается");
            return;
        }

        executedFiles.add(scriptFile.getAbsolutePath());
        CommandManager scriptCommandManager = null;

        try {

            scriptCommandManager = new CommandManager(parametresBundle.collectionManager(),
                    new ScriptInputManager(new FileInputStream(scriptFile)));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Некорректный путь к файлу");
        }
        boolean noExceptionsThrown = true;
        boolean exit = false;
        while (!exit){
            try {
                exit = scriptCommandManager.handle();
            }catch (FunctionFailedException e){
                noExceptionsThrown = false;
                System.err.println("Ошибка в выполнении скрипта: "+e.getMessage());
                exit = true;
            }
        }
        if (noExceptionsThrown) System.out.printf("Скрипт %s выполнен успешно\n",scriptFile.getName());
        executedFiles.remove(scriptFile.getAbsolutePath());
    }
}
