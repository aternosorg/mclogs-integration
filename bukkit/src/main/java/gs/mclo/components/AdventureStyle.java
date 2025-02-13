package gs.mclo.components;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public class AdventureStyle implements IStyle<AdventureStyle, ClickEvent> {
    protected final Style boxed;

    public AdventureStyle(Style boxed) {
        this.boxed = boxed;
    }

    public Style getBoxed() {
        return boxed;
    }

    @Override
    public AdventureStyle underlined() {
        boxed.decorate(TextDecoration.UNDERLINED);
        return this;
    }

    @Override
    public AdventureStyle clickEvent(ClickEvent clickEvent) {
        boxed.clickEvent(clickEvent);
        return this;
    }
}
