package games.alejandrocoria.mapfrontiers.api.server;

import games.alejandrocoria.mapfrontiers.api.event.EventBus;

/**
 * Root entry point for server-side API access.
 * <p>
 * Frontier and collection service methods must be called from the main server thread. Implementations do not schedule
 * calls made from other threads.
 */
@SuppressWarnings("unused")
public interface IMapFrontiersServerAPI {
    /**
     * Frontier operations available on the server side.
     *
     * @return server frontier service
     */
    ServerFrontierService frontiers();

    /**
     * Collection operations available on the server side.
     *
     * @return server collection service
     */
    ServerCollectionService collections();

    /**
     * Event bus scoped to server-side API events.
     *
     * @return server event bus
     */
    EventBus events();
}
