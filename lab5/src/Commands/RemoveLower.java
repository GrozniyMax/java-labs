package Commands;

import Commands.BaseCommand;
import Entities.Flat;
import Input.InputManager;

/**
 * Команда "remove_lover". Удаляет все элементы меньше заявленного
 */
public class RemoveLower extends BaseCommand {
    /**
     * Пустой конструктор
     * @see BaseCommand
     */
    public RemoveLower() {
        super("remove_lover",
                "удаляет все элементы меньше заявленного");
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
        Flat compareObject = parametresBundle.reader().readFlat();
        parametresBundle.collectionManager().getList().removeIf((Flat f)->{
            return f.compareTo(compareObject) == -1;
        });

        for (int i = 0; i < parametresBundle.collectionManager().getList().size(); i++) {
            parametresBundle.collectionManager().getList().get(i).setId((long) i);
        }
    }
}
