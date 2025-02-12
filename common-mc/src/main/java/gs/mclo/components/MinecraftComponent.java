package gs.mclo.components;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MinecraftComponent implements IComponent<MinecraftComponent, MinecraftStyle, ClickEvent> {
    private final MutableComponent boxed;

    public MinecraftComponent(String content) {
        this(Component.literal(content));
    }

    public MinecraftComponent(MutableComponent component) {
        this.boxed = component;
    }

    @Override
    public MinecraftComponent append(MinecraftComponent component) {
        boxed.append(component.boxed);
        return this;
    }

    @Override
    public MinecraftComponent append(String component) {
        return append(new MinecraftComponent(component));
    }

    @Override
    public MinecraftComponent setStyle(MinecraftStyle style) {
        boxed.setStyle(style.getBoxed());
        return this;
    }

    public MutableComponent getBoxed() {
        return boxed;
    }
}
