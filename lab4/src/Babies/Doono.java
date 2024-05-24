package Babies;

import Exceptions.NoConclusionException;
import OtherStuff.City;
import OtherStuff.Condition;
import OtherStuff.ReportData;

import java.util.Objects;
import java.util.Random;

public class Doono extends Baby {

    private ReportData LastGivenData;
    public Doono() {
        super("Знайка");
    }
    public void heel(Baby baby){
        if (baby.getCondition() != Condition.NORMAL){
            baby.setCondition(Condition.NORMAL);
        }
    }


    public void receiveInfo(ReportData info){
        LastGivenData = info;
    }

    public void think(ReportData info){
        class ThinkingProcess{
            private ReportData dataToThinkAbout;
            public ThinkingProcess(ReportData dataToThinkAbout) {
                this.dataToThinkAbout = dataToThinkAbout;
            }

            String generateBegginingOfThinking(){
                return "Знайка: Должна быть всем этим странностям какая-то причина.Но вот в чем она?";
            }

            void makeHypothesis(){
                System.out.println("Знайка: Возможно это как-то связано с районом? Надо об этом подумать");
            }
            Boolean checkHypothesis(Baby baby){
                City.Neighbourhood DoonnoNeighbourhood = baby.neighbourhood;
                for (Baby neightbour :
                        this.dataToThinkAbout.getUnderEffectBabies()) {
                    if (!(neightbour.neighbourhood.equals(baby.neighbourhood))){
                        return Boolean.FALSE;
                    }
                }
                return Boolean.TRUE;
            }

            void makeСonclusion(Baby baby) throws NoConclusionException {
                Random random = new Random();
                if (!(random.nextInt()%50==0)){
                    if (this.checkHypothesis(baby)){
                        System.out.println("Странно, но все случаи невесомости ограничиваются нашим районом");
                        System.out.println("Надо с этим разобраться");
                    }
                }
                else{
                    throw new NoConclusionException("У Знайки нет идей почему некоторые горожане находяться в состоянии невесомости");
                }


            }
        }

        ThinkingProcess process = new ThinkingProcess(info);
        process.generateBegginingOfThinking();
        process.makeСonclusion(this);
    }

    public ReportData getLastGivenData() {
        return LastGivenData;
    }

    @Override
    public String toString() {
        return "Babies.Doono{" +
                "city=" + super.city.toString() +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doono baby)) return false;
        return isCrowd == baby.isCrowd && Objects.equals(city, baby.city) && Objects.equals(name, baby.name) && condition == baby.condition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
