package Main;

import CollectionWrappers.CollectionManager;
import CollectionWrappers.MyCollection;
import DataBase.DBController;
import Managers.CommandManager;
import Net.MultiClientConnectionManager;

import java.util.LinkedList;
import java.util.logging.Logger;

public class Main {

    public static Logger logger = Logger.getLogger("ServerLogger");

    /**
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        System.setErr(System.out);
        logger.info("Server started");


        MyCollection collection = new MyCollection(new LinkedList<>(),0);
//        try {
//            collection = DBController.getInstance().loadCollection();
//        } catch (Exception e) {
//            logger.warning("Unable to load collection. That's fatal");
//        }
        collection = DBController.getInstance().loadCollection();
        MultiClientConnectionManager manger = new MultiClientConnectionManager(new CommandManager(new CollectionManager(collection)));
        MyCollection finalCollection1 = collection;
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {

                    logger.info("Server finishes working");
                    DBController.getInstance().saveCollection(finalCollection1);

                }
        ));
        manger.run();


    }


}