package gs.mclo.commands;

import gs.mclo.components.MinecraftComponent;
import net.minecraft.commands.CommandSourceStack;

import java.nio.file.Path;
import java.util.Collection;

public class CommandSourceStackAccessor implements ICommandSourceAccessor<MinecraftComponent> {
    private final CommandSourceStack source;

    public CommandSourceStackAccessor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return source.hasPermission(permission.level());
    }

    @Override
    public Path getRootDirectory() {
        return source.getServer().getServerDirectory();
    }

    @Override
    public Collection<LogDirectory> getLogDirectories() {
        return LogDirectory.getVanillaLogDirectories(getRootDirectory());
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
