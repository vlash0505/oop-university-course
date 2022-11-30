package plans;

public enum Option {
    CALLS("calls"),
    CALLS_EXTENDED("calls extended"),
    INTERNET("internet"),
    INTERNET_EXTENDED("internet extended"),
    ROAMING("roaming");

    private final String name;

    Option(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
