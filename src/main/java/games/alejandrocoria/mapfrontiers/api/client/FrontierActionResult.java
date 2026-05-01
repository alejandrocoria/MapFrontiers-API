package games.alejandrocoria.mapfrontiers.api.client;

import games.alejandrocoria.mapfrontiers.api.model.FrontierDataView;
import games.alejandrocoria.mapfrontiers.api.model.FrontierId;

import java.util.Optional;

/**
 * Result envelope returned by client-side frontier operations.
 *
 * @param status outcome category for the request
 * @param frontierId id associated with the request when known
 * @param frontier frontier snapshot when the operation was applied locally
 */
public record FrontierActionResult(ActionStatus status,
                                   Optional<FrontierId> frontierId,
                                   Optional<FrontierDataView> frontier) {

    /**
     * Creates a result indicating the operation was applied immediately.
     *
     * @param frontier resulting frontier snapshot
     * @return applied result
     */
    public static FrontierActionResult applied(FrontierDataView frontier) {
        return new FrontierActionResult(ActionStatus.APPLIED_LOCAL, Optional.of(frontier.id()), Optional.of(frontier));
    }

    /**
     * Creates a result indicating the request was accepted for asynchronous server processing.
     *
     * @param frontierId target frontier id when already known
     * @return accepted result
     */
    public static FrontierActionResult acceptedAsync(FrontierId frontierId) {
        return new FrontierActionResult(ActionStatus.ACCEPTED_ASYNC, Optional.of(frontierId), Optional.empty());
    }

    /**
     * Creates a result indicating the target id was not found.
     *
     * @param frontierId target frontier id
     * @return not-found result
     */
    public static FrontierActionResult notFound(FrontierId frontierId) {
        return new FrontierActionResult(ActionStatus.NOT_FOUND, Optional.of(frontierId), Optional.empty());
    }

    /**
     * Creates a result indicating the request was rejected.
     *
     * @return rejected result
     */
    public static FrontierActionResult rejected() {
        return new FrontierActionResult(ActionStatus.REJECTED, Optional.empty(), Optional.empty());
    }
}
