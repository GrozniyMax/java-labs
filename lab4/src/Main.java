import Babies.Baby;
import Babies.Doono;
import Babies.DrPillman;
import Exceptions.EatedNotEnoughException;
import OtherStuff.*;

import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Story background
        Baby bendum = new Baby("Винтик");
        Baby rolyPoly = new Baby("Пончик"){
            @Override
            public void beFed() throws EatedNotEnoughException {
                Random random = new Random();
                int randNumb = random.nextInt(50);
                if (random.nextInt(50)%6==0){
                    super.beFed();
                }
                else{
                    throw new EatedNotEnoughException(this.getName()+" не наелся");
                }
            }
        };
        Doono doono = new Doono();
        DrPillman drPillman = new DrPillman();

        City flowerTown = new City("Цветочная Долина");
        City.Neighbourhood doonosNeighbourhood = flowerTown.new Neighbourhood("Some Name");

        doono.liveInNeighbourhood(doonosNeighbourhood);
        drPillman.liveInNeighbourhood(doonosNeighbourhood);
        rolyPoly.liveInNeighbourhood(doonosNeighbourhood);
        bendum.liveInNeighbourhood(doonosNeighbourhood);

        flowerTown.addCitizens(bendum,rolyPoly,doono,drPillman);
        flowerTown.addCrowd(5);
        Disaster weightless = new Disaster() {
            @Override
            public void changeCondition(ICanBeUnderEffect object) {
                object.changeSelfCondition(Condition.WEIGHTLESS);
            }

        };
        weightless.happen(flowerTown);


        boolean done=false;
        while (!done){
            try{
                flowerTown.toDo(bendum, Interaction.FEED);
                done=true;
            }
            catch (EatedNotEnoughException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println();

        done=false;
        while (!done){
            try{
                flowerTown.toDo(rolyPoly,Interaction.FEED);
                done=true;
            }
            catch (EatedNotEnoughException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println();
        System.out.println("Пончик может потерять что угодно, кроме своего аппетита.");
        System.out.println();

        drPillman.toMakeReport(doono);
        System.out.println();

        doono.think(doono.getLastGivenData());
    }

}