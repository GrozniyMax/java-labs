package OtherStuff;

import Babies.Baby;
import Exceptions.EatedNotEnoughException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class City implements ICanBeUnderEffect, ICanDo {

    public class Neighbourhood{
        ArrayList<Baby> peopleLivingIn;
        String name;
        public Neighbourhood(String name) {
            this.peopleLivingIn = new ArrayList<>();
            this.name = name;
        }

        public void becomeMemberOf(Baby baby){
            this.peopleLivingIn.add(baby);
            System.out.println();
        }

        public ArrayList<Baby> getPeopleLivingIn() {
            return peopleLivingIn;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Neighbourhood that)) return false;
            return Objects.equals(peopleLivingIn, that.peopleLivingIn) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(peopleLivingIn, name);
        }
    }
    private ArrayList<Baby> members;
    String name;
    static final String PSEUDONIM="Малышки";

    public City(String name) {
        this.name=name;
        this.members = new ArrayList<Baby>(); {
        }
    }
    public void addCitizens(Baby... babies){
        for (Baby futureCitizen : babies) {
            this.members.add(futureCitizen);
            futureCitizen.liveInTown(this);
        }
        System.out.println();

    }
    public void addCrowd(int number){
        final Neighbourhood crowdNeighbourhood = this.new Neighbourhood("Some other Neighbourhood");
        for (int i = 0; i < number; i++) {
            Baby futureCitizen = new Baby();
            futureCitizen.liveInNeighbourhood(crowdNeighbourhood);
            futureCitizen.liveInTown(this);
            members.add(futureCitizen);
        }
        System.out.println();
    }

    @Override
    public void changeSelfCondition(Condition newCondition) {
        for (Baby citizen : members) {
            if (!(citizen.getName().contains("Безымянный"))){
                citizen.changeSelfCondition(newCondition);
            }
        }
    }

    @Override
    public void changeSelfConditionSilent(Condition newCondition) {
        for (Baby citizen : members) {
            citizen.changeSelfConditionSilent(newCondition);
        }
        System.out.printf("И все остальные жители цветочного города теперь в %s \n",newCondition.getName());
        System.out.println();
    }

    @Override
    public void toDo(Baby object, Interaction activity) throws EatedNotEnoughException {
        activity.action(this,object);

    }

    public String getName() {
        return name;
    }

    public List<Baby> getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Objects.equals(members, city.members) && Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, name);
    }

    @Override
    public String toString() {
        return "OtherStuff.City{" +
                "name='" + name + '\'' +
                '}';
    }
}
