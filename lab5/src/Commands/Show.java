package Commands;

import CollectionWrappers.CollectionManager;


/**
 * Команда "show" - выводит все элементы коллекции
 */
public class Show extends BaseCommand{
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public Show() {
        super("show","выводит все элементы коллекции");
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
        if (parametresBundle.collectionManager().getList().size()==0){
            System.out.println("Коллекция пустая");
        }
        else {
            parametresBundle.collectionManager().getList().forEach(System.out::println);
        }
    }
}
