package MyMoves.Spesial;

import ru.ifmo.se.pokemon.*;

import java.util.Random;

public class Thunderbolt extends SpecialMove {
    public Thunderbolt() {
        super(Type.ELECTRIC, 90, 100);
    }


    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        // 3 выбрано с потолка так как Math.random выдает числа с почти равной вероятностью
        // Проверено на 100_000_000 итераций
        Effect paralization = new Effect().chance(0.10).condition(Status.PARALYZE);
        pokemon.addEffect(paralization);
    }
    @Override
    protected String describe() {
        return "Использовал ThunderBolt";
    }
//    @Override
//    protected void applyOppEffects(Pokemon pokemon) {
//        // 3 выбрано с потолка так как Math.random выдает числа с почти равной вероятностью
//        // Проверено на 100_000_000 итераций
//        if ((1 + (int)(Math.random() * ((10 - 1) + 1))) == 3){
//            Effect.paralyze(pokemon);
//        }
//    }

}
