package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Persistence model for a frontier snapshot.
 */
public enum FrontierLifetime {
    /**
     * Frontier follows the normal persisted and synchronized lifecycle.
     */
    PERSISTENT,

    /**
     * Frontier exists only for the current client runtime and is never persisted.
     */
    SESSION_ONLY
}
