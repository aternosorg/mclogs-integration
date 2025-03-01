package gs.mclo.commands;

import gs.mclo.command.AdventureCommandSourceAccessor;
import gs.mclo.MclogsBukkitPlugin;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;
import java.util.Collection;

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
    public Path getRootDirectory() {
        return Path.of(".");
    }

    @Override
    public Collection<LogDirectory> getLogDirectories() {
        return LogDirectory.getVanillaLogDirectories(getRootDirectory());
    }

    @Override
    protected Audience getAudience() {
        return plugin.audience(commandSender);
    }
}
