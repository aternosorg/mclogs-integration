package gs.mclo.commands;

import gs.mclo.MclogsPlugin;
import gs.mclo.components.AdventureComponent;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;

public class CommandSenderAccessor implements ICommandSourceAccessor<AdventureComponent> {
    protected final MclogsPlugin plugin;
    protected final BukkitAudiences adventure;
    protected final CommandSender commandSender;

    public CommandSenderAccessor(MclogsPlugin plugin,
                                 BukkitAudiences adventure,
                                 CommandSender commandSender) {
        this.plugin = plugin;
        this.adventure = adventure;
        this.commandSender = commandSender;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        StringBuilder node = new StringBuilder();
        for (String part : permission.nodeParts()) {
            node.append(part);

            if (commandSender.hasPermission(node + ".*")) {
                return true;
            }
        }

        return commandSender.hasPermission(node.toString());
    }

    @Override
    public Path getDirectory() {
        return Path.of(".");
    }

    @Override
    public void sendFailure(AdventureComponent message) {
        adventure.sender(commandSender).sendMessage(message.getBoxed());
    }

    @Override
    public void sendSuccess(AdventureComponent message, boolean allowLogging) {
        adventure.sender(commandSender).sendMessage(message.getBoxed());
    }
}
