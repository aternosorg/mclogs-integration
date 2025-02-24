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
     * Set the color of the text
     * @param color The color to set
     * @return The current style
     */
    Self color(Color color);

    /**
     * Make the text underlined
     * @return The current style
     */
    Self underlined();

    /**
     * Make the text italic
     * @return The current style
     */
    Self italic();

    /**
     * Add a click event to the text
     * @param clickEvent The click event to add
     * @return The current style
     */
    Self clickEvent(ClickEventType clickEvent);
}
