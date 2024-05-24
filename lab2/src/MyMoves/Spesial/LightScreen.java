package MyMoves.Spesial;

import ru.ifmo.se.pokemon.*;

public class LightScreen extends SpecialMove {
    public LightScreen() {
        super(Type.PSYCHIC, 0,0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect damageResistance = new Effect().turns(5).stat(Stat.ATTACK, (int) (pokemon.getStat(Stat.ATTACK) / 2));
        pokemon.addEffect(damageResistance);
    }
    @Override
    protected String describe() {
        return "Использовал Light Screen";
    }
}
