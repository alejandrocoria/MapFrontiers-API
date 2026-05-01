package games.alejandrocoria.mapfrontiers.api;

import games.alejandrocoria.mapfrontiers.api.internal.ApiLogger;
import games.alejandrocoria.mapfrontiers.api.internal.InternalMapFrontiersClientAPI;
import games.alejandrocoria.mapfrontiers.api.internal.InternalMapFrontiersServerAPI;

import java.util.Set;

/**
 * Internal lifecycle bridge used by MapFrontiers runtime to bind/unbind API instances.
 */
public final class MapFrontiersAPIBootstrap {
    private static final Set<String> TRUSTED_CALLERS = Set.of(
            "games.alejandrocoria.mapfrontiers.MapFrontiers",
            "games.alejandrocoria.mapfrontiers.client.MapFrontiersClient"
    );

    private MapFrontiersAPIBootstrap() {
    }

    /**
     * Binds the active client API implementation.
     *
     * @param api active internal client API
     */
    public static void setClientAPI(InternalMapFrontiersClientAPI api) {
        assertTrustedCaller();
        MapFrontiersAPI.setClientAPI(api);
    }

    /**
     * Binds the active server API implementation.
     *
     * @param api active internal server API
     */
    public static void setServerAPI(InternalMapFrontiersServerAPI api) {
        assertTrustedCaller();
        MapFrontiersAPI.setServerAPI(api);
    }

    /**
     * Clears the active client API implementation.
     */
    public static void clearClientAPI() {
        assertTrustedCaller();
        MapFrontiersAPI.clearClientAPI();
    }

    /**
     * Clears the active server API implementation.
     */
    public static void clearServerAPI() {
        assertTrustedCaller();
        MapFrontiersAPI.clearServerAPI();
    }

    /**
     * Overrides the logger used by the API.
     *
     * @param logger logger implementation to use
     */
    public static void setLogger(ApiLogger logger) {
        assertTrustedCaller();
        MapFrontiersAPI.setLogger(logger);
    }

    private static void assertTrustedCaller() {
        Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(stream -> stream
                        .map(StackWalker.StackFrame::getDeclaringClass)
                        .filter(clazz -> clazz != MapFrontiersAPIBootstrap.class)
                        .findFirst()
                        .orElse(null));

        String callerName = caller == null ? "<unknown>" : caller.getName();
        if (!TRUSTED_CALLERS.contains(callerName)) {
            throw new SecurityException("Unauthorized API bootstrap caller: " + callerName);
        }
    }
}
