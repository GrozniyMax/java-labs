package CommonClasses.ArgumentParsers;

import CommonClasses.Entities.View;

/**
 * Парсер для View
 */
public class ViewParser extends AbstractArgumentParser{

    @Override
    public Object apply(String s) {
        return View.valueOf(s.strip().toUpperCase());
    }
}
