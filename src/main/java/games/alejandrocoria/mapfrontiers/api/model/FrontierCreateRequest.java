package games.alejandrocoria.mapfrontiers.api.model;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Immutable frontier creation payload.
 * <p>
 * For now, MapFrontiers limits each frontier name field to 48 characters.
 * Temporary creation is expressed by a dedicated client method, not by a field in this request.
 */
@SuppressWarnings("unused")
public final class FrontierCreateRequest {
    private final DimensionId dimension;
    private final FrontierShape shape;
    private final DefaultValuesProfile defaultValuesProfile;
    private final Optional<CollectionId> collectionId;
    private final Optional<String> name1;
    private final Optional<String> name2;
    private final Optional<Integer> color;
    private final Optional<Set<FrontierVisibilityFlag>> visibility;
    private final Optional<FrontierBanner> banner;
    private final Optional<PathStyle> pathStyle;

    private FrontierCreateRequest(DimensionId dimension,
                                  FrontierShape shape,
                                  DefaultValuesProfile defaultValuesProfile,
                                  Optional<CollectionId> collectionId,
                                  Optional<String> name1,
                                  Optional<String> name2,
                                  Optional<Integer> color,
                                  Optional<Set<FrontierVisibilityFlag>> visibility,
                                  Optional<FrontierBanner> banner,
                                  Optional<PathStyle> pathStyle) {
        this.dimension = Objects.requireNonNull(dimension, "dimension cannot be null");
        this.shape = Objects.requireNonNull(shape, "shape cannot be null");
        this.defaultValuesProfile = Objects.requireNonNull(defaultValuesProfile, "defaultValuesProfile cannot be null");
        this.collectionId = collectionId == null ? Optional.empty() : collectionId;
        this.name1 = name1 == null ? Optional.empty() : name1;
        this.name2 = name2 == null ? Optional.empty() : name2;
        this.color = color == null ? Optional.empty() : color;
        this.visibility = visibility == null ? Optional.empty() : visibility.map(Set::copyOf);
        this.banner = banner == null ? Optional.empty() : banner;
        this.pathStyle = pathStyle == null ? Optional.empty() : pathStyle;
    }

    /**
     * Creates a builder with the required create fields.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     *
     * @param dimension target dimension
     * @param shape initial frontier shape
     * @return new create request builder
     */
    public static Builder builder(DimensionId dimension, FrontierShape shape) {
        return builder(dimension, shape, DefaultValuesProfile.BUILTIN);
    }

    /**
     * Creates a builder with the required create fields and an explicit default value profile.
     * Explicit request fields override the selected base profile.
     *
     * @param dimension target dimension
     * @param shape initial frontier shape
     * @param profile default value profile to use as the base for omitted fields
     * @return new create request builder
     * @throws NullPointerException when profile is null
     */
    public static Builder builder(DimensionId dimension, FrontierShape shape, DefaultValuesProfile profile) {
        return new Builder(dimension, shape, Objects.requireNonNull(profile, "profile cannot be null"));
    }

    /**
     * Returns a request with only the required fields set.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     *
     * @param dimension target dimension
     * @param shape initial frontier shape
     * @return create request with required fields only
     */
    public static FrontierCreateRequest of(DimensionId dimension, FrontierShape shape) {
        return builder(dimension, shape).build();
    }

    /**
     * Returns a request with only the required fields set and an explicit default value profile.
     * Explicit request fields override the selected base profile.
     *
     * @param dimension target dimension
     * @param shape initial frontier shape
     * @param profile default value profile to use as the base for omitted fields
     * @return create request with required fields only
     * @throws NullPointerException when profile is null
     */
    public static FrontierCreateRequest of(DimensionId dimension, FrontierShape shape, DefaultValuesProfile profile) {
        return builder(dimension, shape, profile).build();
    }

    /**
     * Returns the target dimension for the new frontier.
     *
     * @return target dimension
     */
    public DimensionId dimension() {
        return dimension;
    }

    /**
     * Returns the initial shape for the new frontier.
     *
     * @return initial frontier shape
     */
    public FrontierShape shape() {
        return shape;
    }

    /**
     * Returns the default value profile used as the base for omitted fields in this request.
     * {@link DefaultValuesProfile#BUILTIN BUILTIN} uses MapFrontiers built-in defaults.
     * {@link DefaultValuesProfile#CONFIGURED CONFIGURED} uses configurable defaults available in the current execution
     * context.
     *
     * @return default value profile for omitted fields
     */
    public DefaultValuesProfile defaultValuesProfile() {
        return defaultValuesProfile;
    }

    /**
     * Returns the optional initial collection assignment.
     * When present, the target collection must have the same lifetime as the created frontier according to the
     * underlying mod behavior. When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return collection id when present
     */
    public Optional<CollectionId> collectionId() {
        return collectionId;
    }

    /**
     * Returns the optional initial first name field.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return first name value when present
     */
    public Optional<String> name1() {
        return name1;
    }

    /**
     * Returns the optional initial second name field.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return second name value when present
     */
    public Optional<String> name2() {
        return name2;
    }

    /**
     * Returns the optional initial color.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return frontier color when present
     */
    public Optional<Integer> color() {
        return color;
    }

    /**
     * Returns the optional initial visibility set.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
     *
     * @return visibility set when present
     */
    public Optional<Set<FrontierVisibilityFlag>> visibility() {
        return visibility;
    }

    /**
     * Returns the optional initial banner.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return banner when present
     */
    public Optional<FrontierBanner> banner() {
        return banner;
    }

    /**
     * Returns the optional initial path style.
     * When absent, or when {@code null} was provided to the builder, the selected {@link #defaultValuesProfile()} is
     * used as the base. This request does not expose a separate "clear path style" operation.
     *
     * @return path style when present
     */
    public Optional<PathStyle> pathStyle() {
        return pathStyle;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FrontierCreateRequest that)) {
            return false;
        }
        return dimension.equals(that.dimension)
                && shape.equals(that.shape)
                && defaultValuesProfile.equals(that.defaultValuesProfile)
                && collectionId.equals(that.collectionId)
                && name1.equals(that.name1)
                && name2.equals(that.name2)
                && color.equals(that.color)
                && visibility.equals(that.visibility)
                && banner.equals(that.banner)
                && pathStyle.equals(that.pathStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, shape, defaultValuesProfile, collectionId, name1, name2, color, visibility,
                banner, pathStyle);
    }

    @Override
    public String toString() {
        return "FrontierCreateRequest[dimension=" + dimension
                + ", shape=" + shape
                + ", defaultValuesProfile=" + defaultValuesProfile
                + ", collectionId=" + collectionId
                + ", name1=" + name1
                + ", name2=" + name2
                + ", color=" + color
                + ", visibility=" + visibility
                + ", banner=" + banner
                + ", pathStyle=" + pathStyle
                + "]";
    }

    /**
     * Builder for {@link FrontierCreateRequest}.
     */
    public static final class Builder {
        private final DimensionId dimension;
        private final FrontierShape shape;
        private final DefaultValuesProfile defaultValuesProfile;
        private Optional<CollectionId> collectionId = Optional.empty();
        private Optional<String> name1 = Optional.empty();
        private Optional<String> name2 = Optional.empty();
        private Optional<Integer> color = Optional.empty();
        private Optional<Set<FrontierVisibilityFlag>> visibility = Optional.empty();
        private Optional<FrontierBanner> banner = Optional.empty();
        private Optional<PathStyle> pathStyle = Optional.empty();

        private Builder(DimensionId dimension, FrontierShape shape, DefaultValuesProfile defaultValuesProfile) {
            this.dimension = Objects.requireNonNull(dimension, "dimension cannot be null");
            this.shape = Objects.requireNonNull(shape, "shape cannot be null");
            this.defaultValuesProfile = Objects.requireNonNull(defaultValuesProfile, "defaultValuesProfile cannot be null");
        }

        /**
         * Sets the initial collection assignment.
         * When present, the target collection must have the same lifetime as the created frontier according to the
         * underlying mod behavior. When absent, the selected default value profile remains in effect.
         *
         * @param value collection id, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder collection(CollectionId value) {
            collectionId = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the first name field.
         * For now, the name field is limited to 48 characters.
         * When null, the selected default value profile remains in effect.
         *
         * @param value first name value, or null to leave it omitted and use the selected default value profile
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
         * When null, the selected default value profile remains in effect.
         *
         * @param value second name value, or null to leave it omitted and use the selected default value profile
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
         * Null values leave the corresponding field omitted so the selected default value profile remains in effect.
         *
         * @param value1 first name value, or null to leave it omitted and use the selected default value profile
         * @param value2 second name value, or null to leave it omitted and use the selected default value profile
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
         * Sets the initial frontier color.
         * When null, the selected default value profile remains in effect.
         *
         * @param value color value, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder color(Integer value) {
            color = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the initial frontier visibility set.
         * When null, the field remains omitted so the selected default value profile remains in effect.
         * This request does not expose a separate "clear visibility" operation.
         * Supported flags are the values defined by {@link FrontierVisibilityFlag}.
         *
         * @param value visibility set, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder visibility(Set<FrontierVisibilityFlag> value) {
            visibility = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the initial frontier banner.
         * When null, the selected default value profile remains in effect.
         *
         * @param value banner value, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder banner(FrontierBanner value) {
            banner = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the initial path style.
         * When null, the field remains omitted so the selected default value profile remains in effect.
         * This request does not expose a separate "clear path style" operation.
         *
         * @param value path style, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder pathStyle(PathStyle value) {
            pathStyle = Optional.ofNullable(value);
            return this;
        }

        /**
         * Builds an immutable request from the current builder state.
         *
         * @return immutable create request
         */
        public FrontierCreateRequest build() {
            return new FrontierCreateRequest(dimension,
                    shape,
                    defaultValuesProfile,
                    collectionId,
                    name1,
                    name2,
                    color,
                    visibility,
                    banner,
                    pathStyle);
        }
    }
}
