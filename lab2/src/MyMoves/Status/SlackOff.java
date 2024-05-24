package MyMoves.Status;

import ru.ifmo.se.pokemon.*;

public class SlackOff extends StatusMove {
    public SlackOff() {
        super(Type.NORMAL,0,0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect halfhealing = new Effect().stat(Stat.HP, (int) (pokemon.getHP()/2));
        pokemon.addEffect(halfhealing);
    }

    @Override
    protected String describe() {
        return "использовал Slack Off";
    }
}
