package games.alejandrocoria.mapfrontiers.api.model;

import java.util.UUID;

/**
 * Stable user reference used by the API.
 *
 * @param id unique user identifier
 * @param name display name known at the time of the snapshot
 */
public record UserRef(UUID id, String name) {
    /**
     * Validates the wrapped user reference.
     *
     * @param id unique user identifier
     * @param name display name known at the time of the snapshot
     */
    public UserRef {
        if (id == null) {
            throw new IllegalArgumentException("UserRef id cannot be null");
        }
    }
}
