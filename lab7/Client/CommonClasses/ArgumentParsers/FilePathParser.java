package CommonClasses.ArgumentParsers;

/**
 * Парсер для путей к файлам
 */
public class FilePathParser extends AbstractArgumentParser{

    @Override
    public Object apply(String s) {
        return s.strip();
    }
}
