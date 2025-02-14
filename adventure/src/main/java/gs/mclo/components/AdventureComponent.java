package gs.mclo.components;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;

public class AdventureComponent implements IComponent<AdventureComponent, AdventureStyle, ClickEvent> {
    protected TextComponent boxed;

    public AdventureComponent(TextComponent component) {
        this.boxed = component;
    }

    public TextComponent getBoxed() {
        return boxed;
    }

    @Override
    public AdventureComponent append(AdventureComponent component) {
        boxed = boxed.append(component.getBoxed());
        return this;
    }

    @Override
    public AdventureComponent append(String component) {
        return append(new AdventureComponent(Component.text(component)));
    }

    @Override
    public AdventureComponent style(AdventureStyle style) {
        boxed = boxed.style(style.getBoxed());
        return this;
    }
}
