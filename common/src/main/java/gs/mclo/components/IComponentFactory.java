package gs.mclo.components;

public interface IComponentFactory<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    ComponentType literal(String text);

    ComponentType empty();

    StyleType style();

    ClickEventType clickEvent(ClickEventAction action, String value);
}
