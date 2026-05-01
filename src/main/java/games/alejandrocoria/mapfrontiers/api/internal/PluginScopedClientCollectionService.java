package games.alejandrocoria.mapfrontiers.api.internal;

import games.alejandrocoria.mapfrontiers.api.client.CollectionActionResult;
import games.alejandrocoria.mapfrontiers.api.model.CollectionCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.CollectionMutation;

import java.util.List;
import java.util.Optional;

/**
 * Internal client-side collection service that injects plugin context into each request.
 */
@SuppressWarnings("unused")
public interface PluginScopedClientCollectionService {
    /**
     * Returns the last collection snapshot currently known to the client.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return cached collection snapshot when known
     */
    Optional<CollectionDataView> getCollection(String pluginModId, CollectionId collectionId);
    /**
     * Lists cached global collections for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @return cached global collection snapshots
     */
    List<CollectionDataView> listGlobalCollections(String pluginModId);
    /**
     * Lists cached personal collections for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @return cached personal collection snapshots
     */
    List<CollectionDataView> listPersonalCollections(String pluginModId);
    /**
     * Requests creation of a global collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param request initial collection payload
     * @return request status
     */
    CollectionActionResult createGlobalCollection(String pluginModId, CollectionCreateRequest request);
    /**
     * Requests creation of a personal collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param request initial collection payload
     * @return request status
     */
    CollectionActionResult createPersonalCollection(String pluginModId, CollectionCreateRequest request);
    /**
     * Requests creation of a temporary personal collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param request initial collection payload
     * @return request status
     */
    CollectionActionResult createTemporaryPersonalCollection(String pluginModId, CollectionCreateRequest request);
    /**
     * Requests update of a global collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @param mutation partial update payload
     * @return request status
     */
    CollectionActionResult updateGlobalCollection(String pluginModId, CollectionId collectionId, CollectionMutation mutation);
    /**
     * Requests update of a personal collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @param mutation partial update payload
     * @return request status
     */
    CollectionActionResult updatePersonalCollection(String pluginModId, CollectionId collectionId, CollectionMutation mutation);
    /**
     * Requests deletion of a global collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return request status
     */
    CollectionActionResult deleteGlobalCollection(String pluginModId, CollectionId collectionId);
    /**
     * Requests deletion of a personal collection within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return request status
     */
    CollectionActionResult deletePersonalCollection(String pluginModId, CollectionId collectionId);
}
