package MyPokemons;

import MyMoves.Physical.Enveador;
import MyMoves.Physical.ShadowPunch;
import MyMoves.Physical.Tackle;
import MyMoves.Spesial.HudroPump;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Nosepass extends Pokemon {


    public Nosepass(String s, int i) {
        super(s, 1);
        this.setType(Type.ROCK);
        this.setStats(30,45,135,45,90,30);

        this.addMove(new HudroPump());
        this.addMove(new ShadowPunch());
        this.addMove(new Tackle());
        this.addMove(new Enveador());
    }
}
