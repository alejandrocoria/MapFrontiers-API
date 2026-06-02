package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Boolean visibility toggles available for collection rendering.
 * <p>
 * Zoom thresholds remain separate numeric settings in {@link CollectionVisibilitySettings}.
 */
public enum CollectionVisibilityFlag {
    /** Enables collection visibility in general. */
    Visible,
    /** Shows the collection name on the fullscreen map. */
    FullscreenName,
    /** Shows the collection owner on the fullscreen map. */
    FullscreenOwner,
    /** Shows the collection banner on the fullscreen map. */
    FullscreenBanner,
    /** Shows the collection name on the minimap. */
    MinimapName,
    /** Shows the collection owner on the minimap. */
    MinimapOwner,
    /** Shows the collection banner on the minimap. */
    MinimapBanner,
    /** Shows the collection name on the webmap. */
    WebmapName,
    /** Shows the collection owner on the webmap. */
    WebmapOwner,
    /** Shows the collection banner on the webmap. */
    WebmapBanner
}
