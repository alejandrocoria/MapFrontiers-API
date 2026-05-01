package games.alejandrocoria.mapfrontiers.api.model;

/**
 * Visual style for a path frontier.
 *
 * @param startMarker marker used at the start of the path
 * @param innerMarker marker used between start and end
 * @param endMarker marker used at the end of the path
 * @param segmentMarker marker used for path segments
 * @param labelAtStart whether the label can appear at the start
 * @param labelAtMiddle whether the label can appear at the middle
 * @param labelAtEnd whether the label can appear at the end
 */
public record PathStyle(PathMarkerId startMarker,
                        PathMarkerId innerMarker,
                        PathMarkerId endMarker,
                        PathMarkerId segmentMarker,
                        boolean labelAtStart,
                        boolean labelAtMiddle,
                        boolean labelAtEnd) {
    /**
     * Normalizes null marker ids to MapFrontiers defaults.
     *
     * @param startMarker start marker
     * @param innerMarker inner marker
     * @param endMarker end marker
     * @param segmentMarker segment marker
     * @param labelAtStart start label flag
     * @param labelAtMiddle middle label flag
     * @param labelAtEnd end label flag
     */
    public PathStyle {
        startMarker = startMarker == null ? PathMarkerId.BIG_DOT : startMarker;
        innerMarker = innerMarker == null ? PathMarkerId.NONE : innerMarker;
        endMarker = endMarker == null ? PathMarkerId.BIG_DOT : endMarker;
        segmentMarker = segmentMarker == null ? PathMarkerId.SMALL_DOT : segmentMarker;
    }

    /**
     * Returns the default path style used by MapFrontiers.
     *
     * @return default path style
     */
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
