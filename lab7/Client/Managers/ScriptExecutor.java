package Managers;

import CommonClasses.Exceptions.FunctionFailedException;
import CommonClasses.Exceptions.InvalidInputException;
import Input.ScriptInputManager;
import Managers.CommandManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для выполнения скриптов
 */
public class ScriptExecutor {
    /**
     * Предыдущий менеджер команд
     */
    CommandManager previousCommandManager;

    /**
     * Конструктор
     * @param previousCommandManager - предыдущий менеджер команд
     */
    public ScriptExecutor(CommandManager previousCommandManager) {
        this.previousCommandManager = previousCommandManager;
    }

    /**
     * Множество файлов, которые уже были выполнены
     */
    static Set<String> executedFiles = new HashSet<>();

    /**
     * Выполняет скрипт
     * @param filepath - путь к файлу
     */
    public void execute(String filepath) {
        File scriptFile = new File(filepath.strip());

        if (executedFiles.contains(scriptFile.getAbsolutePath())){
            System.err.println("Вы создали бесконечную рекурсию. Поэтому выполнение прикращается");
            return;
        }
        executedFiles.add(scriptFile.getAbsolutePath());
        CommandManager scriptCommandManager = null;
        try {
            scriptCommandManager = this.previousCommandManager.inheritate(new ScriptInputManager(new FileInputStream(scriptFile)));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Некорректный путь к файлу");
        }
        boolean noExceptionsThrown = true;
        boolean exit = false;
        while (!exit){
            try {
                exit = scriptCommandManager.handle();
            }catch (InvalidInputException|FunctionFailedException e){
                noExceptionsThrown = false;
                System.err.println("Ошибка в выполнении скрипта: "+e.getMessage());
                exit = true;
            }
        }
        if (noExceptionsThrown) System.out.printf("Скрипт %s выполнен успешно\n",scriptFile.getName());
        executedFiles.remove(scriptFile.getAbsolutePath());
    }

}
