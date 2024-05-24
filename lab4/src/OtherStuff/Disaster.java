package OtherStuff;

import OtherStuff.ICanBeUnderEffect;

public abstract class Disaster {

    public   void happen(ICanBeUnderEffect object){
        System.out.println("Что-то случилось");
        changeCondition(object);
    }
    public abstract void changeCondition(ICanBeUnderEffect object);

    @Override
    public String toString() {
        return "OtherStuff.Disaster{}";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
