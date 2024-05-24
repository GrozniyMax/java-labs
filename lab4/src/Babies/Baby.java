package Babies;

import Exceptions.EatedNotEnoughException;
import OtherStuff.*;

import java.util.Objects;


public class Baby implements ICanBeUnderEffect, ICanDo {


    static int crowdCount;
    protected City city;
    protected String name;
    protected City.Neighbourhood neighbourhood;
    protected Condition condition;

    public boolean isCrowd;

    {
        this.condition = Condition.NORMAL;
        isCrowd = false;
    }

    public void liveInTown(City city) {
        this.city = city;
        System.out.printf("%s теперь живет в %s \n", this.getName(), city.getName());
    }

    public void liveInNeighbourhood(City.Neighbourhood neighbourhood){
        this.neighbourhood = neighbourhood;
        System.out.printf("%s теперь живет в %s \n", this.getName(), neighbourhood.getName());
    }

    public Baby() {
        this.name = String.format("Безымянный%d", Baby.crowdCount);
        this.isCrowd = true;
        Baby.crowdCount++;
    }

    public void beFed() throws EatedNotEnoughException {
        System.out.println(this.getName()+" наелся");
    }
    public Baby(String name) {
        this.name = name;
    }



    public void toDo(Baby object, Interaction activity) {
        activity.action(this, object);
    }

    public String getName() {
        return name;
    }


    @Override
    public void changeSelfCondition(Condition newCondition) {
        this.condition = newCondition;
        System.out.printf("%s теперь находиться в %s \n", this.name, newCondition.getName());
    }

    @Override
    public void changeSelfConditionSilent(Condition newCondition) {
        this.condition = newCondition;
    }

    public Condition getCondition() {
        return condition;
    }

    public City getCity() {
        return city;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }


    @Override
    public String toString() {
        return "Babies.Baby{" +
                "city=" + city.toString() +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Baby baby)) return false;
        return isCrowd == baby.isCrowd && Objects.equals(city, baby.city) && Objects.equals(name, baby.name) && condition == baby.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, name, condition, isCrowd);
    }
}
