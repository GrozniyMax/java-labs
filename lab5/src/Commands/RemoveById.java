package Commands;

import CollectionWrappers.CollectionManager;

import java.util.regex.Matcher;


/**
 * Команда "remove_by_id". Удаляет элемент из коллекции по его id.
 */
public class RemoveById extends BaseCommand {
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public RemoveById() {
        super("remove_by_id","удаляет объект по id");
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

        try {
            Integer id = Integer.parseInt(parametresBundle.argument().strip());
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();
            parametresBundle.collectionManager().getList().remove(id);
            for (int i = 0; i < parametresBundle.collectionManager().getList().size(); i++) {
                parametresBundle.collectionManager().getList().get(i).setId(Long.valueOf(i));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("id больше чем количество элементов массива");
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Некорректно введенный id");
        }
    }
}
