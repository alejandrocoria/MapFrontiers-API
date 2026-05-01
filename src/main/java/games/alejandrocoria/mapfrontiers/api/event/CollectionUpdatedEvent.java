package games.alejandrocoria.mapfrontiers.api.event;

import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;

/**
 * Emitted after a collection is updated and visible through API state.
 * This also covers collection membership changes when the underlying mod reports them as collection updates.
 */
public record CollectionUpdatedEvent(CollectionDataView collection) {
}
