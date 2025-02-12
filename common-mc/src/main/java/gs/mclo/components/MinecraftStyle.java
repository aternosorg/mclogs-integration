package gs.mclo.components;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;

public class MinecraftStyle implements IStyle<MinecraftStyle, ClickEvent> {
    protected Style boxed;

    public MinecraftStyle(Style style) {
        this.boxed = style;
    }

    public Style getBoxed() {
        return boxed;
    }

    @Override
    public MinecraftStyle underlined() {
        boxed = boxed.withUnderlined(true);
        return this;
    }

    @Override
    public MinecraftStyle clickEvent(ClickEvent clickEvent) {
        boxed.withClickEvent(clickEvent);
        return null;
    }
}
