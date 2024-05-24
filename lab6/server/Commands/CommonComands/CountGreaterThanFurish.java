package Commands.CommonComands;

import CollectionWrappers.MyCollection;
import Commands.BaseCommand;
import Commands.Command;
import Commands.Parametres.ParametresBundle;
import Commands.Parametres.ServerParams;
import CommonClasses.ArgumentParsers.FurnishParcer;
import CommonClasses.Commands.CommandDescription;
import CommonClasses.Commands.UserParams;
import CommonClasses.Entities.Furnish;

import java.util.LinkedList;


public class CountGreaterThanFurish extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public CountGreaterThanFurish() {
        super("count_greater_than_furish",
                "вывести количество элементов, значение поля Furnish которых больше заданного");
    }
    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle) {
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        Furnish compareFurish = Furnish.DESIGNER;
        LinkedList<String> result = new LinkedList<>();
        result.add("���������� ��������� � Furnish ������ ���������:"+collection.getList().stream().filter((f)->f.getFurnish().compareTo(compareFurish)==1).count());
        return result;
    }

    @Override
    public CommandDescription getCommandDescription() {
        return new CommandDescription(name,
                UserParams.ARGUMENT,
                new FurnishParcer());
    }
}
