package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Identifier for a path marker style.
 * <p>
 * Built-in constants are convenience IDs; implementations may also accept marker IDs provided by resources or other mods.
 *
 * @param value marker identifier
 */
public record PathMarkerId(String value) {
    /** Marker-less style. */
    public static final PathMarkerId NONE = new PathMarkerId("mapfrontiers:none");
    /** Large dot marker. */
    public static final PathMarkerId BIG_DOT = new PathMarkerId("mapfrontiers:big_dot");
    /** Small dot marker. */
    public static final PathMarkerId SMALL_DOT = new PathMarkerId("mapfrontiers:small_dot");
    /** Ring marker. */
    public static final PathMarkerId RING = new PathMarkerId("mapfrontiers:ring");
    /** Large square marker. */
    public static final PathMarkerId BIG_SQUARE = new PathMarkerId("mapfrontiers:big_square");
    /** Small square marker. */
    public static final PathMarkerId SMALL_SQUARE = new PathMarkerId("mapfrontiers:small_square");
    /** Diamond marker. */
    public static final PathMarkerId DIAMOND = new PathMarkerId("mapfrontiers:diamond");
    /** Triangle marker. */
    public static final PathMarkerId TRIANGLE = new PathMarkerId("mapfrontiers:triangle");
    /** Arrow marker. */
    public static final PathMarkerId ARROW = new PathMarkerId("mapfrontiers:arrow");
    /** Chevron marker. */
    public static final PathMarkerId CHEVRON = new PathMarkerId("mapfrontiers:chevron");
    /** X-cross marker. */
    public static final PathMarkerId X_CROSS = new PathMarkerId("mapfrontiers:x_cross");

    /**
     * Validates the wrapped marker id.
     *
     * @param value marker identifier
     */
    public PathMarkerId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Path marker id cannot be null or blank");
        }
    }
}
