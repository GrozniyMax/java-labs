package Commands;

/**
 * Команда "update". Обновляет данные элемента коллекции по id
 */
public class Update extends BaseCommand {
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public Update() {
        super("update",
                "обновляет данные элемента коллекции по id");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public RequiredParametres getRequiredParametres() {
        return RequiredParametres.ALL;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public void execute(ParametresBundle parametresBundle) {
        try {
            Integer id = Integer.parseInt(parametresBundle.argument());
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();
            parametresBundle.collectionManager().getList().set( id,
                    parametresBundle.reader().readFlat());
        } catch ( NumberFormatException e){
            throw new IllegalArgumentException("Некорректно введенный id");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(" Указанный id вне чем количество элементов массива");
        }

    }
}
