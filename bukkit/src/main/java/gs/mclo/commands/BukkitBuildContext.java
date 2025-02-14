package gs.mclo.commands;

import gs.mclo.MclogsBukkitPlugin;
import gs.mclo.components.AdventureComponent;
import org.bukkit.command.CommandSender;

public class BukkitBuildContext extends BuildContext<CommandSender, AdventureComponent> {
    protected final MclogsBukkitPlugin plugin;

    public BukkitBuildContext(MclogsBukkitPlugin plugin) {
        super(CommandEnvironment.DEDICATED_SERVER);
        this.plugin = plugin;
    }

    @Override
    public ICommandSourceAccessor<AdventureComponent> mapSource(CommandSender source) {
        return new BukkitCommandSenderAccessor(plugin, source);
    }
}
