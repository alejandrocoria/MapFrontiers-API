package games.alejandrocoria.mapfrontiers.api.model;

import java.util.List;
import java.util.Objects;

/**
 * Frontier geometry definition.
 * Use {@link #vertex(List)}, {@link #chunk(List)}, or {@link #path(List)} to create instances.
 */
public final class FrontierShape {
    private final FrontierShapeType type;
    private final List<Point2i> vertices;
    private final List<ChunkCoord> chunks;
    private final List<Point2i> points;

    private FrontierShape(FrontierShapeType type, List<Point2i> vertices, List<ChunkCoord> chunks, List<Point2i> points) {
        if (type == null) {
            throw new IllegalArgumentException("Shape type cannot be null");
        }
        this.type = type;
        this.vertices = vertices;
        this.chunks = chunks;
        this.points = points;
    }

    /**
     * Creates a vertex-based closed polygon shape.
     */
    public static FrontierShape vertex(List<Point2i> vertices) {
        List<Point2i> safeVertices = vertices == null ? List.of() : List.copyOf(vertices);
        return new FrontierShape(FrontierShapeType.VERTEX, safeVertices, null, null);
    }

    /**
     * Creates a chunk-based shape.
     */
    public static FrontierShape chunk(List<ChunkCoord> chunks) {
        List<ChunkCoord> safeChunks = chunks == null ? List.of() : List.copyOf(chunks);
        return new FrontierShape(FrontierShapeType.CHUNK, null, safeChunks, null);
    }

    /**
     * Creates an open path based on ordered points.
     */
    public static FrontierShape path(List<Point2i> points) {
        List<Point2i> safePoints = points == null ? List.of() : List.copyOf(points);
        return new FrontierShape(FrontierShapeType.PATH, null, null, safePoints);
    }

    public FrontierShapeType type() {
        return type;
    }

    public List<Point2i> vertices() {
        return vertices;
    }

    public List<ChunkCoord> chunks() {
        return chunks;
    }

    public List<Point2i> points() {
        return points;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FrontierShape that)) {
            return false;
        }
        return type == that.type
                && Objects.equals(vertices, that.vertices)
                && Objects.equals(chunks, that.chunks)
                && Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, vertices, chunks, points);
    }

    @Override
    public String toString() {
        return "FrontierShape[type=" + type + ", vertices=" + vertices + ", chunks=" + chunks + ", points=" + points + "]";
    }
}
