package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Identifier for a path marker style.
 * <p>
 * Built-in constants are convenience IDs; implementations may also accept marker IDs provided by resources or other mods.
 */
public record PathMarkerId(String value) {
    public static final PathMarkerId NONE = new PathMarkerId("mapfrontiers:none");
    public static final PathMarkerId BIG_DOT = new PathMarkerId("mapfrontiers:big_dot");
    public static final PathMarkerId SMALL_DOT = new PathMarkerId("mapfrontiers:small_dot");
    public static final PathMarkerId RING = new PathMarkerId("mapfrontiers:ring");
    public static final PathMarkerId BIG_SQUARE = new PathMarkerId("mapfrontiers:big_square");
    public static final PathMarkerId SMALL_SQUARE = new PathMarkerId("mapfrontiers:small_square");
    public static final PathMarkerId DIAMOND = new PathMarkerId("mapfrontiers:diamond");
    public static final PathMarkerId TRIANGLE = new PathMarkerId("mapfrontiers:triangle");
    public static final PathMarkerId ARROW = new PathMarkerId("mapfrontiers:arrow");
    public static final PathMarkerId CHEVRON = new PathMarkerId("mapfrontiers:chevron");
    public static final PathMarkerId X_CROSS = new PathMarkerId("mapfrontiers:x_cross");

    public PathMarkerId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Path marker id cannot be null or blank");
        }
    }
}
