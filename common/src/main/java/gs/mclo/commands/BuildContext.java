package gs.mclo.commands;

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
     * Are click events with client commands supported in this context?
     * Forge (and older NeoForge versions) do not support this.
     * @return True if click events are supported, false otherwise.
     */
    public boolean supportsClientCommandClickEvents() {
        return true;
    }
}
