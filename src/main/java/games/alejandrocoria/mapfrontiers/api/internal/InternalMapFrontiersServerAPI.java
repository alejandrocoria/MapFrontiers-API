package games.alejandrocoria.mapfrontiers.api.internal;

import games.alejandrocoria.mapfrontiers.api.event.EventBus;

/**
 * Internal server-side API root exposed by the MapFrontiers runtime to plugin wrappers.
 */
public interface InternalMapFrontiersServerAPI {
    /**
     * Returns the plugin-scoped frontier service.
     *
     * @return internal server frontier service
     */
    PluginScopedServerFrontierService frontiers();
    /**
     * Returns the plugin-scoped collection service.
     *
     * @return internal server collection service
     */
    PluginScopedServerCollectionService collections();
    /**
     * Returns the shared event bus.
     *
     * @return internal event bus
     */
    EventBus events();
}
