package games.alejandrocoria.mapfrontiers.api.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable snapshot view of collection data exposed by the API.
 * <p>
 * Lifetime is immutable after creation.
 *
 * @param id collection id
 * @param type collection scope represented with the shared enum also used by frontier data views
 * @param lifetime persistence model of the created collection
 * @param owner owning user
 * @param name display name
 * @param color configured color
 * @param visibility collection visibility configuration exposed by the API
 * @param banner banner data when configured, or null when the collection has no banner
 * @param sourcePluginId source plugin id when the collection originated from a plugin-scoped operation
 */
public record CollectionDataView(CollectionId id,
                                 FrontierType type,
                                 EntityLifetime lifetime,
                                 UserRef owner,
                                 String name,
                                 int color,
                                 CollectionVisibilitySettings visibility,
                                 FrontierBanner banner,
                                 Optional<String> sourcePluginId) {
    /**
     * Normalizes optional fields in the collection snapshot.
     *
     * @param id collection id
     * @param type collection scope
     * @param lifetime persistence model
     * @param owner owning user
     * @param name display name
     * @param color configured color
     * @param visibility collection visibility configuration
     * @param banner banner data when configured, or null when the collection has no banner
     * @param sourcePluginId source plugin id when present
     */
    public CollectionDataView {
        lifetime = lifetime == null ? EntityLifetime.PERSISTENT : lifetime;
        visibility = Objects.requireNonNull(visibility, "visibility");
        sourcePluginId = sourcePluginId == null ? Optional.empty() : sourcePluginId;
    }
}
