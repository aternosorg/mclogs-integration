package gs.mclo.commands;

import gs.mclo.MclogsPlugin;
import gs.mclo.components.AdventureComponent;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
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
    public boolean hasPermission(int level) {
        // TODO: Think about how I can implement this
        return true;
    }

    @Override
    public String getMinecraftVersion() {
        return Bukkit.getVersion();
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
