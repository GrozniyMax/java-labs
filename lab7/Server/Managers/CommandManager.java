package Managers;

import CollectionWrappers.CollectionManager;

import Commands.*;
import Commands.CommonComands.*;
import Commands.Parametres.ParametresBundle;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Exceptions.FunctionFailedException;
import CommonClasses.Interaction.ServerResponse;
import CommonClasses.Interaction.UserAuthData;
import CommonClasses.Interaction.UserRequest;
import DataBase.DBController;
import Exceptions.ExitCommandException;
import Exceptions.NoAutentificationException;
import Main.Main;


import java.sql.SQLException;
import java.util.*;

import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс, управляющий командами
 */
public class CommandManager {
    CollectionManager collectionManager;
    static final Logger logger = Main.logger;


    private Map<String, Command> commands = new HashMap<>();

    {
        register(new Add());
        register(new Clear());
        register(new CountGreaterThanFurish());

        register(new Exit());
        register(new GroupCountingByCreationDate());
        register(new Help());
        register(new History());
        register(new Info());
        register(new RemoveAllByView());
        register(new RemoveById());
        register(new RemoveFirst());
        register(new RemoveLower());
        register(new Show());
        register(new Update());
    }

    /**
     * Добавляет команду в список
     * @param newCommand - новая команда
     */
    public void register(Command newCommand){
        commands.put(newCommand.getCommandDescription().comandName(),newCommand);
    }


    /**
     * Конструктор
     * @param collectionManager - менеджер коллекции
     */
    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public CommandManager inheritate(){
        return new CommandManager(collectionManager);
    }

    /**
     * Конструктор
     * @param collectionManager - менеджер коллекции
     * @param commands - список команд
     */
    public CommandManager(CollectionManager collectionManager, Map<String, Command> commands) {
        this.collectionManager = collectionManager;
        this.commands = commands;
    }


    /**
     * Возвращает список команд
     * @return список команд
     */
    public LinkedList<CommandDescription> getCommands(){

        return commands.values().stream().map((command)->command.getCommandDescription()).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Выполняет команду
     * @param request - запрос
     * @return ответ
     * @throws ExitCommandException
     */
    public ServerResponse handle(UserRequest request) throws ExitCommandException {
        Command current = commands.get(request.command().comandName());
        ServerResponse response = null;
        try {
            logger.info("Started executing of command: "+
                    current.getName());
            ParametresBundle arguments = null;

            switch (current.getRequiredParametres()) {
                case COMMANDS -> arguments = new ParametresBundle(commands.values());
                case COLLECTION_MANAGER -> arguments = new ParametresBundle(collectionManager);
                case COLLECTION_MANAGER_AND_USER -> arguments = new ParametresBundle(collectionManager, request.data());
                case NONE -> arguments = new ParametresBundle();
            }
            List<String> result= current.execute(arguments,request.authData());
            DBController.getInstance().chechAuth(request.authData());
//            response = new ServerResponse(result==null?new LinkedList<String>():result);
            response = result!=null?new ServerResponse(result):response;
            return response;
        }catch (FunctionFailedException e){
            logger.warning("Execution of command: "+
                    current.getName()+" failed");
            response = new ServerResponse(e);
            return response;
        } catch (SQLException e) {
            logger.warning("Execution failed due to SQL mistake: " + e.getMessage());
            response = new ServerResponse(new FunctionFailedException("SQL-query mistake"));
            return response;
        } catch (NoAutentificationException e) {
            logger.warning("Not authorised user");
            response = new ServerResponse(new FunctionFailedException("Ошибка аутентификации"));
            return response;
        }
    }

    /**
     * Возвращает менеджер коллекции
     * @return менеджер коллекции
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
