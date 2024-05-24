import MyPokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main  {
    public static void main(String[] args) {
        Battle b = new Battle();
        b.addAlly(new Porygon("Шакальный",1));
        b.addAlly(new Kabutops("Kabum",1));
        b.addAlly(new Lanturn("Lani",1));

        b.addFoe(new Nosepass("Nossy",1));
        b.addFoe(new Pichu("Pika-Pika",1));
        b.addFoe(new Silcoon("Silli",1));
        b.go();
    }
}