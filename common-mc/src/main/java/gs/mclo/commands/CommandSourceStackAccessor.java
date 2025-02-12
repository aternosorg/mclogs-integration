package gs.mclo.commands;

import gs.mclo.components.MinecraftComponent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;

public class CommandSourceStackAccessor implements ICommandSourceAccessor<MinecraftComponent> {
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
    public void sendFailure(MinecraftComponent message) {
        source.sendFailure(message.getBoxed());
    }

    @Override
    public void sendSuccess(MinecraftComponent message, boolean allowLogging) {
        source.sendSuccess(message::getBoxed, allowLogging);
    }
}
