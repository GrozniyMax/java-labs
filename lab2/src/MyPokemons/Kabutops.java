package MyPokemons;

import MyMoves.Status.SlackOff;
import MyMoves.Status.Synthesis;
import MyMoves.Status.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Kabutops extends Pokemon {
    public Kabutops(String s, int i) {
        super(s, i);
        this.setStats(60,115,105,65,70,80);
        this.addType(Type.ROCK);
        this.addType(Type.WATER);

        this.addMove(new Synthesis());
        this.addMove(new SlackOff());
        this.addMove(new ThunderWave());
    }
}
