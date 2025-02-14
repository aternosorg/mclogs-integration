package gs.mclo.commands;

import gs.mclo.MclogsBungeePlugin;
import gs.mclo.components.AdventureComponent;
import net.md_5.bungee.api.CommandSender;

public class BungeeBuildContext extends BuildContext<CommandSender, AdventureComponent> {
    private final MclogsBungeePlugin plugin;

    public BungeeBuildContext(MclogsBungeePlugin plugin) {
        super(CommandEnvironment.PROXY);
        this.plugin = plugin;
    }

    @Override
    public ICommandSourceAccessor<AdventureComponent> mapSource(CommandSender source) {
        return new BungeeCommandSenderAccessor(plugin, source);
    }
}
