package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Supported frontier geometry categories.
 */
public enum FrontierShapeType {
    /**
     * Closed polygon defined by vertices.
     */
    VERTEX,
    /**
     * Set of chunk coordinates.
     */
    CHUNK,
    /**
     * Open path defined by ordered points.
     */
    PATH
}
