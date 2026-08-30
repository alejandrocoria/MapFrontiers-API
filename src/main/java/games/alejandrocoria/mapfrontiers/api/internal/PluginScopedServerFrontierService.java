package games.alejandrocoria.mapfrontiers.api.internal;

import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.FrontierDataView;
import games.alejandrocoria.mapfrontiers.api.model.FrontierId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierMutation;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;

import java.util.List;
import java.util.Optional;

/**
 * Internal server-side frontier service that injects plugin context into each request.
 */
@SuppressWarnings("unused")
public interface PluginScopedServerFrontierService {
    /**
     * Creates a global frontier on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param owner frontier owner
     * @param request initial frontier payload
     * @return created frontier snapshot
     */
    FrontierDataView createGlobalFrontier(String pluginModId, UserRef owner, FrontierCreateRequest request);
    /**
     * Updates a global frontier on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param mutation partial update payload
     * @return updated frontier snapshot when found
     */
    Optional<FrontierDataView> updateGlobalFrontier(String pluginModId, FrontierId frontierId, FrontierMutation mutation);
    /**
     * Deletes a global frontier on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return true when deleted
     */
    boolean deleteGlobalFrontier(String pluginModId, FrontierId frontierId);
    /**
     * Lists global frontiers on authoritative server state for the plugin scope and dimension.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param dimension target dimension
     * @return global frontier snapshots
     */
    List<FrontierDataView> listGlobalFrontiers(String pluginModId, DimensionId dimension);
    /**
     * Lists global frontiers in a collection on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return global frontier snapshots in the collection
     */
    List<FrontierDataView> listGlobalFrontiersInCollection(String pluginModId, CollectionId collectionId);
    /**
     * Returns a global frontier snapshot for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return frontier snapshot when found
     */
    Optional<FrontierDataView> getFrontier(String pluginModId, FrontierId frontierId);
}
