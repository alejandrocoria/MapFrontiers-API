package games.alejandrocoria.mapfrontiers.api.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable collection creation payload.
 * <p>
 * For now, MapFrontiers limits collection names to 48 characters.
 */
@SuppressWarnings("unused")
public final class CollectionCreateRequest {
    private final DefaultValuesProfile defaultValuesProfile;
    private final Optional<String> name;
    private final Optional<Integer> color;
    private final Optional<CollectionVisibilitySettings> visibility;
    private final Optional<FrontierBanner> banner;

    private CollectionCreateRequest(DefaultValuesProfile defaultValuesProfile,
                                    Optional<String> name,
                                    Optional<Integer> color,
                                    Optional<CollectionVisibilitySettings> visibility,
                                    Optional<FrontierBanner> banner) {
        this.defaultValuesProfile = Objects.requireNonNull(defaultValuesProfile, "defaultValuesProfile cannot be null");
        this.name = name == null ? Optional.empty() : name;
        this.color = color == null ? Optional.empty() : color;
        this.visibility = visibility == null ? Optional.empty() : visibility;
        this.banner = banner == null ? Optional.empty() : banner;
    }

    /**
     * Creates a builder for combining multiple initial values in one request.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     *
     * @return new request builder
     */
    public static Builder builder() {
        return builder(DefaultValuesProfile.BUILTIN);
    }

    /**
     * Creates a builder for combining multiple initial values in one request with an explicit default value profile.
     * Explicit request fields override the selected base profile.
     *
     * @param profile default value profile to use as the base for omitted fields
     * @return new request builder
     * @throws NullPointerException when profile is null
     */
    public static Builder builder(DefaultValuesProfile profile) {
        return new Builder(Objects.requireNonNull(profile, "profile cannot be null"));
    }

    /**
     * Returns a request with no optional fields set.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     *
     * @return empty create request
     */
    public static CollectionCreateRequest empty() {
        return builder().build();
    }

    /**
     * Returns a request with no optional fields set and an explicit default value profile.
     *
     * @param profile default value profile to use as the base for omitted fields
     * @return empty create request
     * @throws NullPointerException when profile is null
     */
    public static CollectionCreateRequest empty(DefaultValuesProfile profile) {
        return builder(profile).build();
    }

    /**
     * Returns a request with only the collection name set.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     * Use {@link #builder(DefaultValuesProfile)} or {@link #empty(DefaultValuesProfile)} to choose a different
     * default value profile.
     *
     * @param name initial collection name
     * @return create request with name
     */
    public static CollectionCreateRequest name(String name) {
        return builder().name(name).build();
    }

    /**
     * Returns a request with only the collection color set.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     * Use {@link #builder(DefaultValuesProfile)} or {@link #empty(DefaultValuesProfile)} to choose a different
     * default value profile.
     *
     * @param color initial collection color
     * @return create request with color
     */
    public static CollectionCreateRequest color(int color) {
        return builder().color(color).build();
    }

    /**
     * Returns a request with only the collection visibility set.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     * Use {@link #builder(DefaultValuesProfile)} or {@link #empty(DefaultValuesProfile)} to choose a different
     * default value profile.
     *
     * @param visibility initial collection visibility
     * @return create request with visibility
     */
    public static CollectionCreateRequest visibility(CollectionVisibilitySettings visibility) {
        return builder().visibility(visibility).build();
    }

    /**
     * Returns a request with only the collection banner set.
     * Uses {@link DefaultValuesProfile#BUILTIN} as the base for omitted fields.
     * Use {@link #builder(DefaultValuesProfile)} or {@link #empty(DefaultValuesProfile)} to choose a different
     * default value profile.
     *
     * @param banner initial collection banner
     * @return create request with banner
     */
    public static CollectionCreateRequest banner(FrontierBanner banner) {
        return builder().banner(banner).build();
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
     * Returns the optional initial collection name.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return initial collection name when present
     */
    public Optional<String> name() {
        return name;
    }

    /**
     * Returns the optional initial collection color.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return initial collection color when present
     */
    public Optional<Integer> color() {
        return color;
    }

    /**
     * Returns the optional initial collection visibility settings.
     * When absent, or when {@code null} was provided to the builder, the selected {@link #defaultValuesProfile()} is
     * used as the base. This request does not expose a separate "clear visibility" operation.
     *
     * @return initial visibility settings when present
     */
    public Optional<CollectionVisibilitySettings> visibility() {
        return visibility;
    }

    /**
     * Returns the optional initial collection banner.
     * When absent, the selected {@link #defaultValuesProfile()} is used as the base.
     *
     * @return initial banner when present
     */
    public Optional<FrontierBanner> banner() {
        return banner;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CollectionCreateRequest that)) {
            return false;
        }
        return name.equals(that.name)
                && defaultValuesProfile.equals(that.defaultValuesProfile)
                && color.equals(that.color)
                && visibility.equals(that.visibility)
                && banner.equals(that.banner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultValuesProfile, name, color, visibility, banner);
    }

    @Override
    public String toString() {
        return "CollectionCreateRequest[defaultValuesProfile=" + defaultValuesProfile
                + ", name=" + name
                + ", color=" + color
                + ", visibility=" + visibility
                + ", banner=" + banner
                + "]";
    }

    /**
     * Builder for {@link CollectionCreateRequest}.
     */
    public static final class Builder {
        private final DefaultValuesProfile defaultValuesProfile;
        private Optional<String> name = Optional.empty();
        private Optional<Integer> color = Optional.empty();
        private Optional<CollectionVisibilitySettings> visibility = Optional.empty();
        private Optional<FrontierBanner> banner = Optional.empty();

        private Builder(DefaultValuesProfile defaultValuesProfile) {
            this.defaultValuesProfile = Objects.requireNonNull(defaultValuesProfile, "defaultValuesProfile cannot be null");
        }

        /**
         * Sets the initial collection name.
         * For now, the name field is limited to 48 characters.
         * When null, the selected default value profile remains in effect.
         *
         * @param value collection name, or null to leave it omitted and use the selected default value profile
         * @return this builder
         * @throws IllegalArgumentException when the name exceeds 48 characters
         */
        public Builder name(String value) {
            NameConstraints.validateNameLength("name", value);
            name = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the initial collection color.
         * When null, the selected default value profile remains in effect.
         *
         * @param value collection color, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder color(Integer value) {
            color = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the initial collection visibility settings.
         * When null, the field remains omitted so the selected default value profile remains in effect.
         * This request does not expose a separate "clear visibility" operation.
         *
         * @param value visibility settings, or null to leave it omitted and use the selected default value profile
         * @return this builder
         */
        public Builder visibility(CollectionVisibilitySettings value) {
            visibility = Optional.ofNullable(value);
            return this;
        }

        /**
         * Sets the initial collection banner.
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
         * Builds an immutable request from the current builder state.
         *
         * @return immutable create request
         */
        public CollectionCreateRequest build() {
            return new CollectionCreateRequest(defaultValuesProfile, name, color, visibility, banner);
        }
    }
}
