package Commands;

public interface Command {

    /**
     * @return имя коллекции
     */
    public String getName();

    /**
     * @return описание команды
     */
    public String getDescription();

    /**
     * @return необходимые функции параметры
     */
    public RequiredParametres getRequiredParametres();

    /**
     * Выполнение команды
     * @param parametresBundle набор параметров
     */
    public void execute(ParametresBundle parametresBundle);

}
