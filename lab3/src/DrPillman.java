import java.util.Objects;

public class DrPillman extends Baby {
    public DrPillman() {
        super("Доктор Пилюлькин");
    }

    public String getReportData(City city){
        int normal=0;
        for (Baby citizen: city.getMembers()) {
            if (citizen.getCondition()== Condition.NORMAL){
                normal++;
            }
        }
        if (normal==city.getMembers().size()){
            return " все жители города в нормальном состоянии, жизнь в "+city.getName()+" идет своим чередом.";
        }
        else {
            return String.format("%d из %d жителей города в нормальном состоянии",normal,city.getMembers().size());
        }
    }
    public void toMakeReport(Baby object){
        super.toDo(object, Interaction.REPORT);
        System.out.println("что" + getReportData(object.getMotherland()));
    }

    @Override
    public String toString() {
        return "DrPillman{" +
                "isMemberOf=" + isMemberOf +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrPillman baby)) return false;
        return isCrowd == baby.isCrowd && Objects.equals(isMemberOf, baby.isMemberOf) && Objects.equals(name, baby.name) && condition == baby.condition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
