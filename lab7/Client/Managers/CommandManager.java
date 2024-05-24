package Managers;

import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserData;
import CommonClasses.Entities.Flat;
import CommonClasses.Exceptions.*;
import CommonClasses.Interaction.ServerResponse;
import CommonClasses.Interaction.UserRequest;
import Input.InputManager;

import java.io.IOException;
import java.util.HashMap;


/**
 * Класс для управления командами
 */
public class CommandManager {
    private InputManager inputManager;
    private UDPManager connectionManager;

    private HashMap<String,CommandDescription> commands = new HashMap<>();


    /**
     * Конструктор
     *
     * @param inputManager - менеджер ввода
     */
    public CommandManager(InputManager inputManager, UDPManager connectionManager) throws AuthError, IOException {
        this.inputManager = inputManager;
        this.connectionManager = connectionManager;
        connectionManager.getCommands().forEach((command)->commands.put(command.comandName(),command));
    }

    private CommandManager(){

    }
    public CommandManager inheritate(InputManager inputManager){
        CommandManager result = new CommandManager();
        result.commands=this.commands;
        result.connectionManager = this.connectionManager;
        result.inputManager=inputManager;
        return result;
    }

    /**
     * Обрабатывает команду
     * @return true если нужно завершить работу
     * @throws FunctionFailedException если функция завершилась с ошибкой
     */
    public boolean handle() throws InvalidInputException, FunctionFailedException {
        try {
            String line = inputManager.readLine("#");
            String[] splitted = line.strip().split(" +");

            CommandDescription currentCommand = null;
            for (String commandName :
                    commands.keySet()) {
                if (commandName.equals(splitted[0])) {
                    currentCommand = commands.get(commandName);
                    break;
                }
            }
            if (splitted[0].equals("execute_script")){
                ScriptExecutor scriptExecutor = new ScriptExecutor(this);
                scriptExecutor.execute(splitted[1]);
                return false;
            }
            if (splitted[0].equals("update")){
                try {
                    this.connectionManager.update(Long.parseLong(splitted[1]));
                } catch (AuthError e) {
                    System.out.println(e.getMessage());;
                }catch (FunctionFailedException e) {
                    System.out.println(e.getMessage());;
                }
                return false;
            }
            if (currentCommand == null){
                throw new InvalidInputException("Команда не распознана");
            }
            UserRequest  userRequest = null;
            switch (currentCommand.userParams()){
                case NONE -> userRequest = new UserRequest(currentCommand,connectionManager.getGreetRequest());
                case ARGUMENT -> {
                    Object argument = currentCommand.argumentParseFunction().apply(splitted[1]);
                    argument = argument.getClass().cast(argument);
                    UserData data = new UserData(argument,null);
                    userRequest = new UserRequest(currentCommand,data,connectionManager.getGreetRequest());
                }
                case OBJECT -> {
                    Flat object = inputManager.readFlat();
                    UserData data = new UserData(null,object);
                    userRequest = new UserRequest(currentCommand, data,connectionManager.getGreetRequest());
                }
                case BOTH -> {
                    Object argument = currentCommand.argumentParseFunction().apply(splitted[1]);
                    argument = argument.getClass().cast(argument);
                    Flat object = inputManager.readFlat();
                    UserData data = new UserData(argument,object);
                    userRequest = new UserRequest(currentCommand, data,connectionManager.getGreetRequest());
                }
            }
            connectionManager.sendUserRequest(userRequest);
            if (currentCommand.comandName().equals("exit")) return true;
            ServerResponse response = connectionManager.getServerResponce();
            print(response);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Введен некорректный аргумент функции. "+e.getMessage());
        } catch (EndOfStreamException e){
            return true;
        } catch (InvalidInputException e){
            throw new InvalidInputException("Ошибка ввода. "+e.getMessage());
        }catch (IndexOutOfBoundsException e){
            throw  new InvalidInputException("Ошибка ввода. Не введен аргумент функции");
        }

        return false;
    }

    private void print(ServerResponse response){
        if (response.status()){
            System.out.println("Запрос успешно выполнен");
            if (response.output()!=null) response.output().stream().forEach(System.out::println);
        }else {
            System.out.println("Ошибка в выполнении команды "+ response.exception().getMessage());
        }
    }

    public void finish(){
            connectionManager.sendUserRequest(new UserRequest(commands.get("exit"),connectionManager.getGreetRequest()));
    }
}
