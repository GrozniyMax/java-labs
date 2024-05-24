public class Weightlessness extends Disaster{
    @Override
    public void changeCondition(ICanBeUnderEffect object) {
        object.changeSelfCondition(Condition.WEIGHTLESS);
    }

    @Override
    public String toString() {
        return "Weightlessness{}";
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
