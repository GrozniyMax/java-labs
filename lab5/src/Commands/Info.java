package Commands;

/**
 * Команда "info" - выводит информацию о коллекции
 */
public class Info extends BaseCommand {
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public Info() {
        super("info","выводит информацию о коллекции");
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
        System.out.println("ДАТА СОЗДАНИЯ: "+ parametresBundle.collectionManager().getCollection().creationDate.toString());
        System.out.println("ТИП КОЛЛЕКЦИИ: "+parametresBundle.collectionManager().getList().getClass().getSimpleName());
        System.out.println("КОЛИЧЕСТВО ЭЛЕМЕНТОВ: "+ parametresBundle.collectionManager().getList().size());
    }
}
