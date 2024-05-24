package CommonClasses.Commands;

import CommonClasses.Entities.Furnish;
import CommonClasses.Entities.View;

/**
 * LEGACY CLASS
 * NEVER USED NOW
 */
public class ArgumentParsers {

    public static Long parseID(String argument){
        try {
            Long result = Long.valueOf(argument.strip());
            if (result<0) throw  new IllegalArgumentException("ID не может быть меньше 0");
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("");
        }
    }

    public static String parceFilePath(String argument){
        return  argument.strip();
    }

    public static View parceView(String argument){
        return View.valueOf(argument.strip().toUpperCase());
    }

    public static Furnish parceFurnish(String argument){
        return Furnish.valueOf(argument.strip().toUpperCase());
    }

}
