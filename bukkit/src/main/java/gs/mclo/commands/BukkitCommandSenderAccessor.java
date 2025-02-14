package gs.mclo.commands;

import command.AdventureCommandSourceAccessor;
import gs.mclo.MclogsBukkitPlugin;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;

public class BukkitCommandSenderAccessor extends AdventureCommandSourceAccessor {
    protected final MclogsBukkitPlugin plugin;
    protected final CommandSender commandSender;

    public BukkitCommandSenderAccessor(MclogsBukkitPlugin plugin,
                                       CommandSender commandSender) {
        this.plugin = plugin;
        this.commandSender = commandSender;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return commandSender.hasPermission(permission.node());
    }

    @Override
    public Path getDirectory() {
        return Path.of(".");
    }

    @Override
    protected Audience getAudience() {
        return plugin.audience(commandSender);
    }
}
