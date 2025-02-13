package gs.mclo.commands;

import gs.mclo.MclogsPlugin;
import gs.mclo.components.AdventureComponent;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;

public class CommandSenderAccessor implements ICommandSourceAccessor<AdventureComponent> {
    protected final MclogsPlugin plugin;
    protected final CommandSender commandSender;

    public CommandSenderAccessor(MclogsPlugin plugin,
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
    public void sendFailure(AdventureComponent message) {
        plugin.audience(commandSender).sendMessage(message.getBoxed());
    }

    @Override
    public void sendSuccess(AdventureComponent message, boolean allowLogging) {
        plugin.audience(commandSender).sendMessage(message.getBoxed());
    }
}
