package MyMoves.Status;

import ru.ifmo.se.pokemon.*;

public class Synthesis extends StatusMove {
    public Synthesis() {
        super(Type.GRASS,0,0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        // рандомностью реализованна погода
        Effect healing = new Effect().stat(Stat.HP, (int) (Math.random() * 10));
        pokemon.addEffect(healing);
    }

    @Override
    protected String describe() {
        return "использовал Synthesis";
    }
}
