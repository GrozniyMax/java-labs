import java.lang.reflect.Method;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Story background
        Baby bendum = new Baby("Винтик");
        Baby rolyPoly = new Baby("Пончик");
        Doono doono = new Doono();
        DrPillman drPillman = new DrPillman();

        City flowerTown = new City("Цветочная Долина");
        flowerTown.addCitizens(bendum,rolyPoly,doono,drPillman);
        flowerTown.addCrowd(5);
        Disaster weightless = new Weightlessness();
        weightless.happen(flowerTown);

        doono.heel(doono);
        doono.heel(drPillman);
        doono.heelCrowd(flowerTown);


        // Main part
        flowerTown.toDo(bendum,Interaction.FEED);
        flowerTown.toDo(rolyPoly,Interaction.FEED);
        //sarcastic comments
        System.out.println("Пончик может потерять что угодно кроме своего аппетита\n");

        drPillman.toMakeReport(doono);


    }

}