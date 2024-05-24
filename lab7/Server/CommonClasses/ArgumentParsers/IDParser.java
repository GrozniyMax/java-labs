package CommonClasses.ArgumentParsers;

/**
 * Парсер для ID
 */
public class IDParser extends AbstractArgumentParser{

    @Override
    public Object apply(String s) {
        try {
            Long result = Long.valueOf(s.strip());
            if (result<0) throw  new IllegalArgumentException("ID �� ����� ���� ������ 0");
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("��  ����� �� ����� ����� ������� Long");
        }
    }

}
