package gs.mclo.commands;

import gs.mclo.MclogsBungeePlugin;
import gs.mclo.components.AdventureComponent;
import net.md_5.bungee.api.CommandSender;

/**
 * A BuildContext implementation for BungeeCord
 */
public class BungeeBuildContext extends BuildContext<CommandSender, AdventureComponent> {
    /**
     * The plugin instance
     */
    private final MclogsBungeePlugin plugin;

    /**
     * Create a new BungeeBuildContext
     * @param plugin The plugin instance
     */
    public BungeeBuildContext(MclogsBungeePlugin plugin) {
        super(CommandEnvironment.PROXY);
        this.plugin = plugin;
    }

    @Override
    public ICommandSourceAccessor<AdventureComponent> mapSource(CommandSender source) {
        return new BungeeCommandSenderAccessor(plugin, source);
    }
}
