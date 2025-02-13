package gs.mclo.components;

/**
 * An interface for creating message components which allows platform-agnostic usage.
 *
 * @param <ComponentType> The type of the components produced by this factory.
 * @param <StyleType> The type of the style produced by this factory.
 * @param <ClickEventType> The type of the click event produced by this factory.
 */
public interface IComponentFactory<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    /**
     * Create a new text component with the given text
     * @param text The text of the component
     * @return The new component
     */
    ComponentType literal(String text);

    /**
     * Create a new empty component
     * @return The new component
     */
    ComponentType empty();

    /**
     * Create a new empty style
     * @return The new style
     */
    StyleType style();

    /**
     * Create a new click event
     * @param action The action of the click event
     * @param value The value of the click event (e.g. the command to run or the URL to open)
     * @return The new click event
     */
    ClickEventType clickEvent(ClickEventAction action, String value);
}
