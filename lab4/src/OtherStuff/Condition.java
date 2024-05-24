package OtherStuff;

public enum Condition {
    NORMAL("обычном состоянии"),
    WEIGHTLESS("состоянии невесомости");

    Condition(String name) {
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "OtherStuff.Condition{" +
                "name='" + name + '\'' +
                '}';
    }

}
