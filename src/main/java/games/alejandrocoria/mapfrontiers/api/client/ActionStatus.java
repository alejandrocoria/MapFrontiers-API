package games.alejandrocoria.mapfrontiers.api.client;

/**
 * Outcome category for a client-side API request.
 */
public enum ActionStatus {
    /**
     * Applied immediately to client-managed state without awaiting logical-server processing. Successful operations
     * on session-only personal entities use this status regardless of server availability.
     */
    APPLIED_LOCAL,
    /**
     * Accepted and forwarded to the logical server; final result arrives via events/state updates.
     */
    ACCEPTED_ASYNC,
    /**
     * Target entity id was not found in currently known state.
     */
    NOT_FOUND,
    /**
     * Request was rejected (for example by permissions or unsupported context).
     */
    REJECTED
}
