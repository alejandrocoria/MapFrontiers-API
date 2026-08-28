package games.alejandrocoria.mapfrontiers.api.client;

import games.alejandrocoria.mapfrontiers.api.model.DefaultValuesProfile;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.EntityLifetime;
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
 * Client-side frontier operations.
 * <p>
 * Methods that mutate data usually return quickly with {@link ActionStatus#ACCEPTED_ASYNC}
 * and are finalized by logical-server updates (including singleplayer). Operations on
 * {@link EntityLifetime#SESSION_ONLY session-only} personal frontiers are applied immediately to local state instead
 * and do not depend on server participation.
 */
@SuppressWarnings("unused")
public interface ClientFrontierService {
    /**
     * Returns the last frontier snapshot currently known by the client.
     * This method does not trigger network requests.
     *
     * @param frontierId target frontier id
     * @return cached snapshot when known
     */
    Optional<FrontierDataView> getFrontier(FrontierId frontierId);

    /**
     * Requests creation of a global frontier from the client side.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} uses the local player's
     * configured defaults as the base for omitted fields. Omitting optional fields, or passing null to optional create
     * setters, delegates those values to the selected base profile.
     *
     * @param request initial frontier payload
     * @return request status and optional target id
     */
    FrontierActionResult createGlobalFrontier(FrontierCreateRequest request);

    /**
     * Requests an update for a global frontier.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     * {@link ActionStatus#ACCEPTED_ASYNC} confirms local validation and submission, not final server application.
     *
     * @param frontierId target frontier id
     * @param mutation partial update payload
     * @return request status
     */
    FrontierActionResult updateGlobalFrontier(FrontierId frontierId, FrontierMutation mutation);

    /**
     * Requests deletion of a global frontier.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     *
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult deleteGlobalFrontier(FrontierId frontierId);

    /**
     * Requests conversion of a global frontier to personal for the current client actor.
     * In multiplayer and singleplayer this is handled asynchronously by the logical server.
     *
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult changeToPersonal(FrontierId frontierId);

    /**
     * Returns global frontiers currently cached on the client for the dimension.
     *
     * @param dimension target dimension
     * @return cached global frontier snapshots
     */
    List<FrontierDataView> listGlobalFrontiers(DimensionId dimension);

    /**
     * Requests creation of a persistent personal frontier owned by the current client actor.
     * Temporary creation is exposed by {@link #createTemporaryPersonalFrontier(FrontierCreateRequest)}.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} uses the local player's
     * configured defaults as the base for omitted fields. Omitting optional fields, or passing null to optional create
     * setters, delegates those values to the selected base profile.
     *
     * @param request initial frontier payload
     * @return request status and optional target id
     */
    FrontierActionResult createPersonalFrontier(FrontierCreateRequest request);

    /**
     * Requests creation of a session-only personal frontier.
     * {@link EntityLifetime#SESSION_ONLY Session-only} frontiers are local-only, are not persisted, are not shareable,
     * are never sent to the server, and disappear when the current client runtime is closed.
     * A successful request is applied immediately and returns {@link ActionStatus#APPLIED_LOCAL} with the created id
     * and snapshot.
     * Requests created through convenience overloads that do not take a profile use
     * {@link DefaultValuesProfile#BUILTIN}. {@link DefaultValuesProfile#CONFIGURED} uses the local player's
     * configured defaults as the base for omitted fields. Omitting optional fields, or passing null to optional create
     * setters, delegates those values to the selected base profile.
     *
     * @param request initial frontier payload
     * @return local result with the created id and snapshot when successful
     */
    FrontierActionResult createTemporaryPersonalFrontier(FrontierCreateRequest request);

    /**
     * Requests an update for a personal frontier.
     * In singleplayer this is handled asynchronously by the logical server.
     * In multiplayer this is asynchronous when the mod is present on the server, and may be handled locally when it is not.
     * If the target is {@link EntityLifetime#SESSION_ONLY session-only}, the update is instead applied immediately to
     * local state regardless of server availability and returns {@link ActionStatus#APPLIED_LOCAL} when successful.
     * {@link ActionStatus#ACCEPTED_ASYNC} confirms local validation and submission, not final server application.
     *
     * @param frontierId target frontier id
     * @param mutation partial update payload
     * @return request status
     */
    FrontierActionResult updatePersonalFrontier(FrontierId frontierId, FrontierMutation mutation);

    /**
     * Requests deletion of a personal frontier.
     * In singleplayer this is handled asynchronously by the logical server.
     * In multiplayer this is asynchronous when the mod is present on the server, and may be handled locally when it is not.
     * If the target is {@link EntityLifetime#SESSION_ONLY session-only}, the deletion is instead applied immediately to
     * local state regardless of server availability and returns {@link ActionStatus#APPLIED_LOCAL} when successful.
     *
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult deletePersonalFrontier(FrontierId frontierId);

    /**
     * Requests conversion of a personal frontier to global.
     * {@link EntityLifetime#SESSION_ONLY Session-only} personal frontiers cannot be converted.
     * In singleplayer this is handled asynchronously by the logical server.
     * In multiplayer this is asynchronous when the mod is present on the server, and may be handled locally when it is not.
     *
     * @param frontierId target frontier id
     * @return request status
     */
    FrontierActionResult changeToGlobal(FrontierId frontierId);

    /**
     * Returns personal frontiers currently cached on the client for the dimension.
     *
     * @param dimension target dimension
     * @return cached personal frontier snapshots
     */
    List<FrontierDataView> listPersonalFrontiers(DimensionId dimension);

    /**
     * Requests sharing a personal frontier with another user.
     * {@link EntityLifetime#SESSION_ONLY Session-only} personal frontiers are rejected.
     * This requires the mod to be present on the server.
     * In singleplayer and in multiplayer with the mod on the server, this is handled asynchronously by the logical server.
     * In multiplayer without the mod on the server, this request is rejected.
     *
     * @param frontierId target frontier id
     * @param user user to share with
     * @param permissions permissions to grant; null is treated as an empty set
     * @return request status
     */
    FrontierActionResult sharePersonalFrontier(FrontierId frontierId, UserRef user, Set<FrontierSharePermission> permissions);

    /**
     * Requests updating permissions for an already shared user.
     * {@link EntityLifetime#SESSION_ONLY Session-only} personal frontiers are rejected.
     * This requires the mod to be present on the server.
     * In singleplayer and in multiplayer with the mod on the server, this is handled asynchronously by the logical server.
     * In multiplayer without the mod on the server, this request is rejected.
     *
     * @param frontierId target frontier id
     * @param user shared user to update
     * @param permissions permissions to persist; null is treated as an empty set
     * @return request status
     */
    FrontierActionResult updateSharedUserPermissions(FrontierId frontierId, UserRef user, Set<FrontierSharePermission> permissions);

    /**
     * Requests a partial permission update for an already shared user.
     * {@link EntityLifetime#SESSION_ONLY Session-only} personal frontiers are rejected.
     * This requires the mod to be present on the server.
     * In singleplayer and in multiplayer with the mod on the server, this is handled asynchronously by the logical server.
     * In multiplayer without the mod on the server, this request is rejected.
     *
     * @param frontierId target frontier id
     * @param user shared user to update
     * @param permissionsToAdd permissions to add; null is treated as an empty set
     * @param permissionsToRemove permissions to remove; null is treated as an empty set
     * @return request status
     */
    FrontierActionResult updateSharedUserPermissions(FrontierId frontierId,
                                                     UserRef user,
                                                     Set<FrontierSharePermission> permissionsToAdd,
                                                     Set<FrontierSharePermission> permissionsToRemove);

    /**
     * Requests removing a shared user from a personal frontier.
     * {@link EntityLifetime#SESSION_ONLY Session-only} personal frontiers are rejected.
     * This requires the mod to be present on the server.
     * In singleplayer and in multiplayer with the mod on the server, this is handled asynchronously by the logical server.
     * In multiplayer without the mod on the server, this request is rejected.
     *
     * @param frontierId target frontier id
     * @param user user to remove from sharing
     * @return request status
     */
    FrontierActionResult removeSharedUser(FrontierId frontierId, UserRef user);
}
