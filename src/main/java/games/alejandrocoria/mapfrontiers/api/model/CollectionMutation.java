package games.alejandrocoria.mapfrontiers.api.model;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Partial collection update payload.
 * Only present fields are applied when this mutation is used.
 * <p>
 * For now, MapFrontiers limits collection names to 48 characters.
 */
@SuppressWarnings("unused")
public final class CollectionMutation {
    private final Optional<String> name;
    private final Optional<Integer> color;
    private final Optional<CollectionVisibilitySettings> visibility;
    private final Set<CollectionVisibilityFlag> visibilityToAdd;
    private final Set<CollectionVisibilityFlag> visibilityToRemove;
    private final Optional<Integer> fullscreenZoom;
    private final Optional<Integer> minimapZoom;
    private final Optional<Integer> webmapZoom;
    private final Optional<FrontierBanner> banner;
    private final boolean clearBanner;

    private CollectionMutation(Optional<String> name,
                               Optional<Integer> color,
                               Optional<CollectionVisibilitySettings> visibility,
                               Set<CollectionVisibilityFlag> visibilityToAdd,
                               Set<CollectionVisibilityFlag> visibilityToRemove,
                               Optional<Integer> fullscreenZoom,
                               Optional<Integer> minimapZoom,
                               Optional<Integer> webmapZoom,
                               Optional<FrontierBanner> banner,
                               boolean clearBanner) {
        this.name = name;
        this.color = color;
        this.visibility = visibility == null ? Optional.empty() : visibility;
        this.visibilityToAdd = Set.copyOf(visibilityToAdd);
        this.visibilityToRemove = Set.copyOf(visibilityToRemove);
        this.fullscreenZoom = fullscreenZoom == null ? Optional.empty() : fullscreenZoom;
        this.minimapZoom = minimapZoom == null ? Optional.empty() : minimapZoom;
        this.webmapZoom = webmapZoom == null ? Optional.empty() : webmapZoom;
        this.banner = banner == null ? Optional.empty() : banner;
        this.clearBanner = clearBanner;

        if (clearBanner && this.banner.isPresent()) {
            throw new IllegalArgumentException("Mutation cannot set and clear banner at the same time");
        }
        if (this.visibility.isPresent()
                && (!this.visibilityToAdd.isEmpty()
                || !this.visibilityToRemove.isEmpty()
                || this.fullscreenZoom.isPresent()
                || this.minimapZoom.isPresent()
                || this.webmapZoom.isPresent())) {
            throw new IllegalArgumentException("Mutation cannot combine full visibility replacement with incremental visibility changes");
        }
        this.fullscreenZoom.ifPresent(value -> validateZoom("fullscreenZoom", value));
        this.minimapZoom.ifPresent(value -> validateZoom("minimapZoom", value));
        this.webmapZoom.ifPresent(value -> validateZoom("webmapZoom", value));
    }

    private static void validateZoom(String fieldName, int zoom) {
        if (!CollectionVisibilitySettings.isValidZoom(zoom)) {
            throw new IllegalArgumentException(fieldName + " must match one of the currently supported zoom levels: "
                    + CollectionVisibilitySettings.validZoomLevels());
        }
    }

    /**
     * Creates a builder for combining multiple changes in one mutation.
     *
     * @return new mutation builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns a mutation with no changes.
     *
     * @return empty mutation
     */
    public static CollectionMutation empty() {
        return builder().build();
    }

    /**
     * Returns a mutation that updates the collection name.
     * For now, the name field is limited to 48 characters.
     *
     * @param name new collection name
     * @return mutation with name change
     * @throws IllegalArgumentException when the name exceeds 48 characters
     */
    public static CollectionMutation name(String name) {
        return builder().name(name).build();
    }

    /**
     * Returns a mutation that updates the collection color.
     *
     * @param color new collection color
     * @return mutation with color change
     */
    public static CollectionMutation color(int color) {
        return builder().color(color).build();
    }

    /**
     * Returns a mutation that replaces the collection visibility settings.
     *
     * @param visibility replacement visibility settings
     * @return mutation with visibility replacement
     */
    public static CollectionMutation visibility(CollectionVisibilitySettings visibility) {
        return builder().visibility(visibility).build();
    }

    /**
     * Returns a mutation that adds collection visibility flags to the current settings.
     *
     * @param visibility visibility flags to add
     * @return mutation with additive visibility update
     */
    public static CollectionMutation addVisibility(Set<CollectionVisibilityFlag> visibility) {
        return builder().addVisibility(visibility).build();
    }

    /**
     * Returns a mutation that removes collection visibility flags from the current settings.
     *
     * @param visibility visibility flags to remove
     * @return mutation with subtractive visibility update
     */
    public static CollectionMutation removeVisibility(Set<CollectionVisibilityFlag> visibility) {
        return builder().removeVisibility(visibility).build();
    }

    /**
     * Returns a mutation that updates the fullscreen-map zoom threshold.
     *
     * @param zoom new fullscreen zoom threshold
     * @return mutation with fullscreen zoom change
     */
    public static CollectionMutation fullscreenZoom(int zoom) {
        return builder().fullscreenZoom(zoom).build();
    }

    /**
     * Returns a mutation that updates the minimap zoom threshold.
     *
     * @param zoom new minimap zoom threshold
     * @return mutation with minimap zoom change
     */
    public static CollectionMutation minimapZoom(int zoom) {
        return builder().minimapZoom(zoom).build();
    }

    /**
     * Returns a mutation that updates the webmap zoom threshold.
     *
     * @param zoom new webmap zoom threshold
     * @return mutation with webmap zoom change
     */
    public static CollectionMutation webmapZoom(int zoom) {
        return builder().webmapZoom(zoom).build();
    }

    /**
     * Returns a mutation that updates the collection banner.
     *
     * @param banner new banner data
     * @return mutation with banner change
     */
    public static CollectionMutation banner(FrontierBanner banner) {
        return builder().banner(banner).build();
    }

    /**
     * Returns a mutation that clears the collection banner.
     *
     * @return mutation that removes banner data
     */
    public static CollectionMutation withClearedBanner() {
        return builder().clearBanner().build();
    }

    /**
     * Returns the optional replacement collection name.
     *
     * @return replacement collection name when present
     */
    public Optional<String> name() {
        return name;
    }

    /**
     * Returns the optional replacement collection color.
     *
     * @return replacement collection color when present
     */
    public Optional<Integer> color() {
        return color;
    }

    /**
     * Returns the replacement collection visibility settings when present.
     *
     * @return replacement visibility settings
     */
    public Optional<CollectionVisibilitySettings> visibility() {
        return visibility;
    }

    /**
     * Returns the collection visibility flags to add on top of the current settings.
     *
     * @return visibility flags to add
     */
    public Set<CollectionVisibilityFlag> visibilityToAdd() {
        return visibilityToAdd;
    }

    /**
     * Returns the collection visibility flags to remove from the current settings.
     *
     * @return visibility flags to remove
     */
    public Set<CollectionVisibilityFlag> visibilityToRemove() {
        return visibilityToRemove;
    }

    /**
     * Returns the replacement fullscreen-map zoom threshold when present.
     *
     * @return fullscreen zoom replacement
     */
    public Optional<Integer> fullscreenZoom() {
        return fullscreenZoom;
    }

    /**
     * Returns the replacement minimap zoom threshold when present.
     *
     * @return minimap zoom replacement
     */
    public Optional<Integer> minimapZoom() {
        return minimapZoom;
    }

    /**
     * Returns the replacement webmap zoom threshold when present.
     *
     * @return webmap zoom replacement
     */
    public Optional<Integer> webmapZoom() {
        return webmapZoom;
    }

    /**
     * Returns the replacement banner when present.
     *
     * @return banner replacement
     */
    public Optional<FrontierBanner> banner() {
        return banner;
    }

    /**
     * Returns whether the mutation requests clearing the banner.
     *
     * @return true when banner data should be removed
     */
    public boolean clearBanner() {
        return clearBanner;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CollectionMutation that)) {
            return false;
        }
        return clearBanner == that.clearBanner
                && name.equals(that.name)
                && color.equals(that.color)
                && visibility.equals(that.visibility)
                && visibilityToAdd.equals(that.visibilityToAdd)
                && visibilityToRemove.equals(that.visibilityToRemove)
                && fullscreenZoom.equals(that.fullscreenZoom)
                && minimapZoom.equals(that.minimapZoom)
                && webmapZoom.equals(that.webmapZoom)
                && banner.equals(that.banner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,
                color,
                visibility,
                visibilityToAdd,
                visibilityToRemove,
                fullscreenZoom,
                minimapZoom,
                webmapZoom,
                banner,
                clearBanner);
    }

    @Override
    public String toString() {
        return "CollectionMutation[name=" + name
                + ", color=" + color
                + ", visibility=" + visibility
                + ", visibilityToAdd=" + visibilityToAdd
                + ", visibilityToRemove=" + visibilityToRemove
                + ", fullscreenZoom=" + fullscreenZoom
                + ", minimapZoom=" + minimapZoom
                + ", webmapZoom=" + webmapZoom
                + ", banner=" + banner
                + ", clearBanner=" + clearBanner
                + "]";
    }

    /**
     * Builder for {@link CollectionMutation}.
     */
    public static final class Builder {
        private Optional<String> name = Optional.empty();
        private Optional<Integer> color = Optional.empty();
        private Optional<CollectionVisibilitySettings> visibility = Optional.empty();
        private final EnumSet<CollectionVisibilityFlag> visibilityToAdd = EnumSet.noneOf(CollectionVisibilityFlag.class);
        private final EnumSet<CollectionVisibilityFlag> visibilityToRemove = EnumSet.noneOf(CollectionVisibilityFlag.class);
        private Optional<Integer> fullscreenZoom = Optional.empty();
        private Optional<Integer> minimapZoom = Optional.empty();
        private Optional<Integer> webmapZoom = Optional.empty();
        private Optional<FrontierBanner> banner = Optional.empty();
        private boolean clearBanner = false;

        private Builder() {
        }

        /**
         * Sets the replacement collection name.
         * For now, the name field is limited to 48 characters.
         *
         * @param value collection name, or null to clear the pending replacement
         * @return this builder
         * @throws IllegalArgumentException when the name exceeds 48 characters
         */
        public Builder name(String value) {
            NameConstraints.validateNameLength("name", value);
            name = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement collection color.
         *
         * @param value collection color, or null to clear the pending replacement
         * @return this builder
         */
        public Builder color(Integer value) {
            color = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement collection visibility settings.
         *
         * @param value visibility settings, or null to clear the pending replacement
         * @return this builder
         */
        public Builder visibility(CollectionVisibilitySettings value) {
            visibility = Optional.ofNullable(value);
            return this;
        }

        /**
         * Adds collection visibility flags to the current settings.
         *
         * @param value visibility flags to add
         * @return this builder
         */
        public Builder addVisibility(Set<CollectionVisibilityFlag> value) {
            if (value != null) {
                visibilityToAdd.addAll(value);
                visibilityToRemove.removeAll(value);
            }
            return this;
        }

        /**
         * Removes collection visibility flags from the current settings.
         *
         * @param value visibility flags to remove
         * @return this builder
         */
        public Builder removeVisibility(Set<CollectionVisibilityFlag> value) {
            if (value != null) {
                visibilityToRemove.addAll(value);
                visibilityToAdd.removeAll(value);
            }
            return this;
        }

        /**
         * Sets the replacement fullscreen-map zoom threshold.
         *
         * @param value fullscreen zoom value, or null to clear the pending replacement
         * @return this builder
         */
        public Builder fullscreenZoom(Integer value) {
            fullscreenZoom = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement minimap zoom threshold.
         *
         * @param value minimap zoom value, or null to clear the pending replacement
         * @return this builder
         */
        public Builder minimapZoom(Integer value) {
            minimapZoom = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement webmap zoom threshold.
         *
         * @param value webmap zoom value, or null to clear the pending replacement
         * @return this builder
         */
        public Builder webmapZoom(Integer value) {
            webmapZoom = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement collection banner.
         *
         * @param value banner value, or null to clear the pending replacement
         * @return this builder
         */
        public Builder banner(FrontierBanner value) {
            banner = Optional.ofNullable(value);
            clearBanner = false;
            return this;
        }

        /**
         * Marks the banner for removal.
         *
         * @return this builder
         */
        public Builder clearBanner() {
            clearBanner = true;
            banner = Optional.empty();
            return this;
        }

        /**
         * Builds an immutable mutation from the current builder state.
         *
         * @return immutable mutation
         */
        public CollectionMutation build() {
            return new CollectionMutation(name,
                    color,
                    visibility,
                    visibilityToAdd,
                    visibilityToRemove,
                    fullscreenZoom,
                    minimapZoom,
                    webmapZoom,
                    banner,
                    clearBanner);
        }
    }
}
