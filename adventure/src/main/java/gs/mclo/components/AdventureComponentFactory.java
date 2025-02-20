package gs.mclo.components;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.Style;

/**
 * A IComponentFactory implementation for Adventure components
 */
public class AdventureComponentFactory implements IComponentFactory<
        AdventureComponent,
        AdventureStyle,
        ClickEvent
        > {
    @Override
    public AdventureComponent literal(String text) {
        return new AdventureComponent(Component.text(text));
    }

    @Override
    public AdventureComponent empty() {
        return new AdventureComponent(Component.empty());
    }

    @Override
    public AdventureStyle style() {
        return new AdventureStyle(Style.empty());
    }

    @Override
    public ClickEvent clickEvent(ClickEventAction action, String value) {
        return switch (action) {
            case OPEN_URL -> ClickEvent.openUrl(value);
            case RUN_COMMAND -> ClickEvent.runCommand(value);
        };
    }
}
