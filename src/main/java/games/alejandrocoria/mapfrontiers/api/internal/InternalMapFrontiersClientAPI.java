package games.alejandrocoria.mapfrontiers.api.internal;

import games.alejandrocoria.mapfrontiers.api.event.EventBus;

/**
 * Internal client-side API root exposed by the MapFrontiers runtime to plugin wrappers.
 */
public interface InternalMapFrontiersClientAPI {
    /**
     * Returns the plugin-scoped frontier service.
     *
     * @return internal client frontier service
     */
    PluginScopedClientFrontierService frontiers();
    /**
     * Returns the plugin-scoped collection service.
     *
     * @return internal client collection service
     */
    PluginScopedClientCollectionService collections();
    /**
     * Returns the shared event bus.
     *
     * @return internal event bus
     */
    EventBus events();
}
