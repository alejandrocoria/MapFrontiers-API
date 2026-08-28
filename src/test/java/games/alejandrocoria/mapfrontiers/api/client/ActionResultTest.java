package games.alejandrocoria.mapfrontiers.api.client;

import games.alejandrocoria.mapfrontiers.api.model.CollectionDataView;
import games.alejandrocoria.mapfrontiers.api.model.CollectionId;
import games.alejandrocoria.mapfrontiers.api.model.CollectionVisibilitySettings;
import games.alejandrocoria.mapfrontiers.api.model.DimensionId;
import games.alejandrocoria.mapfrontiers.api.model.EntityLifetime;
import games.alejandrocoria.mapfrontiers.api.model.FrontierDataView;
import games.alejandrocoria.mapfrontiers.api.model.FrontierId;
import games.alejandrocoria.mapfrontiers.api.model.FrontierShape;
import games.alejandrocoria.mapfrontiers.api.model.FrontierType;
import games.alejandrocoria.mapfrontiers.api.model.UserRef;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionResultTest {
    private static final UserRef OWNER = new UserRef(UUID.randomUUID(), "owner");

    @Test
    void appliedFrontierResultContainsIdAndSnapshot() {
        FrontierDataView frontier = new FrontierDataView(
                new FrontierId(UUID.randomUUID()),
                FrontierType.PERSONAL,
                EntityLifetime.SESSION_ONLY,
                new DimensionId("minecraft:overworld"),
                0,
                "",
                "",
                FrontierShape.vertex(List.of()),
                Set.of(),
                null,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                OWNER,
                List.of()
        );

        FrontierActionResult result = FrontierActionResult.applied(frontier);

        assertEquals(ActionStatus.APPLIED_LOCAL, result.status());
        assertEquals(Optional.of(frontier.id()), result.frontierId());
        assertEquals(Optional.of(frontier), result.frontier());
    }

    @Test
    void appliedCollectionResultContainsIdAndSnapshot() {
        CollectionDataView collection = new CollectionDataView(
                new CollectionId(UUID.randomUUID()),
                FrontierType.PERSONAL,
                EntityLifetime.SESSION_ONLY,
                OWNER,
                "",
                0,
                CollectionVisibilitySettings.builder().build(),
                null,
                Optional.empty()
        );

        CollectionActionResult result = CollectionActionResult.applied(collection);

        assertEquals(ActionStatus.APPLIED_LOCAL, result.status());
        assertEquals(Optional.of(collection.id()), result.collectionId());
        assertEquals(Optional.of(collection), result.collection());
    }
}
