package gs.mclo.components;


import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.net.URI;

public class MinecraftComponentFactory implements IComponentFactory<MinecraftComponent, MinecraftStyle, ClickEvent> {
    @Override
    public MinecraftComponent literal(String text) {
        return new MinecraftComponent(text);
    }

    @Override
    public MinecraftComponent empty() {
        return new MinecraftComponent(Component.empty());
    }

    @Override
    public MinecraftStyle style() {
        return new MinecraftStyle(Style.EMPTY);
    }

    @Override
    public ClickEvent clickEvent(ClickEventAction action, String value) {
        return switch (action) {
            case OPEN_URL -> new ClickEvent.OpenUrl(URI.create(value));
            case RUN_COMMAND -> new ClickEvent.RunCommand(value);
        };
    }
}
