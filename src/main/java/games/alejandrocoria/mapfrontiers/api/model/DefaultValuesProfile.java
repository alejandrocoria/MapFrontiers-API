package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Selects which default value profile is used as the base when creating a new frontier or collection.
 */
@SuppressWarnings("unused")
public enum DefaultValuesProfile {
    /**
     * Uses the built-in defaults defined by MapFrontiers itself.
     */
    BUILTIN,

    /**
     * Uses configurable defaults available in the current execution context.
     * On the client API this means the local player's configured defaults.
     * On the server API this is currently unsupported.
     */
    CONFIGURED
}
