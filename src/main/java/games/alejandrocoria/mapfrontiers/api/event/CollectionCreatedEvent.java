package games.alejandrocoria.mapfrontiers.api.event;

import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;

/**
 * Emitted after a collection is created and visible through API state.
 */
public record CollectionCreatedEvent(CollectionDataView collection) {
}
