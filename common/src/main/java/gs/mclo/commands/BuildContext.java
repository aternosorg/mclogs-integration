package gs.mclo.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import gs.mclo.components.IComponent;

/**
 * A context for building commands.
 *
 * @param <T> Type of the "command source".
 * @param <ComponentType> Type of the components used in this context.
 */
public abstract class BuildContext<T, ComponentType extends IComponent<ComponentType, ?, ?>> {
    /**
     * The environment in which the command is being built.
     */
    public final CommandEnvironment environment;

    /**
     * Constructs a new build context.
     * @param environment The environment in which the command is being built.
     */
    public BuildContext(CommandEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Maps a command source to a command source accessor.
     * @param source The command source to map.
     * @return A command source accessor.
     */
    public abstract ICommandSourceAccessor<ComponentType> mapSource(T source);

    /**
     * Creates a new literal argument builder.
     * @param name The name of the literal.
     * @return A new literal argument builder.
     */
    public abstract LiteralArgumentBuilder<T> literal(String name);

    /**
     * Creates a new required argument builder.
     * @param name The name of the argument.
     * @param type The type of the argument.
     * @return A new required argument builder.
     * @param <V> The type of the argument value.
     */
    public abstract <V> RequiredArgumentBuilder<T, V> argument(String name, ArgumentType<V> type);

    public abstract boolean supportsClickEvents();
}
