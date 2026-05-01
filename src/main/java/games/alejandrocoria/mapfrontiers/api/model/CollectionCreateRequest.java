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
    private final Optional<String> name;
    private final Optional<Integer> color;

    private CollectionCreateRequest(Optional<String> name, Optional<Integer> color) {
        this.name = name == null ? Optional.empty() : name;
        this.color = color == null ? Optional.empty() : color;
    }

    /**
     * Creates a builder for combining multiple initial values in one request.
     *
     * @return new request builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns a request with no optional fields set.
     *
     * @return empty create request
     */
    public static CollectionCreateRequest empty() {
        return builder().build();
    }

    /**
     * Returns a request with only the collection name set.
     *
     * @param name initial collection name
     * @return create request with name
     */
    public static CollectionCreateRequest name(String name) {
        return builder().name(name).build();
    }

    /**
     * Returns a request with only the collection color set.
     *
     * @param color initial collection color
     * @return create request with color
     */
    public static CollectionCreateRequest color(int color) {
        return builder().color(color).build();
    }

    /**
     * Returns the optional initial collection name.
     *
     * @return initial collection name when present
     */
    public Optional<String> name() {
        return name;
    }

    /**
     * Returns the optional initial collection color.
     *
     * @return initial collection color when present
     */
    public Optional<Integer> color() {
        return color;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CollectionCreateRequest that)) {
            return false;
        }
        return name.equals(that.name) && color.equals(that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }

    @Override
    public String toString() {
        return "CollectionCreateRequest[name=" + name
                + ", color=" + color
                + "]";
    }

    /**
     * Builder for {@link CollectionCreateRequest}.
     */
    public static final class Builder {
        private Optional<String> name = Optional.empty();
        private Optional<Integer> color = Optional.empty();

        private Builder() {
        }

        /**
         * Sets the initial collection name.
         * For now, the name field is limited to 48 characters.
         *
         * @param value collection name, or null to clear it
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
         *
         * @param value collection color, or null to clear it
         * @return this builder
         */
        public Builder color(Integer value) {
            color = Optional.ofNullable(value);
            return this;
        }

        /**
         * Builds an immutable request from the current builder state.
         *
         * @return immutable create request
         */
        public CollectionCreateRequest build() {
            return new CollectionCreateRequest(name, color);
        }
    }
}
