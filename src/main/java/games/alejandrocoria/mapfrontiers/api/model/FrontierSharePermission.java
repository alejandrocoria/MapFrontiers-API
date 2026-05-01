package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Permissions that can be granted to a user shared on a personal frontier.
 */
public enum FrontierSharePermission {
    /**
     * Allows editing the frontier geometry and mutable data.
     */
    UpdateFrontier,
    /**
     * Allows editing frontier sharing and settings.
     */
    UpdateSettings
}
