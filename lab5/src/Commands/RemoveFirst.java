package Commands;

import CollectionWrappers.CollectionManager;

import java.util.regex.Matcher;

/**
 * Команда "remove_first". Удаляет первый элемент.
 */
public class RemoveFirst extends BaseCommand{
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public RemoveFirst() {
        super("remove_first",
                "удаляет первый элемент");
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
        parametresBundle.collectionManager().getList().remove(0);
        for (int i = 0; i < parametresBundle.collectionManager().getList().size(); i++) {
            parametresBundle.collectionManager().getList().get(i).setId((long) i);
        }
    }
}
