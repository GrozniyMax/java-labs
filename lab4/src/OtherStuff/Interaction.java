package OtherStuff;

import Babies.Baby;
import Exceptions.EatedNotEnoughException;
import OtherStuff.City;

/**
 * Описывает взаимодействие малышек между собой
 */
public enum Interaction {

    FEED("кормит","кормят"){
        @Override
        public void action(City subject, Baby object) throws EatedNotEnoughException {
            super.action(subject,object);
            object.beFed();
        }
    },
    REPORT("доложил",""),
    COMEBACK("вернулся из обхода");

    protected String singularForm, plurarForm;
    Interaction(String singularForm, String plurarForm) {
        this.singularForm=singularForm;
        this.plurarForm = plurarForm;
    }
    Interaction(String singularForm){
        this.singularForm=singularForm;
    }


    public void action(Baby subject, Baby object){

        System.out.println(subject.getName()+" " + this.singularForm+" " +object.getName());
    }
    public void action(City subject, Baby object) throws EatedNotEnoughException {

        System.out.println(City.PSEUDONIM+" "+this.plurarForm+" "+object.getName());
    }

    @Override
    public String toString() {
        return "Interaction{" +
                "singularForm='" + singularForm + '\'' +
                ", plurarForm='" + plurarForm + '\'' +
                '}';
    }
}
