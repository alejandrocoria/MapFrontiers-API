package games.alejandrocoria.mapfrontiers.api.model;

import java.util.Objects;
import java.util.Optional;

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

    private CollectionMutation(Optional<String> name, Optional<Integer> color) {
        this.name = name;
        this.color = color;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CollectionMutation that)) {
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
        return "CollectionMutation[name=" + name
                + ", color=" + color
                + "]";
    }

    /**
     * Builder for {@link CollectionMutation}.
     */
    public static final class Builder {
        private Optional<String> name = Optional.empty();
        private Optional<Integer> color = Optional.empty();

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
         * Builds an immutable mutation from the current builder state.
         *
         * @return immutable mutation
         */
        public CollectionMutation build() {
            return new CollectionMutation(name, color);
        }
    }
}
