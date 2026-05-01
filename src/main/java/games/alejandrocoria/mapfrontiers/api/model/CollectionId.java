package games.alejandrocoria.mapfrontiers.api.model;

import java.util.UUID;

/**
 * Stable identifier for a collection.
 *
 * @param value underlying UUID value
 */
public record CollectionId(UUID value) {
    /**
     * Validates the wrapped UUID.
     *
     * @param value underlying UUID value
     */
    public CollectionId {
        if (value == null) {
            throw new IllegalArgumentException("CollectionId cannot be null");
        }
    }
}
