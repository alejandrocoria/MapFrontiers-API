package games.alejandrocoria.mapfrontiers.api.model;

import java.util.Objects;
import java.util.Set;

/**
 * Immutable, ordered geometry operation contained in a {@link FrontierMutation}.
 * Instances are created through {@link FrontierMutation.Builder}; the public variants allow integrations to inspect
 * the resulting operation sequence without depending on MapFrontiers implementation classes.
 */
@SuppressWarnings("unused")
public sealed interface GeometryEdit permits GeometryEdit.InsertPathPointAt, GeometryEdit.InsertPathPointBeforeFirst,
        GeometryEdit.InsertPathPointAfterLast, GeometryEdit.InsertPathPointAutomatically, GeometryEdit.SetPathPointAt,
        GeometryEdit.RemovePathPointAt, GeometryEdit.ReversePath, GeometryEdit.InsertVertexAt,
        GeometryEdit.InsertVertexAutomatically, GeometryEdit.SetVertexAt, GeometryEdit.RemoveVertexAt,
        GeometryEdit.AddChunks, GeometryEdit.RemoveChunks {
    /** Inserts a point at an explicit index in a Path frontier. */
    final class InsertPathPointAt implements GeometryEdit {
        private final int index;
        private final Point2i point;

        InsertPathPointAt(int index, Point2i point) {
            this.index = index;
            this.point = Objects.requireNonNull(point, "point");
        }

        /** @return insertion index */
        public int index() {
            return index;
        }

        /** @return point to insert */
        public Point2i point() {
            return point;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof InsertPathPointAt that && index == that.index && point.equals(that.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, point);
        }

        @Override
        public String toString() {
            return "InsertPathPointAt[index=" + index + ", point=" + point + "]";
        }
    }

    /** Inserts a point before the first point of a Path frontier. */
    final class InsertPathPointBeforeFirst implements GeometryEdit {
        private final Point2i point;

        InsertPathPointBeforeFirst(Point2i point) {
            this.point = Objects.requireNonNull(point, "point");
        }

        /** @return point to insert */
        public Point2i point() {
            return point;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof InsertPathPointBeforeFirst that && point.equals(that.point);
        }

        @Override
        public int hashCode() {
            return point.hashCode();
        }

        @Override
        public String toString() {
            return "InsertPathPointBeforeFirst[point=" + point + "]";
        }
    }

    /** Inserts a point after the last point of a Path frontier. */
    final class InsertPathPointAfterLast implements GeometryEdit {
        private final Point2i point;

        InsertPathPointAfterLast(Point2i point) {
            this.point = Objects.requireNonNull(point, "point");
        }

        /** @return point to insert */
        public Point2i point() {
            return point;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof InsertPathPointAfterLast that && point.equals(that.point);
        }

        @Override
        public int hashCode() {
            return point.hashCode();
        }

        @Override
        public String toString() {
            return "InsertPathPointAfterLast[point=" + point + "]";
        }
    }

    /** Inserts a point according to the documented automatic Path placement rules. */
    final class InsertPathPointAutomatically implements GeometryEdit {
        private final Point2i point;

        InsertPathPointAutomatically(Point2i point) {
            this.point = Objects.requireNonNull(point, "point");
        }

        /** @return point to insert */
        public Point2i point() {
            return point;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof InsertPathPointAutomatically that && point.equals(that.point);
        }

        @Override
        public int hashCode() {
            return point.hashCode();
        }

        @Override
        public String toString() {
            return "InsertPathPointAutomatically[point=" + point + "]";
        }
    }

    /** Replaces the point at an explicit index in a Path frontier. */
    final class SetPathPointAt implements GeometryEdit {
        private final int index;
        private final Point2i point;

        SetPathPointAt(int index, Point2i point) {
            this.index = index;
            this.point = Objects.requireNonNull(point, "point");
        }

        /** @return replacement index */
        public int index() {
            return index;
        }

        /** @return replacement point */
        public Point2i point() {
            return point;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof SetPathPointAt that && index == that.index && point.equals(that.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, point);
        }

        @Override
        public String toString() {
            return "SetPathPointAt[index=" + index + ", point=" + point + "]";
        }
    }

    /** Removes the point at an explicit index in a Path frontier. */
    final class RemovePathPointAt implements GeometryEdit {
        private final int index;

        RemovePathPointAt(int index) {
            this.index = index;
        }

        /** @return removal index */
        public int index() {
            return index;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof RemovePathPointAt that && index == that.index;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(index);
        }

        @Override
        public String toString() {
            return "RemovePathPointAt[index=" + index + "]";
        }
    }

    /** Reverses the stored point order of a Path frontier. */
    final class ReversePath implements GeometryEdit {
        ReversePath() {
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof ReversePath;
        }

        @Override
        public int hashCode() {
            return ReversePath.class.hashCode();
        }

        @Override
        public String toString() {
            return "ReversePath[]";
        }
    }

    /** Inserts a vertex at an explicit index in a Vertex frontier. */
    final class InsertVertexAt implements GeometryEdit {
        private final int index;
        private final Point2i vertex;

        InsertVertexAt(int index, Point2i vertex) {
            this.index = index;
            this.vertex = Objects.requireNonNull(vertex, "vertex");
        }

        /** @return insertion index */
        public int index() {
            return index;
        }

        /** @return vertex to insert */
        public Point2i vertex() {
            return vertex;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof InsertVertexAt that && index == that.index && vertex.equals(that.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, vertex);
        }

        @Override
        public String toString() {
            return "InsertVertexAt[index=" + index + ", vertex=" + vertex + "]";
        }
    }

    /** Inserts a vertex according to the documented automatic Vertex placement rules. */
    final class InsertVertexAutomatically implements GeometryEdit {
        private final Point2i vertex;

        InsertVertexAutomatically(Point2i vertex) {
            this.vertex = Objects.requireNonNull(vertex, "vertex");
        }

        /** @return vertex to insert */
        public Point2i vertex() {
            return vertex;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof InsertVertexAutomatically that && vertex.equals(that.vertex);
        }

        @Override
        public int hashCode() {
            return vertex.hashCode();
        }

        @Override
        public String toString() {
            return "InsertVertexAutomatically[vertex=" + vertex + "]";
        }
    }

    /** Replaces the vertex at an explicit index in a Vertex frontier. */
    final class SetVertexAt implements GeometryEdit {
        private final int index;
        private final Point2i vertex;

        SetVertexAt(int index, Point2i vertex) {
            this.index = index;
            this.vertex = Objects.requireNonNull(vertex, "vertex");
        }

        /** @return replacement index */
        public int index() {
            return index;
        }

        /** @return replacement vertex */
        public Point2i vertex() {
            return vertex;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof SetVertexAt that && index == that.index && vertex.equals(that.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, vertex);
        }

        @Override
        public String toString() {
            return "SetVertexAt[index=" + index + ", vertex=" + vertex + "]";
        }
    }

    /** Removes the vertex at an explicit index in a Vertex frontier. */
    final class RemoveVertexAt implements GeometryEdit {
        private final int index;

        RemoveVertexAt(int index) {
            this.index = index;
        }

        /** @return removal index */
        public int index() {
            return index;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof RemoveVertexAt that && index == that.index;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(index);
        }

        @Override
        public String toString() {
            return "RemoveVertexAt[index=" + index + "]";
        }
    }

    /** Adds a set of chunks to a Chunk frontier. */
    final class AddChunks implements GeometryEdit {
        private final Set<ChunkCoord> chunks;

        AddChunks(Set<ChunkCoord> chunks) {
            this.chunks = Set.copyOf(Objects.requireNonNull(chunks, "chunks"));
        }

        /** @return immutable chunks to add */
        public Set<ChunkCoord> chunks() {
            return chunks;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof AddChunks that && chunks.equals(that.chunks);
        }

        @Override
        public int hashCode() {
            return chunks.hashCode();
        }

        @Override
        public String toString() {
            return "AddChunks[chunks=" + chunks + "]";
        }
    }

    /** Removes a set of chunks from a Chunk frontier. */
    final class RemoveChunks implements GeometryEdit {
        private final Set<ChunkCoord> chunks;

        RemoveChunks(Set<ChunkCoord> chunks) {
            this.chunks = Set.copyOf(Objects.requireNonNull(chunks, "chunks"));
        }

        /** @return immutable chunks to remove */
        public Set<ChunkCoord> chunks() {
            return chunks;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof RemoveChunks that && chunks.equals(that.chunks);
        }

        @Override
        public int hashCode() {
            return chunks.hashCode();
        }

        @Override
        public String toString() {
            return "RemoveChunks[chunks=" + chunks + "]";
        }
    }
}
