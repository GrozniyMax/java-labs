package MyPokemons;

import MyMoves.Physical.ShadowPunch;
import MyMoves.Spesial.HudroPump;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Lanturn extends Pokemon {
    public Lanturn(String s, int i) {
        super(s, i);
        this.setStats(125,58,58,76,76,67);
        this.addType(Type.WATER);
        this.addType(Type.ELECTRIC);

        this.addMove(new HudroPump());
        this.addMove(new ShadowPunch());
    }
}
