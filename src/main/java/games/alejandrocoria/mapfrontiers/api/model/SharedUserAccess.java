package games.alejandrocoria.mapfrontiers.api.model;

import java.util.EnumSet;
import java.util.Set;

/**
 * Shared-user entry attached to a personal frontier snapshot.
 *
 * @param user shared user reference
 * @param permissions permissions currently granted to that user
 * @param pending whether the share is still pending acceptance
 */
public record SharedUserAccess(UserRef user, Set<FrontierSharePermission> permissions, boolean pending) {
    /**
     * Normalizes the permission set for a shared-user entry.
     *
     * @param user shared user reference
     * @param permissions permissions currently granted to that user
     * @param pending whether the share is still pending acceptance
     */
    public SharedUserAccess {
        if (user == null) {
            throw new IllegalArgumentException("Shared user cannot be null");
        }

        EnumSet<FrontierSharePermission> normalized = EnumSet.noneOf(FrontierSharePermission.class);
        if (permissions != null) {
            normalized.addAll(permissions);
        }
        permissions = Set.copyOf(normalized);
    }
}
