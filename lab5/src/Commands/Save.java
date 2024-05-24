package Commands;

import CollectionWrappers.CollectionManager;

import Managers.JsonManager;

import java.io.FileNotFoundException;

/**
 * Команда "save" - сохраняет коллекцию в файл
 */
public class Save extends BaseCommand{
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public Save() {
        super("save",
                "сохраняет коллекцию в файл");
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
        try {
            JsonManager.dump(parametresBundle.collectionManager().getCollection());
            System.out.println("Коллекция сохранена");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
