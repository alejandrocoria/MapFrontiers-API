package games.alejandrocoria.mapfrontiers.api.event;

import java.util.function.Consumer;

/**
 * Event stream for API-level frontier and collection events.
 * <p>
 * The bus remains a single stream shared by the API surface. The API reflects the emission semantics already used by the
 * underlying mod: it does not fabricate extra events for request objects or new entity types, and it does not filter
 * internal events to invent a different public lifecycle.
 */
@SuppressWarnings("unused")
public interface EventBus {
    /**
     * Handle returned by {@link #subscribe(Class, Consumer)}.
     */
    interface Subscription {
        /**
         * Removes the listener from the event bus.
         */
        void unsubscribe();
    }

    /**
     * Subscribes a listener to a concrete event type.
     * Frontier and collection events are subscribed independently by their concrete record class, even though they share
     * the same bus instance.
     *
     * @param <T> event payload type
     * @param eventType event class to listen for
     * @param listener callback invoked for each event instance
     * @return subscription handle for manual unsubscribe
     */
    <T> Subscription subscribe(Class<T> eventType, Consumer<T> listener);
}
