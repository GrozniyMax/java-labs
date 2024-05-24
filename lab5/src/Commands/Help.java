package Commands;

import Input.BaseInputManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда "help" - выводит правку по доступным командам
 */
public class Help extends BaseCommand {
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public Help() {
        super("help","Выводит правку по доступным командам");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public RequiredParametres getRequiredParametres() {
        return RequiredParametres.COMMANDS;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public void execute(ParametresBundle parametresBundle) {
        System.out.println("ОЧЕНЬ ВАЖНО: Вводить поля Flat в следующем порядке: name, coordinates, area, numberOfRooms, furnish, view, transport, house");
        System.out.println("Поля house вводить в следующем порядке: name, year, numberOfFloors, numberOfFloors");
        System.out.println("Все поля вводятся по одному в строку");
        System.out.println("Доступные команды:");
        parametresBundle.commands().forEach((Command c)->{System.out.println(c.getName()+" : "+c.getDescription());});
    }
}
