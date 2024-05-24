import CollectionWrappers.CollectionManager;
import CollectionWrappers.MyCollection;
import Entities.Flat;
import Exceptions.EndOfStreamException;
import Exceptions.FunctionFailedException;
import Input.ConsoleInputManager;
import Managers.CommandManager;
import Managers.JsonManager;

import java.nio.file.FileSystems;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.Objects;

public class Main {
    /**
     * Точка входа в программу
     * @param args - аргументы командной строки
     */
    public static void main(String[] args) {
        System.setErr(System.out);

        String fileName = null;
        try {
            fileName = Objects.requireNonNull(System.getenv("collectionFileName"));
        } catch (Throwable e){
            System.err.println("Что-то пошло не так извлечением пути к файлу. Будет использоваться ~/dumping.json");
            fileName = "dumping.json";
        }

        //Приводим путь к абсолютному виду
        fileName = FileSystems.getDefault().getPath(fileName).normalize().toAbsolutePath().toString();
        JsonManager.setFilePath(fileName);
        MyCollection collection=new MyCollection(new LinkedList<Flat>(), ZonedDateTime.now());
        try {
             collection = JsonManager.load();
        } catch (IllegalArgumentException e) {
            System.err.println("В указаном файле в JSON есть ошибка");
        }
        catch (Throwable e){
            System.out.println("Поскольку что-то пошло не так, у нас не получилось распарсить коллекцию");
        }
        finally {
            runApp(new CommandManager(new CollectionManager(collection), ConsoleInputManager.getInstance()));
        }
    }

    public static void runApp(CommandManager commandManager){
        boolean exit = false;
        while (!exit){
            try {
                exit = commandManager.handle();
            }catch (FunctionFailedException e){
                System.err.println(e.getMessage());
            }catch (EndOfStreamException e){
                exit=true;
            }
        }
    }
}