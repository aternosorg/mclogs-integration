package gs.mclo.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public abstract class BuildContext<T> {
    public final CommandEnvironment environment;

    public BuildContext(CommandEnvironment environment) {
        this.environment = environment;
    }

    public abstract CommandSourceAccessor mapSource(T source);

    public abstract LiteralArgumentBuilder<T> literal(String name);

    public abstract <V> RequiredArgumentBuilder<T, V> argument(String name, ArgumentType<V> type);

    public abstract boolean supportsClickEvents();
}
