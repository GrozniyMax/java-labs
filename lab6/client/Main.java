

import CommonClasses.Exceptions.InvalidInputException;
import CommonClasses.Exceptions.EndOfStreamException;
import CommonClasses.Interaction.Roles;
import Input.ConsoleInputManager;
import Managers.CommandManager;
import Managers.UDPManager;

import java.io.FileNotFoundException;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    /**
     * Точка входа в программу
     * @param args - аргументы командной строки
     */
    public static void main(String[] args) {
        Roles role = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-role")) {
                if (i + 1 < args.length) {
                    role = Roles.valueOf(args[i + 1].toUpperCase());
                }
            }
        }
        if (role == null) {
            role = Roles.CLIENT;
        }
        boolean exit = false;
        //CommandManager commandManager = new CommandManager(ConsoleInputManager.getInstance(), new UDPManager());
        CommandManager commandManager = null;
        try {
            commandManager = new CommandManager(ConsoleInputManager.getInstance(), new UDPManager(role));
        } catch (RuntimeException e) {

            if (e.getCause().getClass().equals(PortUnreachableException.class)) {
                System.out.println("Сервер недоступен. Попробуйте позже");
                exit = true;
            }
        }

        while (!exit) {
            try {
                exit = commandManager.handle();
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            } catch (EndOfStreamException e) {
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
            }
        }
        CommandManager finalCommandManager = commandManager;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {if (finalCommandManager !=null){
        finalCommandManager.finish();}
        }));

    }

}