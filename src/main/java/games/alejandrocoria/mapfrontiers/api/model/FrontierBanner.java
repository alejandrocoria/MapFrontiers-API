package games.alejandrocoria.mapfrontiers.api.model;

import java.util.Objects;

/**
 * Banner metadata stored in a frontier.
 *
 * @param baseColorId vanilla dye color id
 * @param patternsNbt serialized banner pattern list as SNBT text. Use {@code []} for banners with no patterns.
 * @param rotation banner rotation value used by MapFrontiers rendering
 */
public record FrontierBanner(int baseColorId, String patternsNbt, int rotation) {
    /**
     * Normalizes and validates banner data.
     *
     * @param baseColorId vanilla dye color id
     * @param patternsNbt serialized banner patterns as SNBT
     * @param rotation banner rotation
     */
    public FrontierBanner {
        Objects.requireNonNull(patternsNbt, "patternsNbt");
        if (patternsNbt.isBlank()) {
            throw new IllegalArgumentException("patternsNbt must be a valid SNBT list; use [] for an empty list");
        }
    }
}
