package gs.mclo.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import gs.mclo.MclogsPlugin;
import gs.mclo.components.AdventureComponent;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

public class BukkitBuildContext extends BuildContext<CommandSender, AdventureComponent> {
    protected final MclogsPlugin plugin;
    protected final BukkitAudiences adventure;

    public BukkitBuildContext(MclogsPlugin plugin, BukkitAudiences adventure) {
        super(CommandEnvironment.DEDICATED_SERVER);
        this.plugin = plugin;
        this.adventure = adventure;
    }

    @Override
    public ICommandSourceAccessor<AdventureComponent> mapSource(CommandSender source) {
        return new CommandSenderAccessor(plugin, adventure, source);
    }

    @Override
    public LiteralArgumentBuilder<CommandSender> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    @Override
    public <V> RequiredArgumentBuilder<CommandSender, V> argument(String name, ArgumentType<V> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    @Override
    public boolean supportsClickEvents() {
        return true;
    }
}
