package Commands;

import CollectionWrappers.CollectionManager;
import Entities.Flat;
import Entities.View;

import java.util.regex.Matcher;

/**
 * Команда "remove_all_by_view" удаления всех элементов, значение поля view которых эквивалентно введенному
 */
public class RemoveAllByView extends BaseCommand{

    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public RemoveAllByView() {
        super("remove_all_by_view",
                "удаляет все элементы, view оторых совпадает с введенным");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public RequiredParametres getRequiredParametres() {
        return RequiredParametres.ARGUMENT;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public void execute(ParametresBundle parametresBundle) {
        View view = View.valueOf(parametresBundle.argument().strip().toUpperCase());

        parametresBundle.collectionManager().getList().removeIf((Flat colObj)->colObj.getView().equals(view));
        for (int i = 0; i < parametresBundle.collectionManager().getList().size(); i++) {
            parametresBundle.collectionManager().getList().get(i).setId(Long.valueOf(i));
        }
    }
}
