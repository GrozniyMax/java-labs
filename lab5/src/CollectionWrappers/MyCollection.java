package CollectionWrappers;

import Entities.Flat;

import java.time.ZonedDateTime;
import java.util.LinkedList;


/**
 * Класс-обертка коллекции
 */
public class MyCollection {
    private LinkedList<Flat> list;
    public final ZonedDateTime creationDate;


    /**
     * Конструктор
     * @param list - коллекция
     * @param creationDate - дата создания
     */
    public MyCollection(LinkedList<Flat> list, ZonedDateTime creationDate) {
        this.list = list;
        this.creationDate = creationDate;
    }

    /**
     * Возвращает коллекцию
     * @return коллекция
     */
    public LinkedList<Flat> getList() {
        return list;
    }

}
