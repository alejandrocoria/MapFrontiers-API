package games.alejandrocoria.mapfrontiers.api.internal;

import games.alejandrocoria.mapfrontiers.api.client.FrontierActionResult;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierCreateRequest;
import games.alejandrocoria.mapfrontiers.api.model.FrontierDataView;
import games.alejandrocoria.mapfrontiers.api.model.FrontierId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierMutation;
import games.alejandrocoria.mapfrontiers.api.model.FrontierSharePermission;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Internal client-side frontier service that injects plugin context into each request.
 */
@SuppressWarnings("unused")
public interface PluginScopedClientFrontierService {
    /**
     * Returns the last frontier snapshot currently known to the client.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return cached frontier snapshot when known
     */
    Optional<FrontierDataView> getFrontier(String pluginModId, FrontierId frontierId);
    /**
     * Requests creation of a global frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param request initial frontier payload
     * @return request status
     */
    FrontierActionResult createGlobalFrontier(String pluginModId, FrontierCreateRequest request);
    /**
     * Requests update of a global frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param mutation partial update payload
     * @return request status
     */
    FrontierActionResult updateGlobalFrontier(String pluginModId, FrontierId frontierId, FrontierMutation mutation);
    /**
     * Requests deletion of a global frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult deleteGlobalFrontier(String pluginModId, FrontierId frontierId);
    /**
     * Requests conversion of a global frontier to personal within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult changeToPersonal(String pluginModId, FrontierId frontierId);
    /**
     * Lists cached global frontiers for the plugin scope and dimension.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param dimension target dimension
     * @return cached global frontier snapshots
     */
    List<FrontierDataView> listGlobalFrontiers(String pluginModId, DimensionId dimension);
    /**
     * Lists cached global frontiers in a collection for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return cached global frontier snapshots in the collection
     */
    List<FrontierDataView> listGlobalFrontiersInCollection(String pluginModId, CollectionId collectionId);
    /**
     * Requests creation of a persistent personal frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param request initial frontier payload
     * @return request status
     */
    FrontierActionResult createPersonalFrontier(String pluginModId, FrontierCreateRequest request);
    /**
     * Requests creation of a temporary personal frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param request initial frontier payload
     * @return request status
     */
    FrontierActionResult createTemporaryPersonalFrontier(String pluginModId, FrontierCreateRequest request);
    /**
     * Requests update of a personal frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param mutation partial update payload
     * @return request status
     */
    FrontierActionResult updatePersonalFrontier(String pluginModId, FrontierId frontierId, FrontierMutation mutation);
    /**
     * Requests deletion of a personal frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult deletePersonalFrontier(String pluginModId, FrontierId frontierId);
    /**
     * Requests conversion of a personal frontier to global within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult changeToGlobal(String pluginModId, FrontierId frontierId);
    /**
     * Lists cached personal frontiers for the plugin scope and dimension.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param dimension target dimension
     * @return cached personal frontier snapshots
     */
    List<FrontierDataView> listPersonalFrontiers(String pluginModId, DimensionId dimension);
    /**
     * Lists cached personal frontiers in a collection for the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param collectionId target collection id
     * @return cached personal frontier snapshots in the collection
     */
    List<FrontierDataView> listPersonalFrontiersInCollection(String pluginModId, CollectionId collectionId);
    /**
     * Requests sharing a personal frontier within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param user shared user
     * @param permissions permissions to grant
     * @return request status
     */
    FrontierActionResult sharePersonalFrontier(String pluginModId, FrontierId frontierId, UserRef user, Set<FrontierSharePermission> permissions);
    /**
     * Requests replacing permissions for a shared user within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param user shared user
     * @param permissions permissions to persist
     * @return request status
     */
    FrontierActionResult updateSharedUserPermissions(String pluginModId, FrontierId frontierId, UserRef user, Set<FrontierSharePermission> permissions);
    /**
     * Requests partial permission changes for a shared user within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param user shared user
     * @param permissionsToAdd permissions to add
     * @param permissionsToRemove permissions to remove
     * @return request status
     */
    FrontierActionResult updateSharedUserPermissions(String pluginModId,
                                                     FrontierId frontierId,
                                                     UserRef user,
                                                     Set<FrontierSharePermission> permissionsToAdd,
                                                     Set<FrontierSharePermission> permissionsToRemove);
    /**
     * Requests removal of a shared user within the plugin scope.
     *
     * @param pluginModId source plugin id injected by the wrapper
     * @param frontierId target frontier id
     * @param user shared user
     * @return request status
     */
    FrontierActionResult removeSharedUser(String pluginModId, FrontierId frontierId, UserRef user);
}
