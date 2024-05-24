package Commands;

import CollectionWrappers.CollectionManager;
import Entities.Flat;
import Input.BaseInputManager;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Класс команды группировки элементов по дате создания
 */
public class GroupCountingByCreationDate extends BaseCommand{
    /**
     * Конструктор класса команды
     * @see BaseCommand
     */
    public GroupCountingByCreationDate() {
        super("group_counting_by_creation_date",
                "группирует элементы по дате создания, выводя их количество");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public RequiredParametres getRequiredParametres() {
        return RequiredParametres.COLLECTION_ONLY;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public void execute(ParametresBundle parametresBundle) {
        HashMap<ZonedDateTime,Integer> dates = new HashMap<>();
        ZonedDateTime currentDate;
        for (Flat flat : parametresBundle.collectionManager().getList()) {
            currentDate = flat.getCreationDate();
            if (dates.containsKey(currentDate)){
                dates.put(currentDate,dates.get(currentDate)+1);
            }
            else {
                dates.put(currentDate,1);
            }
        }
        for (ZonedDateTime time: dates.keySet()) {
            System.out.println(time.toString()+" : "+dates.get(time).toString());
        }
    }
}
