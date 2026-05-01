package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Visibility toggles used by MapFrontiers displays.
 * <p>
 * The current values are:
 * {@code Frontier}, {@code AnnounceInChat}, {@code AnnounceInTitle}, {@code MentionCollection},
 * {@code Fullscreen}, {@code FullscreenName}, {@code FullscreenCollection}, {@code FullscreenOwner},
 * {@code FullscreenBanner}, {@code FullscreenDay}, {@code FullscreenNight}, {@code FullscreenUnderground},
 * {@code FullscreenTopo}, {@code FullscreenBiome}, {@code Minimap}, {@code MinimapName},
 * {@code MinimapCollection}, {@code MinimapOwner}, {@code MinimapBanner}, {@code MinimapDay},
 * {@code MinimapNight}, {@code MinimapUnderground}, {@code MinimapTopo}, {@code MinimapBiome},
 * {@code Webmap}, {@code WebmapName}, {@code WebmapCollection}, {@code WebmapOwner},
 * {@code WebmapBanner}, {@code WebmapDay}, {@code WebmapNight}, {@code WebmapUnderground},
 * {@code WebmapTopo}, {@code WebmapBiome}.
 */
public enum FrontierVisibilityFlag {
    /** Shows the frontier itself. */
    Frontier,
    /** Announces frontier entry or exit in chat. */
    AnnounceInChat,
    /** Announces frontier entry or exit in the title overlay. */
    AnnounceInTitle,
    /** Mentions the collection associated with the frontier. */
    MentionCollection,
    /** Enables fullscreen-map rendering. */
    Fullscreen,
    /** Shows the name on the fullscreen map. */
    FullscreenName,
    /** Shows the collection on the fullscreen map. */
    FullscreenCollection,
    /** Shows the owner on the fullscreen map. */
    FullscreenOwner,
    /** Shows the banner on the fullscreen map. */
    FullscreenBanner,
    /** Enables daytime styling on the fullscreen map. */
    FullscreenDay,
    /** Enables nighttime styling on the fullscreen map. */
    FullscreenNight,
    /** Enables underground styling on the fullscreen map. */
    FullscreenUnderground,
    /** Enables topographic styling on the fullscreen map. */
    FullscreenTopo,
    /** Enables biome styling on the fullscreen map. */
    FullscreenBiome,
    /** Enables minimap rendering. */
    Minimap,
    /** Shows the name on the minimap. */
    MinimapName,
    /** Shows the collection on the minimap. */
    MinimapCollection,
    /** Shows the owner on the minimap. */
    MinimapOwner,
    /** Shows the banner on the minimap. */
    MinimapBanner,
    /** Enables daytime styling on the minimap. */
    MinimapDay,
    /** Enables nighttime styling on the minimap. */
    MinimapNight,
    /** Enables underground styling on the minimap. */
    MinimapUnderground,
    /** Enables topographic styling on the minimap. */
    MinimapTopo,
    /** Enables biome styling on the minimap. */
    MinimapBiome,
    /** Enables webmap rendering. */
    Webmap,
    /** Shows the name on the webmap. */
    WebmapName,
    /** Shows the collection on the webmap. */
    WebmapCollection,
    /** Shows the owner on the webmap. */
    WebmapOwner,
    /** Shows the banner on the webmap. */
    WebmapBanner,
    /** Enables daytime styling on the webmap. */
    WebmapDay,
    /** Enables nighttime styling on the webmap. */
    WebmapNight,
    /** Enables underground styling on the webmap. */
    WebmapUnderground,
    /** Enables topographic styling on the webmap. */
    WebmapTopo,
    /** Enables biome styling on the webmap. */
    WebmapBiome
}
