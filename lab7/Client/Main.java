

import CommonClasses.Exceptions.FunctionFailedException;
import CommonClasses.Exceptions.InvalidInputException;
import CommonClasses.Exceptions.EndOfStreamException;
import Input.ConsoleInputManager;
import Managers.AuthError;
import Managers.AutorisationManager;
import Managers.CommandManager;
import Managers.UDPManager;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;

public class Main {
    /**
     * Точка входа в программу
     * @param args - аргументы командной строки
     */
    public static void main(String[] args) {
        AutorisationManager manager = new AutorisationManager();
        manager.login();
        boolean exit = false;
        //CommandManager commandManager = new CommandManager(ConsoleInputManager.getInstance(), new UDPManager());
        CommandManager commandManager = null;
        try {
            commandManager = new CommandManager(ConsoleInputManager.getInstance(), new UDPManager(manager.packToRequest(), manager.isRegistering()));
        } catch (IOException e) {
            System.out.println("Сервер недоступен. Попробуйте позже");
            exit = true;
        } catch (AuthError e) {
            System.err.println("Ошибка аутентификации. Перезапустите приложение");
            System.exit(-1);
        }

        while (!exit) {
            try {
                exit = commandManager.handle();
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            } catch (EndOfStreamException e) {
                exit = true;
            } catch (NullPointerException e){
                System.out.println("Сервер недоступен. Попробуйте позже");
                exit = true;
            } catch (RuntimeException e) {
                if (e.getCause()==null){
                    System.err.println(e.getMessage());
                }
                if (e.getCause().getClass().equals(PortUnreachableException.class)) {
                    System.out.println("Сервер недоступен. Попробуйте позже");
                    exit = true;
                }
                if (e.getCause().getClass().equals(SocketTimeoutException.class)){
                    System.out.println("Время ожидания ответа от сервера вышло. Возможно сервер недоступен");
                }
            } catch (FunctionFailedException e) {
                System.out.println("Не получилось выполнить функцию. "+e.getMessage());
            }
        }
        CommandManager finalCommandManager = commandManager;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {if (finalCommandManager !=null){
        finalCommandManager.finish();}
        }));

    }

}