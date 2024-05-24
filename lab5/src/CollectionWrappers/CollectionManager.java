package CollectionWrappers;

import Entities.Flat;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс, управляющий коллекцией
 */
public class CollectionManager {


    MyCollection collection;
    private final String[] history = new String[5];
    private int historyIndex = 0;

    /**
     * Конструктор
     * @param collection - коллекция
     */
    public CollectionManager(MyCollection collection) {
        this.collection = collection;
    }

    /**
     * Возвращает историю команд
     * @return история команд
     */
    public List<String> getHistory() {
        List<String> copy = new ArrayList<>();
        for (int i = historyIndex; i < historyIndex+5; i++) {
            if (history[i%5]!=null) {
                copy.add(history[i%5]);
            }
        }
        return copy;
    }
    /**
     * Добавляет команду в историю
     * @param command - имя команды
     */
    public void addHistory(String command){
        history[historyIndex] = command;
        historyIndex = (historyIndex+1)%5;
    }


    /**
     * Возвращает класс-обертку коллекции
     * @return коллекция
     */
    public MyCollection getCollection() {
        return collection;
    }


    /**
     * @return коллекция без класса-обертки
     */
    public List<Flat> getList(){
        return this.collection.getList();
    }
}
