package cyb.periodicals.entities.subscription;

public enum Period {
    MONTHLY("monthly"),
    YEARLY("yearly");

    private final String name;

    Period(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
