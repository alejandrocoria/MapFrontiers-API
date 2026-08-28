package games.alejandrocoria.mapfrontiers.api.client;

import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;

import java.util.Optional;

/**
 * Result envelope returned by client-side collection operations.
 *
 * @param status outcome category for the request
 * @param collectionId id associated with the request when known; present in service results applied locally
 * @param collection collection snapshot when the operation was applied locally; present in service results with
 *                   {@link ActionStatus#APPLIED_LOCAL}
 */
public record CollectionActionResult(ActionStatus status,
                                     Optional<CollectionId> collectionId,
                                     Optional<CollectionDataView> collection) {

    /**
     * Creates a result indicating the operation was applied immediately, including its id and resulting snapshot.
     *
     * @param collection resulting collection snapshot
     * @return applied result
     */
    public static CollectionActionResult applied(CollectionDataView collection) {
        return new CollectionActionResult(ActionStatus.APPLIED_LOCAL, Optional.of(collection.id()), Optional.of(collection));
    }

    /**
     * Creates a result indicating the request was accepted for asynchronous server processing.
     *
     * @param collectionId target collection id when already known
     * @return accepted result
     */
    public static CollectionActionResult acceptedAsync(CollectionId collectionId) {
        return new CollectionActionResult(ActionStatus.ACCEPTED_ASYNC, Optional.of(collectionId), Optional.empty());
    }

    /**
     * Creates a result indicating the target id was not found.
     *
     * @param collectionId target collection id
     * @return not-found result
     */
    public static CollectionActionResult notFound(CollectionId collectionId) {
        return new CollectionActionResult(ActionStatus.NOT_FOUND, Optional.of(collectionId), Optional.empty());
    }

    /**
     * Creates a result indicating the request was rejected.
     *
     * @return rejected result
     */
    public static CollectionActionResult rejected() {
        return new CollectionActionResult(ActionStatus.REJECTED, Optional.empty(), Optional.empty());
    }
}
