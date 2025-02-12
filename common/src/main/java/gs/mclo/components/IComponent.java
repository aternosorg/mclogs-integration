package gs.mclo.components;

public interface IComponent<
        Self extends IComponent<Self, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    Self append(Self component);

    Self append(String component);

    Self setStyle(StyleType style);
}
