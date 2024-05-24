package MyPokemons;

import MyMoves.Physical.ShadowPunch;
import MyMoves.Physical.Tackle;
import MyMoves.Spesial.HudroPump;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Pichu extends Pokemon {
    public Pichu(String s, int i) {
        super(s, i);
        this.setStats(20,40,15,35,35,60);
        this.addType(Type.ELECTRIC);

        this.addMove(new HudroPump());
        this.addMove(new ShadowPunch());
        this.addMove(new Tackle());
    }
}
