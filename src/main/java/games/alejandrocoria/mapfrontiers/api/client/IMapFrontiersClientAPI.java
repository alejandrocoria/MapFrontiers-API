package games.alejandrocoria.mapfrontiers.api.client;

import games.alejandrocoria.mapfrontiers.api.event.EventBus;

/**
 * Root entry point for client-side API access.
 */
@SuppressWarnings("unused")
public interface IMapFrontiersClientAPI {
    /**
     * Frontier operations available on the client side.
     *
     * @return client frontier service
     */
    ClientFrontierService frontiers();

    /**
     * Collection operations available on the client side.
     *
     * @return client collection service
     */
    ClientCollectionService collections();

    /**
     * Event bus scoped to client-side API events.
     *
     * @return client event bus
     */
    EventBus events();
}
