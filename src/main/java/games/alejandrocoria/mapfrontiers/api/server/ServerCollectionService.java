package games.alejandrocoria.mapfrontiers.api.server;

import games.alejandrocoria.mapfrontiers.api.model.CollectionCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.CollectionMutation;
import games.alejandrocoria.mapfrontiers.api.model.DefaultValuesProfile;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;

import java.util.List;
import java.util.Optional;

/**
 * Server-side collection operations.
 * <p>
 * Methods in this service mutate authoritative server state immediately. Collection requests and mutations can include
 * authoritative visibility and banner metadata when supported by the underlying mod runtime.
 */
@SuppressWarnings("unused")
public interface ServerCollectionService {
    /**
     * Creates a global collection directly on server state.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} is currently unsupported in the
     * server API and may be rejected by the implementation with {@link IllegalArgumentException} when the request is
     * processed. Omitting optional fields, or passing null to optional create setters, delegates those values to the
     * selected base profile.
     *
     * @param owner owner to persist in the created collection
     * @param request initial collection payload
     * @return created collection snapshot
     * @throws IllegalArgumentException when the implementation rejects an unsupported default value profile
     */
    CollectionDataView createGlobalCollection(UserRef owner, CollectionCreateRequest request);

    /**
     * Updates a global collection directly on server state.
     *
     * @param collectionId target collection id
     * @param mutation partial update payload
     * @return updated collection snapshot, or empty when not found or not global
     */
    Optional<CollectionDataView> updateGlobalCollection(CollectionId collectionId, CollectionMutation mutation);

    /**
     * Deletes a global collection directly on server state.
     *
     * @param collectionId target collection id
     * @return true when deleted
     */
    boolean deleteGlobalCollection(CollectionId collectionId);

    /**
     * Lists global collection snapshots.
     *
     * @return global collection snapshots
     */
    List<CollectionDataView> listGlobalCollections();

    /**
     * Returns a global collection snapshot by id.
     *
     * @param collectionId target collection id
     * @return empty when id is unknown or references a personal collection
     */
    Optional<CollectionDataView> getCollection(CollectionId collectionId);
}
