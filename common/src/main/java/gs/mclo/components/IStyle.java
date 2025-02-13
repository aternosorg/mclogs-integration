package gs.mclo.components;

/**
 * An interface for message component styles which allows platform-agnostic usage.
 * All methods are fluent-setters and return the current instance while modifying the internal state.
 *
 * @param <Self> The type of the implementing class. (Used for chaining)
 * @param <ClickEventType> The click event type of the component.
 */
public interface IStyle<
        Self extends IStyle<Self, ClickEventType>,
        ClickEventType
        > {
    /**
     * Make the text underlined
     * @return The current style
     */
    Self underlined();

    /**
     * Add a click event to the text
     * @return The current style
     */
    Self clickEvent(ClickEventType clickEvent);
}
