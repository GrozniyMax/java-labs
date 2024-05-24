package CollectionWrappers;

import CommonClasses.Entities.Flat;

import java.time.ZonedDateTime;
import java.util.LinkedList;


/**
 * Класс коллекции
 */
public class MyCollection {
    private LinkedList<Flat> list;
    public final ZonedDateTime creationDate;


    /**
     * Конструктор
     * @param list - список
     * @param creationDate - дата создания
     */
    public MyCollection(LinkedList<Flat> list, ZonedDateTime creationDate) {
        this.list = list;
        this.creationDate = creationDate;
    }

    /**
     * Метод для получения списка
     * @return
     */
    public LinkedList<Flat> getList() {
        return list;
    }

    /**
     * Метод для установки списка
     * @param list - список
     */
    public void setList(LinkedList<Flat> list) {
        this.list = list;
    }
}
