package gs.mclo.components;

public interface IStyle<
        Self extends IStyle<Self, ClickEventType>,
        ClickEventType
        > {
    Self underlined();

    Self clickEvent(ClickEventType clickEvent);
}
