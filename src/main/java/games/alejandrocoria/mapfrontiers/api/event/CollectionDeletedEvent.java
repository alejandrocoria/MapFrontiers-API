package games.alejandrocoria.mapfrontiers.api.event;

import games.alejandrocoria.mapfrontiers.api.model.CollectionId;

/**
 * Emitted after a collection is deleted.
 */
public record CollectionDeletedEvent(CollectionId collectionId) {
}
