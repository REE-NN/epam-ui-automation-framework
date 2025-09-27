package driver;

public enum Browser {
    CHROME,
    FIREFOX,
    EDGE;

    public static Browser from(String name) {
        if (name == null || name.isBlank()) {
            return FIREFOX; // дефолт
        }
        try {
            return Browser.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown browser: " + name);
        }
    }
}
