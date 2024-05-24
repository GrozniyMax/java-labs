package Commands.CommonComands;

import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Help extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public Help() {
        super("help","выводит справку по доступным командам");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COMMANDS;
    }

    /**
     * @see Command#execute(ParametresBundle)
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle) {
        List<BaseCommand> commands;
        return Stream.concat(
                Stream.of("ОЧЕНЬ ВАЖНО: Вводить поля Flat в следующем порядке: name, coordinates, area, numberOfRooms, furnish, view, transport, house",
                        "Поля house вводить в следующем порядке: name, year, numberOfFloors, numberOfFloors",
                        "Все поля вводятся по одному в строку",
                        "Доступные команды:"),
                parametresBundle.commands().stream().map((c)->c.getName()+" : "+c.getDescription())).collect(Collectors.toCollection(LinkedList<String>::new));
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.NONE);
    }
}
