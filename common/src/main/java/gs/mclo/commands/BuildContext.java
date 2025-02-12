package gs.mclo.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import gs.mclo.components.IComponent;

public abstract class BuildContext<T, ComponentType extends IComponent<ComponentType, ?, ?>> {
    public final CommandEnvironment environment;

    public BuildContext(CommandEnvironment environment) {
        this.environment = environment;
    }

    public abstract ICommandSourceAccessor<ComponentType> mapSource(T source);

    public abstract LiteralArgumentBuilder<T> literal(String name);

    public abstract <V> RequiredArgumentBuilder<T, V> argument(String name, ArgumentType<V> type);

    public abstract boolean supportsClickEvents();
}
