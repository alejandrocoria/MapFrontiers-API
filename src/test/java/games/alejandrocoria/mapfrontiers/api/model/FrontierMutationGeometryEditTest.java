package games.alejandrocoria.mapfrontiers.api.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FrontierMutationGeometryEditTest {
    @Test
    void preservesOperationOrderAndTypedValues() {
        Point2i inserted = new Point2i(10, 20);
        Point2i replaced = new Point2i(30, 40);

        FrontierMutation mutation = FrontierMutation.builder()
                .removePathPointAt(2)
                .insertPathPointAt(1, inserted)
                .setPathPointAt(0, replaced)
                .reversePath()
                .build();

        List<GeometryEdit> edits = mutation.geometryEdits();
        assertEquals(4, edits.size());
        assertEquals(2, assertInstanceOf(GeometryEdit.RemovePathPointAt.class, edits.get(0)).index());
        GeometryEdit.InsertPathPointAt insertion = assertInstanceOf(GeometryEdit.InsertPathPointAt.class, edits.get(1));
        assertEquals(1, insertion.index());
        assertEquals(inserted, insertion.point());
        GeometryEdit.SetPathPointAt replacement = assertInstanceOf(GeometryEdit.SetPathPointAt.class, edits.get(2));
        assertEquals(0, replacement.index());
        assertEquals(replaced, replacement.point());
        assertInstanceOf(GeometryEdit.ReversePath.class, edits.get(3));
    }

    @Test
    void builderExposesEveryGeometryOperationVariant() {
        Point2i point = new Point2i(1, 2);
        ChunkCoord chunk = new ChunkCoord(3, 4);

        assertEquals(List.of(
                        GeometryEdit.InsertPathPointAt.class,
                        GeometryEdit.InsertPathPointBeforeFirst.class,
                        GeometryEdit.InsertPathPointAfterLast.class,
                        GeometryEdit.InsertPathPointAutomatically.class,
                        GeometryEdit.SetPathPointAt.class,
                        GeometryEdit.RemovePathPointAt.class,
                        GeometryEdit.ReversePath.class
                ), FrontierMutation.builder()
                        .insertPathPointAt(0, point)
                        .insertPathPointBeforeFirst(point)
                        .insertPathPointAfterLast(point)
                        .insertPathPointAutomatically(point)
                        .setPathPointAt(0, point)
                        .removePathPointAt(0)
                        .reversePath()
                        .build()
                        .geometryEdits()
                        .stream()
                        .map(Object::getClass)
                        .toList());

        assertEquals(List.of(
                        GeometryEdit.InsertVertexAt.class,
                        GeometryEdit.InsertVertexAutomatically.class,
                        GeometryEdit.SetVertexAt.class,
                        GeometryEdit.RemoveVertexAt.class
                ), FrontierMutation.builder()
                        .insertVertexAt(0, point)
                        .insertVertexAutomatically(point)
                        .setVertexAt(0, point)
                        .removeVertexAt(0)
                        .build()
                        .geometryEdits()
                        .stream()
                        .map(Object::getClass)
                        .toList());

        assertEquals(List.of(
                        GeometryEdit.AddChunks.class,
                        GeometryEdit.AddChunks.class,
                        GeometryEdit.RemoveChunks.class,
                        GeometryEdit.RemoveChunks.class
                ), FrontierMutation.builder()
                        .addChunk(chunk)
                        .addChunks(Set.of(chunk))
                        .removeChunk(chunk)
                        .removeChunks(Set.of(chunk))
                        .build()
                        .geometryEdits()
                        .stream()
                        .map(Object::getClass)
                        .toList());
    }

    @Test
    void builtMutationAndChunkBatchesAreImmutableSnapshots() {
        Set<ChunkCoord> chunks = new HashSet<>(Set.of(new ChunkCoord(1, 2)));
        FrontierMutation.Builder builder = FrontierMutation.builder().addChunks(chunks);
        FrontierMutation first = builder.build();

        chunks.add(new ChunkCoord(3, 4));
        builder.removeChunk(new ChunkCoord(5, 6));

        assertEquals(1, first.geometryEdits().size());
        GeometryEdit.AddChunks addition = assertInstanceOf(GeometryEdit.AddChunks.class, first.geometryEdits().get(0));
        assertEquals(Set.of(new ChunkCoord(1, 2)), addition.chunks());
        assertThrows(UnsupportedOperationException.class, () -> first.geometryEdits().add(addition));
        assertThrows(UnsupportedOperationException.class, () -> addition.chunks().add(new ChunkCoord(7, 8)));
        assertEquals(2, builder.build().geometryEdits().size());
    }

    @Test
    void rejectsMixedGeometryFamiliesWhenSecondFamilyIsAdded() {
        FrontierMutation.Builder builder = FrontierMutation.builder().insertVertexAt(0, new Point2i(0, 0));

        assertThrows(IllegalStateException.class, () -> builder.addChunk(new ChunkCoord(0, 0)));
    }

    @Test
    void rejectsShapeReplacementAndIncrementalEditsInBothOrders() {
        FrontierShape shape = FrontierShape.path(List.of(new Point2i(0, 0)));

        assertThrows(IllegalStateException.class,
                () -> FrontierMutation.builder().shape(shape).reversePath());
        assertThrows(IllegalStateException.class,
                () -> FrontierMutation.builder().reversePath().shape(shape));
    }

    @Test
    void clearingPendingShapeAllowsIncrementalEdits() {
        FrontierMutation mutation = FrontierMutation.builder()
                .shape(FrontierShape.path(List.of(new Point2i(0, 0))))
                .shape(null)
                .reversePath()
                .build();

        assertTrue(mutation.shape().isEmpty());
        assertEquals(1, mutation.geometryEdits().size());
    }

    @Test
    void stateDependentIndexValidationIsDeferredToApplication() {
        FrontierMutation mutation = FrontierMutation.builder().removeVertexAt(5).build();

        assertEquals(5, assertInstanceOf(GeometryEdit.RemoveVertexAt.class, mutation.geometryEdits().get(0)).index());
    }

    @Test
    void variantsHaveNoPublicConstructionPath() {
        for (Class<?> variant : GeometryEdit.class.getPermittedSubclasses()) {
            for (var constructor : variant.getDeclaredConstructors()) {
                assertFalse(Modifier.isPublic(constructor.getModifiers()), variant.getName());
            }
        }
    }

    @Test
    void sealedInterfaceDirectlyPermitsExactlyThePublicVariants() {
        Set<Class<?>> permittedVariants = Set.of(GeometryEdit.class.getPermittedSubclasses());

        assertEquals(Set.of(
                GeometryEdit.InsertPathPointAt.class,
                GeometryEdit.InsertPathPointBeforeFirst.class,
                GeometryEdit.InsertPathPointAfterLast.class,
                GeometryEdit.InsertPathPointAutomatically.class,
                GeometryEdit.SetPathPointAt.class,
                GeometryEdit.RemovePathPointAt.class,
                GeometryEdit.ReversePath.class,
                GeometryEdit.InsertVertexAt.class,
                GeometryEdit.InsertVertexAutomatically.class,
                GeometryEdit.SetVertexAt.class,
                GeometryEdit.RemoveVertexAt.class,
                GeometryEdit.AddChunks.class,
                GeometryEdit.RemoveChunks.class
        ), permittedVariants);
        assertTrue(permittedVariants.stream().allMatch(type -> Modifier.isPublic(type.getModifiers())));
    }

    @Test
    void equivalentGeometrySequencesParticipateInMutationValueSemantics() {
        FrontierMutation first = FrontierMutation.builder()
                .insertVertexAutomatically(new Point2i(4, 5))
                .removeVertexAt(0)
                .build();
        FrontierMutation second = FrontierMutation.builder()
                .insertVertexAutomatically(new Point2i(4, 5))
                .removeVertexAt(0)
                .build();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertTrue(first.toString().contains("geometryEdits="));
    }
}
