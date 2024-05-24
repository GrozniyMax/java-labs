import java.util.Objects;


public class Baby implements ICanBeUnderEffect, ICanDo {
    static int crowdCount;
    protected City isMemberOf;
    protected String name;
    protected Condition condition;

    public boolean isCrowd;

    {
        this.condition = Condition.NORMAL;
        isCrowd = false;
    }

    public void liveInTown(City city) {
        this.isMemberOf = city;
        System.out.printf("%s теперь живет в %s \n", this.getName(), city.getName());
    }

    public Baby() {
        this.name = String.format("Безымянный%d", Baby.crowdCount);
        this.isCrowd = true;
        Baby.crowdCount++;
    }


    public Baby(String name) {
        this.name = name;
    }


    @Override
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

    public City getMotherland() {
        return isMemberOf;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }


    @Override
    public String toString() {
        return "Baby{" +
                "isMemberOf=" + isMemberOf.toString() +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Baby baby)) return false;
        return isCrowd == baby.isCrowd && Objects.equals(isMemberOf, baby.isMemberOf) && Objects.equals(name, baby.name) && condition == baby.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isMemberOf, name, condition, isCrowd);
    }
}
