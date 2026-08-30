package games.alejandrocoria.mapfrontiers.api.server;

import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.DefaultValuesProfile;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.FrontierDataView;
import games.alejandrocoria.mapfrontiers.api.model.FrontierId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierMutation;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;

import java.util.List;
import java.util.Optional;

/**
 * Server-side frontier operations.
 * <p>
 * Methods in this service mutate authoritative server state immediately.
 */
@SuppressWarnings("unused")
public interface ServerFrontierService {
    /**
     * Creates a global frontier directly on server state.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} is currently unsupported in the
     * server API and may be rejected by the implementation with {@link IllegalArgumentException} when the request is
     * processed. Omitting optional fields, or passing null to optional create setters, delegates those values to the
     * selected base profile.
     *
     * @param owner owner to persist in the created frontier
     * @param request initial frontier payload
     * @return created frontier snapshot
     * @throws IllegalArgumentException when the implementation rejects an unsupported default value profile
     */
    FrontierDataView createGlobalFrontier(UserRef owner, FrontierCreateRequest request);

    /**
     * Updates a global frontier directly on server state.
     *
     * @param frontierId target frontier id
     * @param mutation partial update payload
     * @return updated frontier snapshot, or empty when not found, not global, or the mutation cannot be applied
     */
    Optional<FrontierDataView> updateGlobalFrontier(FrontierId frontierId, FrontierMutation mutation);

    /**
     * Deletes a global frontier directly on server state.
     *
     * @param frontierId target frontier id
     * @return true when deleted
     */
    boolean deleteGlobalFrontier(FrontierId frontierId);

    /**
     * Lists global frontier snapshots for a dimension.
     *
     * @param dimension target dimension
     * @return global frontier snapshots
     */
    List<FrontierDataView> listGlobalFrontiers(DimensionId dimension);

    /**
     * Lists global frontier snapshots that belong to a collection.
     * An unknown, empty, or personal collection produces an empty list. The returned snapshots have no guaranteed
     * order.
     *
     * @param collectionId target collection id
     * @return global frontier snapshots in the collection
     */
    List<FrontierDataView> listGlobalFrontiersInCollection(CollectionId collectionId);

    /**
     * Returns a global frontier snapshot by id.
     *
     * @param frontierId target frontier id
     * @return empty when id is unknown or references a personal frontier
     */
    Optional<FrontierDataView> getFrontier(FrontierId frontierId);
}
