package OtherStuff;

import Babies.Baby;

import java.util.ArrayList;

public class ReportData {

    private ArrayList<Baby> OKBabies;
    private ArrayList<Baby> UnderEffectBabies;

    public ReportData() {
        this.OKBabies = new ArrayList<>();
        this.UnderEffectBabies = new ArrayList<>();
    }

    public void addOkBaby(Baby baby){
        this.OKBabies.add(baby);
    }
    public void addUnderEffectBaby(Baby baby){
        this.UnderEffectBabies.add(baby);
    }

    public String getStringInterpretation(){

        return String.format("%d из %d находяться в состоянии невесомости", this.UnderEffectBabies.size(),this.UnderEffectBabies.size()+this.OKBabies.size());
    }

    public ArrayList<Baby> getOKBabies() {
        return OKBabies;
    }

    public ArrayList<Baby> getUnderEffectBabies() {
        return UnderEffectBabies;
    }
}
