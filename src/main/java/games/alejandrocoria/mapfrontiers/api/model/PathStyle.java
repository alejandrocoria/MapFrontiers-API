package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Visual style for a path frontier.
 */
public record PathStyle(PathMarkerId startMarker,
                        PathMarkerId innerMarker,
                        PathMarkerId endMarker,
                        PathMarkerId segmentMarker,
                        boolean labelAtStart,
                        boolean labelAtMiddle,
                        boolean labelAtEnd) {
    public PathStyle {
        startMarker = startMarker == null ? PathMarkerId.BIG_DOT : startMarker;
        innerMarker = innerMarker == null ? PathMarkerId.NONE : innerMarker;
        endMarker = endMarker == null ? PathMarkerId.BIG_DOT : endMarker;
        segmentMarker = segmentMarker == null ? PathMarkerId.SMALL_DOT : segmentMarker;
    }

    public static PathStyle defaultStyle() {
        return new PathStyle(
                PathMarkerId.BIG_DOT,
                PathMarkerId.NONE,
                PathMarkerId.BIG_DOT,
                PathMarkerId.SMALL_DOT,
                true,
                false,
                false
        );
    }
}
