package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Dimension identifier string, usually a namespaced id like {@code minecraft:overworld}.
 *
 * @param value dimension identifier
 */
public record DimensionId(String value) {
    /**
     * Validates the wrapped dimension identifier.
     *
     * @param value dimension identifier
     */
    public DimensionId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("DimensionId cannot be blank");
        }
    }
}
