package CommonClasses.ArgumentParsers;

import CommonClasses.Entities.Furnish;



/**
 * Парсер для Furnish
 */
public class FurnishParcer extends AbstractArgumentParser{
    @Override
    public Object apply(String s) throws IllegalArgumentException {
        return Furnish.valueOf(s.strip().toUpperCase());
    }
}
