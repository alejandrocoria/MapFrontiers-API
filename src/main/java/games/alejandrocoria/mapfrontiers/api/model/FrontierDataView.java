package games.alejandrocoria.mapfrontiers.api.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Immutable snapshot view of frontier data exposed by the API.
 * <p>
 * For now, MapFrontiers limits {@code name1} and {@code name2} to 48 characters each.
 * Lifetime is immutable after creation.
 *
 * @param id frontier id
 * @param type frontier scope
 * @param lifetime persistence model of the created frontier
 * @param dimension dimension that contains the frontier
 * @param color configured color
 * @param name1 first display name field
 * @param name2 second display name field
 * @param shape frontier geometry
 * @param visibility visibility flags visible through the API
 * @param banner banner data when configured, or null when the frontier has no banner
 * @param pathStyle optional path styling when the frontier is path-based
 * @param collectionId optional collection membership
 * @param sourcePluginId source plugin id when the frontier originated from a plugin-scoped operation
 * @param owner owning user
 * @param sharedUsers shared-user access entries
 */
public record FrontierDataView(FrontierId id,
                               FrontierType type,
                               EntityLifetime lifetime,
                               DimensionId dimension,
                               int color,
                               String name1,
                               String name2,
                               FrontierShape shape,
                               Set<FrontierVisibilityFlag> visibility,
                               FrontierBanner banner,
                               Optional<PathStyle> pathStyle,
                               Optional<CollectionId> collectionId,
                               Optional<String> sourcePluginId,
                               UserRef owner,
                               List<SharedUserAccess> sharedUsers) {
    /**
     * Normalizes optional fields and defensive copies in the frontier snapshot.
     *
     * @param id frontier id
     * @param type frontier scope
     * @param lifetime persistence model
     * @param dimension frontier dimension
     * @param color configured color
     * @param name1 first display name
     * @param name2 second display name
     * @param shape frontier geometry
     * @param visibility visibility flags
     * @param banner banner data when configured, or null when the frontier has no banner
     * @param pathStyle path style when configured
     * @param collectionId collection membership when present
     * @param sourcePluginId source plugin id when present
     * @param owner owning user
     * @param sharedUsers shared-user access entries
     */
    public FrontierDataView {
        // Defensive copies ensure API callers cannot mutate internal state by reference.
        lifetime = lifetime == null ? EntityLifetime.PERSISTENT : lifetime;
        visibility = visibility == null ? Set.of() : Set.copyOf(visibility);
        pathStyle = pathStyle == null ? Optional.empty() : pathStyle;
        collectionId = collectionId == null ? Optional.empty() : collectionId;
        sourcePluginId = sourcePluginId == null ? Optional.empty() : sourcePluginId;
        sharedUsers = sharedUsers == null ? List.of() : List.copyOf(sharedUsers);
    }
}
