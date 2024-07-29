package src;

/**
 * An enum representing the different TransportTypes (Bus, Train, Plane)
 */
public enum TransportType {
    BUS("bus"),
    TRAIN("train"),
    PLANE("plane");

    private final String label;

    TransportType(String label) {
        this.label = label;
    }

    /**
     * returns the enum corresponding to the inputted string
     *
     * @param typeString string to be converted to enum
     * @return TransportType enum
     */
    public static TransportType fromString(String typeString) {
        return switch (typeString.toLowerCase()) {
            case "bus" -> BUS;
            case "train" -> TRAIN;
            case "plane" -> PLANE;
            default -> throw new IllegalArgumentException("String: '" + typeString
                + "' not a valid TransportType");
        };
    }

    /**
     * accessor to get the TransportType's label, for instance "bus" if enum is BUS
     * @return the TransportType's label
     */
    public String getLabel() {
        return this.label;
    }
}