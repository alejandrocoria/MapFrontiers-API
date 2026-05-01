package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Persistence model for an existing frontier or collection snapshot.
 * <p>
 * This is a public observable property of data after creation. The API exposes it on already created entities because
 * it describes runtime behavior such as persistence, sharing, and authoritative lifecycle constraints. Temporary
 * creation is expressed by dedicated client create methods, not by fields in create requests.
 */
public enum EntityLifetime {
    /**
     * Entity follows the normal persisted and synchronized lifecycle.
     */
    PERSISTENT,

    /**
     * Entity exists only for the current client runtime, is never persisted, and is not part of the authoritative
     * server lifecycle.
     */
    SESSION_ONLY
}
