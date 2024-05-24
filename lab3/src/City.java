import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class City implements ICanBeUnderEffect, ICanDo {
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
        for (int i = 0; i < number; i++) {
            Baby futureCitizen = new Baby();
            futureCitizen.liveInTown(this);
            members.add(futureCitizen);
        }
        System.out.println();
    }

    @Override
    public void changeSelfCondition(Condition newCondition) {
        for (Baby citizen : members) {
            citizen.changeSelfCondition(newCondition);
        }
        System.out.printf("То есть, все теперь в состоянии %s\n",newCondition.getName());
        System.out.println(" ");
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
    public void toDo(Baby object, Interaction activity) {
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
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }
}
