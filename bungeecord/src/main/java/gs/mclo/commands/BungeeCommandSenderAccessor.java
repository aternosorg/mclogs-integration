package gs.mclo.commands;

import command.AdventureCommandSourceAccessor;
import gs.mclo.MclogsBungeePlugin;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;

import java.nio.file.Path;

public class BungeeCommandSenderAccessor extends AdventureCommandSourceAccessor {
    private final MclogsBungeePlugin plugin;
    private final CommandSender sender;

    public BungeeCommandSenderAccessor(MclogsBungeePlugin plugin,
                                       CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return sender.hasPermission(permission.node());
    }

    @Override
    public Path getDirectory() {
        return Path.of(".");
    }

    @Override
    protected Audience getAudience() {
        return plugin.audience(sender);
    }
}
