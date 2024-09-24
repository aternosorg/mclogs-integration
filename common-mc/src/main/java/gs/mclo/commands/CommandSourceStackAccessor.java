package gs.mclo.commands;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;

public class CommandSourceStackAccessor implements CommandSourceAccessor {
    private final CommandSourceStack source;

    public CommandSourceStackAccessor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(int level) {
        return source.hasPermission(level);
    }

    @Override
    public String getMinecraftVersion() {
        return source.getServer().getServerVersion();
    }

    @Override
    public Path getDirectory() {
        return source.getServer().getServerDirectory();
    }

    @Override
    public void sendFailure(Component message) {
        source.sendFailure(message);
    }

    @Override
    public void sendSuccess(Component message, boolean allowLogging) {
        source.sendSuccess(() -> message, allowLogging);
    }
}
