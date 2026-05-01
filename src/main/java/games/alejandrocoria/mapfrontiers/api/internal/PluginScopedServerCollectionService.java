package games.alejandrocoria.mapfrontiers.api.internal;

import games.alejandrocoria.mapfrontiers.api.model.CollectionCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.CollectionMutation;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;

import java.util.List;
import java.util.Optional;

/**
 * Internal server-side collection service that injects plugin context into each request.
 */
@SuppressWarnings("unused")
public interface PluginScopedServerCollectionService {
    /**
     * Creates a global collection on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param owner collection owner
     * @param request initial collection payload
     * @return created collection snapshot
     */
    CollectionDataView createGlobalCollection(String pluginModId, UserRef owner, CollectionCreateRequest request);
    /**
     * Updates a global collection on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @param mutation partial update payload
     * @return updated collection snapshot when found
     */
    Optional<CollectionDataView> updateGlobalCollection(String pluginModId, CollectionId collectionId, CollectionMutation mutation);
    /**
     * Deletes a global collection on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return true when deleted
     */
    boolean deleteGlobalCollection(String pluginModId, CollectionId collectionId);
    /**
     * Lists global collections on authoritative server state for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @return global collection snapshots
     */
    List<CollectionDataView> listGlobalCollections(String pluginModId);
    /**
     * Returns a global collection snapshot for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return collection snapshot when found
     */
    Optional<CollectionDataView> getCollection(String pluginModId, CollectionId collectionId);
}
