package games.alejandrocoria.mapfrontiers.api.external;

import games.alejandrocoria.mapfrontiers.api.model.ChunkCoord;
import games.alejandrocoria.mapfrontiers.api.model.FrontierMutation;
import games.alejandrocoria.mapfrontiers.api.model.GeometryEdit;
import games.alejandrocoria.mapfrontiers.api.model.Point2i;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class GeometryEditConsumerTest {
    @Test
    void publicVariantsExposeTheirPayloadOutsideTheModelPackage() {
        FrontierMutation mutation = FrontierMutation.builder()
                .insertVertexAt(3, new Point2i(10, 20))
                .build();

        GeometryEdit.InsertVertexAt edit = assertInstanceOf(
                GeometryEdit.InsertVertexAt.class,
                mutation.geometryEdits().get(0)
        );
        assertEquals(3, edit.index());
        assertEquals(new Point2i(10, 20), edit.vertex());

        GeometryEdit.AddChunks chunks = assertInstanceOf(
                GeometryEdit.AddChunks.class,
                FrontierMutation.builder().addChunk(new ChunkCoord(1, 2)).build().geometryEdits().get(0)
        );
        assertEquals(Set.of(new ChunkCoord(1, 2)), chunks.chunks());
    }
}
