package MyMoves.Physical;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Enveador extends PhysicalMove {
    Pokemon myself;
    public Enveador() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applySelfDamage(Pokemon pokemon, double v) {
        this.myself = pokemon;
        super.applySelfDamage(pokemon, v);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (myself.getStat(Stat.HP)>pokemon.getStat(Stat.HP)){
            super.applyOppEffects(pokemon);
        }
        else {
            pokemon.setMod(Stat.HP, (int) (myself.getStat(Stat.HP)-pokemon.getHP()));
        }
    }

    @Override
    protected String describe() {
        return "Использовал Enveador";
    }
}
