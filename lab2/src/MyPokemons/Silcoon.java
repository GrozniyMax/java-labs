package MyPokemons;

import MyMoves.Status.FocusEnergy;
import MyMoves.Status.SlackOff;
import MyMoves.Status.Synthesis;
import MyMoves.Status.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Silcoon extends Pokemon {
    public Silcoon(String s, int i) {
        super(s, i);
        this.setStats(50,35,55,25,25,15);
        this.addType(Type.BUG);

        this.addMove(new Synthesis());
        this.addMove(new SlackOff());
        this.addMove(new ThunderWave());
        this.addMove(new FocusEnergy());
    }
}
