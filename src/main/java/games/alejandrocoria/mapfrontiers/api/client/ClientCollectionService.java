package games.alejandrocoria.mapfrontiers.api.client;

import games.alejandrocoria.mapfrontiers.api.model.CollectionCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.CollectionMutation;
import games.alejandrocoria.mapfrontiers.api.model.DefaultValuesProfile;
import games.alejandrocoria.mapfrontiers.api.model.EntityLifetime;

import java.util.List;
import java.util.Optional;

/**
 * Client-side collection operations.
 * <p>
 * Most methods that mutate data return quickly with {@link ActionStatus#ACCEPTED_ASYNC} and are finalized by
 * logical-server updates (including singleplayer). Session-only personal collection creation is the main exception and
 * may be applied locally without server participation. Collection requests and mutations can include authoritative
 * visibility and banner metadata when supported by the underlying mod runtime.
 */
@SuppressWarnings("unused")
public interface ClientCollectionService {
    /**
     * Returns the last collection snapshot currently known by the client.
     * This method does not trigger network requests.
     *
     * @param collectionId target collection id
     * @return cached snapshot when known
     */
    Optional<CollectionDataView> getCollection(CollectionId collectionId);

    /**
     * Returns global collections currently cached on the client.
     *
     * @return cached global collection snapshots
     */
    List<CollectionDataView> listGlobalCollections();

    /**
     * Returns personal collections currently cached on the client.
     *
     * @return cached personal collection snapshots
     */
    List<CollectionDataView> listPersonalCollections();

    /**
     * Requests creation of a global collection from the client side.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} uses the local player's
     * configured defaults as the base for omitted fields. Omitting optional fields, or passing null to optional create
     * setters, delegates those values to the selected base profile.
     *
     * @param request initial collection payload
     * @return request status and optional target id
     */
    CollectionActionResult createGlobalCollection(CollectionCreateRequest request);

    /**
     * Requests creation of a personal collection owned by the current client actor.
     * In singleplayer this is handled asynchronously by the logical server.
     * In multiplayer this is asynchronous when the mod is present on the server, and may be handled locally when it is not.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} uses the local player's
     * configured defaults as the base for omitted fields. Omitting optional fields, or passing null to optional create
     * setters, delegates those values to the selected base profile.
     *
     * @param request initial collection payload
     * @return request status and optional target id
     */
    CollectionActionResult createPersonalCollection(CollectionCreateRequest request);

    /**
     * Requests creation of a session-only personal collection.
     * {@link EntityLifetime#SESSION_ONLY Session-only} collections are local-only, are not persisted, are never sent to
     * the server, and disappear when the current client runtime is closed.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} uses the local player's
     * configured defaults as the base for omitted fields. Omitting optional fields, or passing null to optional create
     * setters, delegates those values to the selected base profile.
     *
     * @param request initial collection payload
     * @return request status and optional target id
     */
    CollectionActionResult createTemporaryPersonalCollection(CollectionCreateRequest request);

    /**
     * Requests an update for a global collection.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     *
     * @param collectionId target collection id
     * @param mutation partial update payload
     * @return request status
     */
    CollectionActionResult updateGlobalCollection(CollectionId collectionId, CollectionMutation mutation);

    /**
     * Requests an update for a personal collection.
     * In singleplayer this is handled asynchronously by the logical server.
     * In multiplayer this is asynchronous when the mod is present on the server, and may be handled locally when it is not.
     *
     * @param collectionId target collection id
     * @param mutation partial update payload
     * @return request status
     */
    CollectionActionResult updatePersonalCollection(CollectionId collectionId, CollectionMutation mutation);

    /**
     * Requests deletion of a global collection.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     *
     * @param collectionId target collection id
     * @return request status
     */
    CollectionActionResult deleteGlobalCollection(CollectionId collectionId);

    /**
     * Requests deletion of a personal collection.
     * In singleplayer this is handled asynchronously by the logical server.
     * In multiplayer this is asynchronous when the mod is present on the server, and may be handled locally when it is not.
     *
     * @param collectionId target collection id
     * @return request status
     */
    CollectionActionResult deletePersonalCollection(CollectionId collectionId);
}
