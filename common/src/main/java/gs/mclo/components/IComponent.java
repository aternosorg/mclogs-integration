package gs.mclo.components;

/**
 * An interface for message components which allows platform-agnostic usage.
 * All methods are fluent-setters and return the current instance while modifying the internal state.
 *
 * @param <Self> The type of the implementing class. (Used for chaining)
 * @param <StyleType> The style type of the component.
 * @param <ClickEventType> The click event type of the component.
 */
public interface IComponent<
        Self extends IComponent<Self, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    /**
     * Appends a component to the current component.
     * @param component The component to append.
     * @return The current component.
     */
    Self append(Self component);

    /**
     * Appends a raw string to the current component.
     * @param component The string to append.
     * @return The current component.
     */
    Self append(String component);

    /**
     * Sets the text of the component.
     * @param style The style to apply to the text.
     * @return The current component.
     */
    Self style(StyleType style);
}
