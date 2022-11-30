package plans;

public enum Option {
    CALLS("MINIMUM"),
    CALLS_EXTENDED("STANDARD"),
    INTERNET("PREMIUM"),
    INTERNET_EXTENDED("PREMIUM"),
    ROAMING("ROAMING");

    private final String name;

    Option(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
