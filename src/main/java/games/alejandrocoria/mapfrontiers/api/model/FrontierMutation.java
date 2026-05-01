package games.alejandrocoria.mapfrontiers.api.model;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Partial frontier update payload.
 * Only present fields are applied when this mutation is used.
 * <p>
 * For now, MapFrontiers limits each frontier name field to 48 characters.
 */
@SuppressWarnings("unused")
public final class FrontierMutation {
    private final Optional<String> name1;
    private final Optional<String> name2;
    private final Optional<Integer> color;
    private final Optional<FrontierShape> shape;
    private final Optional<Set<FrontierVisibilityFlag>> visibility;
    private final Set<FrontierVisibilityFlag> visibilityToAdd;
    private final Set<FrontierVisibilityFlag> visibilityToRemove;
    private final Optional<FrontierBanner> banner;
    private final Optional<PathStyle> pathStyle;
    private final Optional<CollectionId> collectionId;
    private final boolean clearBanner;
    private final boolean clearCollection;

    private FrontierMutation(Optional<String> name1,
                             Optional<String> name2,
                             Optional<Integer> color,
                             Optional<FrontierShape> shape,
                             Optional<Set<FrontierVisibilityFlag>> visibility,
                             Set<FrontierVisibilityFlag> visibilityToAdd,
                             Set<FrontierVisibilityFlag> visibilityToRemove,
                             Optional<FrontierBanner> banner,
                             Optional<PathStyle> pathStyle,
                             Optional<CollectionId> collectionId,
                             boolean clearBanner,
                             boolean clearCollection) {
        this.name1 = name1;
        this.name2 = name2;
        this.color = color;
        this.shape = shape;
        this.visibility = visibility.map(Set::copyOf);
        this.visibilityToAdd = Set.copyOf(visibilityToAdd);
        this.visibilityToRemove = Set.copyOf(visibilityToRemove);
        this.banner = banner;
        this.pathStyle = pathStyle;
        this.collectionId = collectionId;
        this.clearBanner = clearBanner;
        this.clearCollection = clearCollection;

        if (clearBanner && this.banner.isPresent()) {
            throw new IllegalArgumentException("Mutation cannot set and clear banner at the same time");
        }
        if (clearCollection && this.collectionId.isPresent()) {
            throw new IllegalArgumentException("Mutation cannot set and clear collection at the same time");
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
    public static FrontierMutation empty() {
        return builder().build();
    }

    /**
     * Returns a mutation that updates both name fields.
     * For now, each name field is limited to 48 characters.
     *
     * @param name1 new first name value
     * @param name2 new second name value
     * @return mutation with both name fields updated
     * @throws IllegalArgumentException when either name exceeds 48 characters
     */
    public static FrontierMutation names(String name1, String name2) {
        return builder().names(name1, name2).build();
    }

    /**
     * Returns a mutation that updates the first name field.
     * For now, the name field is limited to 48 characters.
     *
     * @param name1 new first name value
     * @return mutation with the first name updated
     * @throws IllegalArgumentException when the name exceeds 48 characters
     */
    public static FrontierMutation name1(String name1) {
        return builder().name1(name1).build();
    }

    /**
     * Returns a mutation that updates the second name field.
     * For now, the name field is limited to 48 characters.
     *
     * @param name2 new second name value
     * @return mutation with the second name updated
     * @throws IllegalArgumentException when the name exceeds 48 characters
     */
    public static FrontierMutation name2(String name2) {
        return builder().name2(name2).build();
    }

    /**
     * Returns a mutation that updates the frontier color.
     *
     * @param color new frontier color
     * @return mutation with color change
     */
    public static FrontierMutation color(int color) {
        return builder().color(color).build();
    }

    /**
     * Returns a mutation that replaces the frontier shape.
     *
     * @param shape new frontier shape
     * @return mutation with shape change
     */
    public static FrontierMutation shape(FrontierShape shape) {
        return builder().shape(shape).build();
    }

    /**
     * Returns a mutation that replaces the frontier visibility set.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @param visibility replacement visibility flags
     * @return mutation with visibility replacement
     */
    public static FrontierMutation visibility(Set<FrontierVisibilityFlag> visibility) {
        return builder().visibility(visibility).build();
    }

    /**
     * Returns a mutation that adds visibility flags to the current set.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @param visibility visibility flags to add
     * @return mutation with additive visibility update
     */
    public static FrontierMutation addVisibility(Set<FrontierVisibilityFlag> visibility) {
        return builder().addVisibility(visibility).build();
    }

    /**
     * Returns a mutation that removes visibility flags from the current set.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @param visibility visibility flags to remove
     * @return mutation with subtractive visibility update
     */
    public static FrontierMutation removeVisibility(Set<FrontierVisibilityFlag> visibility) {
        return builder().removeVisibility(visibility).build();
    }

    /**
     * Returns a mutation that updates the frontier banner.
     *
     * @param banner new banner data
     * @return mutation with banner change
     */
    public static FrontierMutation banner(FrontierBanner banner) {
        return builder().banner(banner).build();
    }

    /**
     * Returns a mutation that updates the frontier path style.
     *
     * @param pathStyle new path style
     * @return mutation with path-style change
     */
    public static FrontierMutation pathStyle(PathStyle pathStyle) {
        return builder().pathStyle(pathStyle).build();
    }

    /**
     * Returns a mutation that assigns the frontier to a collection.
     * The target collection must have the same lifetime as the frontier according to the underlying mod behavior.
     *
     * @param collectionId target collection id
     * @return mutation with collection assignment
     */
    public static FrontierMutation collection(CollectionId collectionId) {
        return builder().collection(collectionId).build();
    }

    /**
     * Returns a mutation that clears the frontier banner.
     *
     * @return mutation that removes banner data
     */
    public static FrontierMutation withClearedBanner() {
        return builder().clearBanner().build();
    }

    /**
     * Returns a mutation that clears the frontier collection assignment.
     *
     * @return mutation that removes collection membership
     */
    public static FrontierMutation withClearedCollection() {
        return builder().clearCollection().build();
    }

    /**
     * Returns the replacement first name field when present.
     *
     * @return first name replacement
     */
    public Optional<String> name1() {
        return name1;
    }

    /**
     * Returns the replacement second name field when present.
     *
     * @return second name replacement
     */
    public Optional<String> name2() {
        return name2;
    }

    /**
     * Returns the replacement color when present.
     *
     * @return color replacement
     */
    public Optional<Integer> color() {
        return color;
    }

    /**
     * Returns the replacement shape when present.
     *
     * @return shape replacement
     */
    public Optional<FrontierShape> shape() {
        return shape;
    }

    /**
     * Returns the replacement visibility set when present.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @return visibility replacement
     */
    public Optional<Set<FrontierVisibilityFlag>> visibility() {
        return visibility;
    }

    /**
     * Returns the visibility flags to add on top of the current set.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @return visibility flags to add
     */
    public Set<FrontierVisibilityFlag> visibilityToAdd() {
        return visibilityToAdd;
    }

    /**
     * Returns the visibility flags to remove from the current set.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @return visibility flags to remove
     */
    public Set<FrontierVisibilityFlag> visibilityToRemove() {
        return visibilityToRemove;
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
     * Returns the replacement path style when present.
     *
     * @return path-style replacement
     */
    public Optional<PathStyle> pathStyle() {
        return pathStyle;
    }

    /**
     * Returns the replacement collection id when present.
     * The target collection must have the same lifetime as the frontier according to the underlying mod behavior.
     *
     * @return collection replacement
     */
    public Optional<CollectionId> collectionId() {
        return collectionId;
    }

    /**
     * Returns whether the mutation requests clearing the banner.
     *
     * @return true when banner data should be removed
     */
    public boolean clearBanner() {
        return clearBanner;
    }

    /**
     * Returns whether the mutation requests clearing the collection assignment.
     *
     * @return true when collection membership should be removed
     */
    public boolean clearCollection() {
        return clearCollection;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FrontierMutation that)) {
            return false;
        }
        return clearBanner == that.clearBanner
                && name1.equals(that.name1)
                && name2.equals(that.name2)
                && color.equals(that.color)
                && shape.equals(that.shape)
                && visibility.equals(that.visibility)
                && visibilityToAdd.equals(that.visibilityToAdd)
                && visibilityToRemove.equals(that.visibilityToRemove)
                && banner.equals(that.banner)
                && pathStyle.equals(that.pathStyle)
                && collectionId.equals(that.collectionId)
                && clearCollection == that.clearCollection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name1, name2, color, shape, visibility, visibilityToAdd, visibilityToRemove, banner, pathStyle, collectionId, clearBanner, clearCollection);
    }

    @Override
    public String toString() {
        return "FrontierMutation[name1=" + name1
                + ", name2=" + name2
                + ", color=" + color
                + ", shape=" + shape
                + ", visibility=" + visibility
                + ", visibilityToAdd=" + visibilityToAdd
                + ", visibilityToRemove=" + visibilityToRemove
                + ", banner=" + banner
                + ", pathStyle=" + pathStyle
                + ", collectionId=" + collectionId
                + ", clearBanner=" + clearBanner
                + ", clearCollection=" + clearCollection
                + "]";
    }

    /**
     * Builder for {@link FrontierMutation}.
     */
    public static final class Builder {
        private Optional<String> name1 = Optional.empty();
        private Optional<String> name2 = Optional.empty();
        private Optional<Integer> color = Optional.empty();
        private Optional<FrontierShape> shape = Optional.empty();
        private Optional<Set<FrontierVisibilityFlag>> visibility = Optional.empty();
        private final EnumSet<FrontierVisibilityFlag> visibilityToAdd = EnumSet.noneOf(FrontierVisibilityFlag.class);
        private final EnumSet<FrontierVisibilityFlag> visibilityToRemove = EnumSet.noneOf(FrontierVisibilityFlag.class);
        private Optional<FrontierBanner> banner = Optional.empty();
        private Optional<PathStyle> pathStyle = Optional.empty();
        private Optional<CollectionId> collectionId = Optional.empty();
        private boolean clearBanner = false;
        private boolean clearCollection = false;

        private Builder() {
        }

        /**
         * Sets the first name field.
         * For now, the name field is limited to 48 characters.
         *
         * @param value first name value, or null to clear the pending replacement
         * @return this builder
         * @throws IllegalArgumentException when the name exceeds 48 characters
         */
        public Builder name1(String value) {
            NameConstraints.validateNameLength("name1", value);
            name1 = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the second name field.
         * For now, the name field is limited to 48 characters.
         *
         * @param value second name value, or null to clear the pending replacement
         * @return this builder
         * @throws IllegalArgumentException when the name exceeds 48 characters
         */
        public Builder name2(String value) {
            NameConstraints.validateNameLength("name2", value);
            name2 = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets both name fields.
         * For now, each name field is limited to 48 characters.
         *
         * @param value1 first name value, or null to clear the pending replacement
         * @param value2 second name value, or null to clear the pending replacement
         * @return this builder
         * @throws IllegalArgumentException when either name exceeds 48 characters
         */
        public Builder names(String value1, String value2) {
            NameConstraints.validateNameLength("name1", value1);
            NameConstraints.validateNameLength("name2", value2);
            name1 = Optional.ofNullable(value1);
            name2 = Optional.ofNullable(value2);
            return this;
        }

        /**
         * Sets the replacement color.
         *
         * @param value color value, or null to clear the pending replacement
         * @return this builder
         */
        public Builder color(Integer value) {
            color = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement shape.
         *
         * @param value shape value, or null to clear the pending replacement
         * @return this builder
         */
        public Builder shape(FrontierShape value) {
            shape = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement visibility set.
         * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
         *
         * @param value visibility set, or null to clear the pending replacement
         * @return this builder
         */
        public Builder visibility(Set<FrontierVisibilityFlag> value) {
            visibility = Optional.ofNullable(value);
            return this;
        }

        /**
         * Adds visibility flags to the current frontier visibility set.
         * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
         *
         * @param value visibility flags to add
         * @return this builder
         */
        public Builder addVisibility(Set<FrontierVisibilityFlag> value) {
            if (value != null) {
                visibilityToAdd.addAll(value);
                visibilityToRemove.removeAll(value);
            }
            return this;
        }

        /**
         * Removes visibility flags from the current frontier visibility set.
         * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
         *
         * @param value visibility flags to remove
         * @return this builder
         */
        public Builder removeVisibility(Set<FrontierVisibilityFlag> value) {
            if (value != null) {
                visibilityToRemove.addAll(value);
                visibilityToAdd.removeAll(value);
            }
            return this;
        }

        /**
         * Sets the replacement banner.
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
         * Sets the replacement path style.
         *
         * @param value path style, or null to clear the pending replacement
         * @return this builder
         */
        public Builder pathStyle(PathStyle value) {
            pathStyle = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the replacement collection assignment.
         * The target collection must have the same lifetime as the frontier according to the underlying mod behavior.
         *
         * @param value collection id, or null to clear the pending replacement
         * @return this builder
         */
        public Builder collection(CollectionId value) {
            collectionId = Optional.ofNullable(value);
            clearCollection = false;
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
         * Marks the collection assignment for removal.
         *
         * @return this builder
         */
        public Builder clearCollection() {
            clearCollection = true;
            collectionId = Optional.empty();
            return this;
        }

        /**
         * Builds an immutable mutation from the current builder state.
         *
         * @return immutable mutation
         */
        public FrontierMutation build() {
            return new FrontierMutation(name1,
                    name2,
                    color,
                    shape,
                    visibility,
                    visibilityToAdd,
                    visibilityToRemove,
                    banner,
                    pathStyle,
                    collectionId,
                    clearBanner,
                    clearCollection);
        }
    }
}
