package MyPokemons;

import MyMoves.Spesial.LightScreen;
import MyMoves.Spesial.RazorWind;
import MyMoves.Spesial.Thunderbolt;
import MyMoves.Status.FocusEnergy;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Porygon extends Pokemon {
    public Porygon(String s, int i) {
        super(s, i);
        this.setStats(65,60,70,85,75,40);
        this.addType(Type.NORMAL);

        this.addMove(new LightScreen());
        this.addMove(new RazorWind());
        this.addMove(new Thunderbolt());
        this.addMove(new FocusEnergy());

    }
}
