package games.alejandrocoria.mapfrontiers.api.model;

import java.util.List;
import java.util.Objects;

/**
 * Immutable collection visibility configuration exposed by the API.
 * <p>
 * The exact valid zoom levels and builder defaults currently mirror the underlying mod implementation so plugins can
 * align with current behavior. They are not yet documented as a permanently stable public contract.
 */
@SuppressWarnings("unused")
public final class CollectionVisibilitySettings {
    private static final List<Integer> VALID_ZOOM_LEVELS = List.of(
            0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384
    );

    private final boolean visible;
    private final int fullscreenZoom;
    private final int minimapZoom;
    private final int webmapZoom;
    private final boolean fullscreenName;
    private final boolean fullscreenOwner;
    private final boolean fullscreenBanner;
    private final boolean minimapName;
    private final boolean minimapOwner;
    private final boolean minimapBanner;
    private final boolean webmapName;
    private final boolean webmapOwner;
    private final boolean webmapBanner;

    private CollectionVisibilitySettings(boolean visible,
                                         int fullscreenZoom,
                                         int minimapZoom,
                                         int webmapZoom,
                                         boolean fullscreenName,
                                         boolean fullscreenOwner,
                                         boolean fullscreenBanner,
                                         boolean minimapName,
                                         boolean minimapOwner,
                                         boolean minimapBanner,
                                         boolean webmapName,
                                         boolean webmapOwner,
                                         boolean webmapBanner) {
        validateZoom("fullscreenZoom", fullscreenZoom);
        validateZoom("minimapZoom", minimapZoom);
        validateZoom("webmapZoom", webmapZoom);

        this.visible = visible;
        this.fullscreenZoom = fullscreenZoom;
        this.minimapZoom = minimapZoom;
        this.webmapZoom = webmapZoom;
        this.fullscreenName = fullscreenName;
        this.fullscreenOwner = fullscreenOwner;
        this.fullscreenBanner = fullscreenBanner;
        this.minimapName = minimapName;
        this.minimapOwner = minimapOwner;
        this.minimapBanner = minimapBanner;
        this.webmapName = webmapName;
        this.webmapOwner = webmapOwner;
        this.webmapBanner = webmapBanner;
    }

    /**
     * Creates a builder initialized with the defaults currently used by the underlying mod implementation.
     *
     * @return new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the zoom levels currently accepted by the underlying mod implementation.
     *
     * @return immutable list of valid zoom levels
     */
    public static List<Integer> validZoomLevels() {
        return VALID_ZOOM_LEVELS;
    }

    /**
     * Returns whether the given zoom level is currently accepted by the underlying mod implementation.
     *
     * @param zoom zoom level to validate
     * @return true when the zoom is currently valid
     */
    public static boolean isValidZoom(int zoom) {
        return VALID_ZOOM_LEVELS.contains(zoom);
    }

    private static void validateZoom(String fieldName, int zoom) {
        if (!isValidZoom(zoom)) {
            throw new IllegalArgumentException(fieldName + " must match one of the currently supported zoom levels: " + VALID_ZOOM_LEVELS);
        }
    }

    /**
     * Returns whether the collection is generally visible.
     *
     * @return general visibility flag
     */
    public boolean visible() {
        return visible;
    }

    /**
     * Returns the fullscreen-map zoom threshold currently configured for the collection.
     *
     * @return fullscreen zoom threshold
     */
    public int fullscreenZoom() {
        return fullscreenZoom;
    }

    /**
     * Returns the minimap zoom threshold currently configured for the collection.
     *
     * @return minimap zoom threshold
     */
    public int minimapZoom() {
        return minimapZoom;
    }

    /**
     * Returns the webmap zoom threshold currently configured for the collection.
     *
     * @return webmap zoom threshold
     */
    public int webmapZoom() {
        return webmapZoom;
    }

    /**
     * Returns whether the collection name is shown on the fullscreen map.
     *
     * @return true when enabled
     */
    public boolean fullscreenName() {
        return fullscreenName;
    }

    /**
     * Returns whether the collection owner is shown on the fullscreen map.
     *
     * @return true when enabled
     */
    public boolean fullscreenOwner() {
        return fullscreenOwner;
    }

    /**
     * Returns whether the collection banner is shown on the fullscreen map.
     *
     * @return true when enabled
     */
    public boolean fullscreenBanner() {
        return fullscreenBanner;
    }

    /**
     * Returns whether the collection name is shown on the minimap.
     *
     * @return true when enabled
     */
    public boolean minimapName() {
        return minimapName;
    }

    /**
     * Returns whether the collection owner is shown on the minimap.
     *
     * @return true when enabled
     */
    public boolean minimapOwner() {
        return minimapOwner;
    }

    /**
     * Returns whether the collection banner is shown on the minimap.
     *
     * @return true when enabled
     */
    public boolean minimapBanner() {
        return minimapBanner;
    }

    /**
     * Returns whether the collection name is shown on the webmap.
     *
     * @return true when enabled
     */
    public boolean webmapName() {
        return webmapName;
    }

    /**
     * Returns whether the collection owner is shown on the webmap.
     *
     * @return true when enabled
     */
    public boolean webmapOwner() {
        return webmapOwner;
    }

    /**
     * Returns whether the collection banner is shown on the webmap.
     *
     * @return true when enabled
     */
    public boolean webmapBanner() {
        return webmapBanner;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CollectionVisibilitySettings that)) {
            return false;
        }
        return visible == that.visible
                && fullscreenZoom == that.fullscreenZoom
                && minimapZoom == that.minimapZoom
                && webmapZoom == that.webmapZoom
                && fullscreenName == that.fullscreenName
                && fullscreenOwner == that.fullscreenOwner
                && fullscreenBanner == that.fullscreenBanner
                && minimapName == that.minimapName
                && minimapOwner == that.minimapOwner
                && minimapBanner == that.minimapBanner
                && webmapName == that.webmapName
                && webmapOwner == that.webmapOwner
                && webmapBanner == that.webmapBanner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(visible,
                fullscreenZoom,
                minimapZoom,
                webmapZoom,
                fullscreenName,
                fullscreenOwner,
                fullscreenBanner,
                minimapName,
                minimapOwner,
                minimapBanner,
                webmapName,
                webmapOwner,
                webmapBanner);
    }

    @Override
    public String toString() {
        return "CollectionVisibilitySettings[visible=" + visible
                + ", fullscreenZoom=" + fullscreenZoom
                + ", minimapZoom=" + minimapZoom
                + ", webmapZoom=" + webmapZoom
                + ", fullscreenName=" + fullscreenName
                + ", fullscreenOwner=" + fullscreenOwner
                + ", fullscreenBanner=" + fullscreenBanner
                + ", minimapName=" + minimapName
                + ", minimapOwner=" + minimapOwner
                + ", minimapBanner=" + minimapBanner
                + ", webmapName=" + webmapName
                + ", webmapOwner=" + webmapOwner
                + ", webmapBanner=" + webmapBanner
                + "]";
    }

    /**
     * Builder for {@link CollectionVisibilitySettings}.
     */
    public static final class Builder {
        private boolean visible = false;
        private int fullscreenZoom = 256;
        private int minimapZoom = 256;
        private int webmapZoom = 256;
        private boolean fullscreenName = true;
        private boolean fullscreenOwner = false;
        private boolean fullscreenBanner = true;
        private boolean minimapName = true;
        private boolean minimapOwner = false;
        private boolean minimapBanner = true;
        private boolean webmapName = true;
        private boolean webmapOwner = false;
        private boolean webmapBanner = true;

        private Builder() {
        }

        /**
         * Sets whether the collection is generally visible.
         *
         * @param value general visibility flag
         * @return this builder
         */
        public Builder visible(boolean value) {
            visible = value;
            return this;
        }

        /**
         * Sets the fullscreen-map zoom threshold.
         *
         * @param value fullscreen zoom threshold
         * @return this builder
         */
        public Builder fullscreenZoom(int value) {
            fullscreenZoom = value;
            return this;
        }

        /**
         * Sets the minimap zoom threshold.
         *
         * @param value minimap zoom threshold
         * @return this builder
         */
        public Builder minimapZoom(int value) {
            minimapZoom = value;
            return this;
        }

        /**
         * Sets the webmap zoom threshold.
         *
         * @param value webmap zoom threshold
         * @return this builder
         */
        public Builder webmapZoom(int value) {
            webmapZoom = value;
            return this;
        }

        /**
         * Sets whether the collection name is shown on the fullscreen map.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder fullscreenName(boolean value) {
            fullscreenName = value;
            return this;
        }

        /**
         * Sets whether the collection owner is shown on the fullscreen map.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder fullscreenOwner(boolean value) {
            fullscreenOwner = value;
            return this;
        }

        /**
         * Sets whether the collection banner is shown on the fullscreen map.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder fullscreenBanner(boolean value) {
            fullscreenBanner = value;
            return this;
        }

        /**
         * Sets whether the collection name is shown on the minimap.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder minimapName(boolean value) {
            minimapName = value;
            return this;
        }

        /**
         * Sets whether the collection owner is shown on the minimap.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder minimapOwner(boolean value) {
            minimapOwner = value;
            return this;
        }

        /**
         * Sets whether the collection banner is shown on the minimap.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder minimapBanner(boolean value) {
            minimapBanner = value;
            return this;
        }

        /**
         * Sets whether the collection name is shown on the webmap.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder webmapName(boolean value) {
            webmapName = value;
            return this;
        }

        /**
         * Sets whether the collection owner is shown on the webmap.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder webmapOwner(boolean value) {
            webmapOwner = value;
            return this;
        }

        /**
         * Sets whether the collection banner is shown on the webmap.
         *
         * @param value true when enabled
         * @return this builder
         */
        public Builder webmapBanner(boolean value) {
            webmapBanner = value;
            return this;
        }

        /**
         * Builds an immutable visibility settings object.
         *
         * @return immutable visibility settings
         */
        public CollectionVisibilitySettings build() {
            return new CollectionVisibilitySettings(visible,
                    fullscreenZoom,
                    minimapZoom,
                    webmapZoom,
                    fullscreenName,
                    fullscreenOwner,
                    fullscreenBanner,
                    minimapName,
                    minimapOwner,
                    minimapBanner,
                    webmapName,
                    webmapOwner,
                    webmapBanner);
        }
    }
}
