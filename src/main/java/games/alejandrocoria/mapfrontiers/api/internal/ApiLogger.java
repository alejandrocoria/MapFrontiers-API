package games.alejandrocoria.mapfrontiers.api.internal;

/**
 * Internal logging abstraction used by the API to avoid depending on a specific logging backend.
 */
public interface ApiLogger {
    /**
     * Logs an informational message.
     *
     * @param message log message
     */
    void info(String message);

    /**
     * Logs a warning message.
     *
     * @param message log message
     */
    void warn(String message);

    /**
     * Logs an error message.
     *
     * @param message log message
     */
    void error(String message);

    /**
     * Logs an error message with the associated failure.
     *
     * @param message log message
     * @param throwable failure to include in the log
     */
    void error(String message, Throwable throwable);
}
