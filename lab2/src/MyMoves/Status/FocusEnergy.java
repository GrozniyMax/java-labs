package MyMoves.Status;

import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class FocusEnergy extends StatusMove {
    public FocusEnergy() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected String describe() {
        return "использовал Focus Energy";
    }
}
