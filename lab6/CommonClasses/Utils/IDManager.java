package CommonClasses.Utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс, представляющий менеджер идентификаторов
 */
public class IDManager {
    public  Long lastId = 0L;
    private  Queue<Long> freeIDs = new LinkedList<>();

    /**
     * Метод для получения нового идентификатора
     * @return - новый идентификатор
     */
    public  Long getID(){
        if (freeIDs.isEmpty()){
            return lastId++;
        }
        return freeIDs.poll();
    }

    /**
     * Метод для освобождения идентификатора
     * @param ID - идентификатор, который нужно освободить
     */
    public  void addFreeID(Long ID){
        freeIDs.add(ID);
    }
}
