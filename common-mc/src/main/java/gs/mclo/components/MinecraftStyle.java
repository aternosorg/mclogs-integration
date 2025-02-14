package gs.mclo.components;

import net.minecraft.ChatFormatting;
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
    public MinecraftStyle color(Color color) {
        boxed = boxed.withColor(switch (color) {
            case RED -> ChatFormatting.RED;
        });
        return this;
    }

    @Override
    public MinecraftStyle underlined() {
        boxed = boxed.withUnderlined(true);
        return this;
    }

    @Override
    public MinecraftStyle italic() {
        boxed = boxed.withItalic(true);
        return this;
    }

    @Override
    public MinecraftStyle clickEvent(ClickEvent clickEvent) {
        boxed = boxed.withClickEvent(clickEvent);
        return this;
    }

    @Override
    public MinecraftStyle copy() {
        return new MinecraftStyle(boxed);
    }
}
