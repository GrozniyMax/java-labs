package MyMoves.Spesial;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class HudroPump  extends SpecialMove {
    public HudroPump() {
        super(Type.WATER, 110,80);
    }

    @Override
    protected String describe() {
        return "Использовал Hudro Pump";
    }
}
