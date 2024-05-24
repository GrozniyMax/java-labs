package Commands;

/**
 * Класс команды завершения программы
 */
public class Exit extends BaseCommand {
    /**
     * Конструктор класса команды
     * @see BaseCommand
     */
    public Exit() {
        super("exit",
                "завершает выполнение программы");
    }


    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public RequiredParametres getRequiredParametres() {
        return RequiredParametres.NOTHING;
    }

    /**
     * @see Command#execute(ParametresBundle) ()
     */
    @Override
    public void execute(ParametresBundle parametresBundle) {
        //DO NOTHING
    }
}
