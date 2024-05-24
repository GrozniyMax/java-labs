package Babies;

import OtherStuff.City;
import OtherStuff.Condition;
import OtherStuff.Interaction;
import OtherStuff.ReportData;

import java.util.Objects;

public class DrPillman extends Baby {
    public DrPillman() {
        super("Доктор Пилюлькин");
    }

    public ReportData getReportData(City city){

        int normal=0;
        ReportData data = new ReportData();
        for (Baby citizen: city.getMembers()) {
            if (citizen.getCondition()== Condition.NORMAL){
                normal++;
                data.addOkBaby(citizen);
            }
            else{
                data.addUnderEffectBaby(citizen);
            }
        }
        return data;
    }
    public void toMakeReport(Doono object){
        super.toDo(object, Interaction.REPORT);
        ReportData reportData = getReportData(object.getCity());
        object.receiveInfo(reportData);
        System.out.println("что " + reportData.getStringInterpretation());
    }

    @Override
    public String toString() {
        return "Babies.DrPillman{" +
                "city=" + city +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrPillman baby)) return false;
        return isCrowd == baby.isCrowd && Objects.equals(city, baby.city) && Objects.equals(name, baby.name) && condition == baby.condition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
