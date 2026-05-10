package games.alejandrocoria.mapfrontiers.api.model;

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
 * @param sourcePluginId source plugin id when the collection originated from a plugin-scoped operation
 */
public record CollectionDataView(CollectionId id,
                                 FrontierType type,
                                 EntityLifetime lifetime,
                                 UserRef owner,
                                 String name,
                                 int color,
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
     * @param sourcePluginId source plugin id when present
     */
    public CollectionDataView {
        lifetime = lifetime == null ? EntityLifetime.PERSISTENT : lifetime;
        sourcePluginId = sourcePluginId == null ? Optional.empty() : sourcePluginId;
    }
}
