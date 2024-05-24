package MyMoves.Spesial;

import ru.ifmo.se.pokemon.*;

public class RazorWind extends SpecialMove {
    public RazorWind() {
        super(Type.NORMAL,80,100);
    }

    @Override
    protected String describe() {
        return "Использовал Razor Wind";
    }
}
