package CommonClasses.Utils;

import java.io.*;

/**
 * Утилиты для работы с датаграммами
 */
public class IOUtils {

    static final int PACKET_SIZE=1024;

    /**
     * Преобразует объект в массив байт
     * @param object объект
     * @return массив байт
     */
    public static byte[] toByteArray(Object object){
        try(var array = new ByteArrayOutputStream()) {
            new ObjectOutputStream(array).writeObject(object);
            return array.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Преобразует массив байт в объект
     * @param array массив байт
     * @param <T> тип объекта
     * @return объект
     */
    public static <T> T fromByteArray(byte[] array){
        try(var reader = new ObjectInputStream(new ByteArrayInputStream(array));) {
            return (T) reader.readObject();
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
