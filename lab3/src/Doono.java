import java.util.Objects;

public class Doono extends Baby {
    public Doono() {
        super("Знайка");
    }
    public void heel(Baby baby){
        if (baby.getCondition() != Condition.NORMAL){
            baby.setCondition(Condition.NORMAL);
        }
    }

    public void heelCrowd(City city){
        for (Baby citizen :
                city.getMembers()) {
            if (citizen.isCrowd) {
                this.heel(citizen);
            }
            }
    }

    @Override
    public String toString() {
        return "Doono{" +
                "isMemberOf=" + super.isMemberOf.toString() +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doono baby)) return false;
        return isCrowd == baby.isCrowd && Objects.equals(isMemberOf, baby.isMemberOf) && Objects.equals(name, baby.name) && condition == baby.condition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
