package Utils;

/**
 * Класс для хранения объекта типа Pair
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class Pair <K,V>{
    private final K key;
    private final V value;

    /**
     * Конструктор
     * @param key - ключ
     * @param value - значение
     */
    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
    /**
     * Возвращает ключ
     * @return ключ
     */
    public K getKey() {
        return key;
    }
    /**
     * Возвращает значение
     * @return значение
     */
    public V getValue() {
        return value;
    }
}
