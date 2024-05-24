package Managers;

import CollectionWrappers.MyCollection;
import Entities.*;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedList;

public final class JsonManager {
    static String filePath;


    /**
     * Класс для сохранения менеджера в JSON-файл
     */
    private static class MyCollectionAdapter{
        LinkedList<FlatParams> list;
        public Date creationTime;
        /**
         * Конструктор для создания объекта из объекта класса Managers.CollectionManager
         * @param manager - объект класса Managers.CollectionManager
         */
        public MyCollectionAdapter(MyCollection manager) {
            this.list = new LinkedList<>();
            this.creationTime = Date.from(manager.creationDate.toInstant());
            for (Flat f :
                    manager.getList()) {
                list.add(new FlatParams(f));
            }
        }
    }


    /**
     * Класс для сохранения коллекции в JSON-файл
     */
    private static class FlatParams{
        public String name; //Поле не может быть null, Строка не может быть пустой
        public Coordinates coordinates; //Поле не может быть null
        public Date creationDate;
        public Integer area; //Максимальное значение поля: 745, Значение поля должно быть больше 0
        public Long numberOfRooms; //Значение поля должно быть больше 0
        public Furnish furnish; //Поле может быть null
        public View view; //Поле не может быть null
        public Transport transport; //Поле не может быть null
        public House house; //Поле не может быть null


        /**
         * Конструктор для создания объекта из объекта класса Flat
         * @param copyFrom - объект класса Flat
         */
        public FlatParams(Flat copyFrom) {
            this.name = copyFrom.getName();
            this.creationDate = Date.from(copyFrom.getCreationDate().toInstant());
            this.coordinates = copyFrom.getCoordinates();
            this.transport=copyFrom.getTransport();
            this.area = copyFrom.getArea();
            this.numberOfRooms = copyFrom.getNumberOfRooms();
            this.furnish = copyFrom.getFurnish();
            this.view = copyFrom.getView();
            this.house = copyFrom.getHouse();
        }
    }


    /**
     * Устанавливает путь к файлу
     * @param filePath1 - путь к файлу
     */
    public static void setFilePath(String filePath1){
        filePath = filePath1;
    }


    public static void dump(MyCollection collection) throws FileNotFoundException {
        MyCollectionAdapter managerAdapter = new MyCollectionAdapter(collection);
        OutputStreamWriter writer = null;
        Gson g = new Gson();
        String file = g.toJson(managerAdapter);
        try {
            writer = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");
            writer.write(file);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Программист даун");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Указаный файл не найден");
        } catch (IOException e) {
            throw new RuntimeException("Не получилось сохранить коллекцию в JSON-файл");
        }
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Загружает менеджера из JSON-файла
     * @return - менеджер коллекции
     * @throws IllegalArgumentException - если файл не найден
     */
    public static MyCollection load() throws IllegalArgumentException, IOException {

        StringBuilder lines = new StringBuilder();
        ZonedDateTime resultTime;


        BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
            for (; ; ) {
                String line = reader.readLine();
                if (line == null)
                    break;
                lines.append(line.replace("\n",""));
            }
        MyCollectionAdapter adapter= new Gson().fromJson(lines.toString(),MyCollectionAdapter.class);
        LinkedList<Flat> list = new LinkedList<>();
        long currentId=0;
        for (int i=0;i<adapter.list.size();i++) {
            try {
                FlatParams transferObject = adapter.list.get(i);
                Flat f = new Flat();
                f.setName(transferObject.name);
                f.setCreationDate(ZonedDateTime.ofInstant(transferObject.creationDate.toInstant(),ZoneId.systemDefault()));
                f.setCoordinates(transferObject.coordinates);
                f.setArea(transferObject.area);
                f.setNumberOfRooms(transferObject.numberOfRooms);
                f.setView(transferObject.view);
                f.setFurnish(transferObject.furnish);
                f.setHouse(transferObject.house);
                f.setTransport(transferObject.transport);
                f.setId(currentId);
                currentId++;
                list.add(f);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("В процессе обработки файла возникла ошибка в объекте "+ i + ":" + e.getMessage());
            }
        }
        return new MyCollection(list,ZonedDateTime.ofInstant(adapter.creationTime.toInstant(),ZoneId.systemDefault()));


    }


}
