package Commands;

import CollectionWrappers.CollectionManager;
import Entities.Flat;
import Entities.Furnish;
import Input.BaseInputManager;

import java.util.regex.Matcher;

/**
 * Команда "count_greater_than_furish"
 */
public class CountGreaterThanFurish extends BaseCommand{
    /**
     * Конструктор команды
     * @see BaseCommand
     */
    public CountGreaterThanFurish() {
        super("count_greater_than_furish",
                "считает количество элементов, поле Furish которых больше введенного");
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
        try {
            Furnish furish = Furnish.valueOf(parametresBundle.argument().toUpperCase());
            long counter=0;
            for (Flat f : parametresBundle.collectionManager().getList() ) {
                if (f.getFurnish().compareTo(furish)>0){
                    counter++;
                }
            }
            System.out.println("Количество объектов с таким полем Furish " + counter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некорректно введенный аргумент");
        }
    }
}
