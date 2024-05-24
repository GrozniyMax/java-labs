package MyMoves.Status;

import ru.ifmo.se.pokemon.*;

public class ThunderWave extends StatusMove {
    public ThunderWave() {
        super(Type.ELECTRIC, 0, 90);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect paralizateAndReduceSpeed = new Effect().condition(Status.PARALYZE).stat(Stat.SPEED,-2);
        pokemon.addEffect(paralizateAndReduceSpeed);
    }
}
