package cyb.periodicals.entities.user;

public enum Role {
    CLIENT("client"),
    USER("user");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
