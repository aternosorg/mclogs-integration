package gs.mclo.command;

import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.commands.LogDirectory;
import gs.mclo.commands.Permission;
import gs.mclo.components.MinecraftComponent;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.nio.file.Path;
import java.util.Collection;

public class FabricClientCommandSourceAccessor implements ICommandSourceAccessor<MinecraftComponent> {
    private final FabricClientCommandSource source;

    public FabricClientCommandSourceAccessor(FabricClientCommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return source.hasPermission(permission.level());
    }

    @Override
    public Path getRootDirectory() {
        return source.getClient().gameDirectory.toPath();
    }

    @Override
    public Collection<LogDirectory> getLogDirectories() {
        return LogDirectory.getVanillaLogDirectories(getRootDirectory());
    }

    @Override
    public void sendFailure(MinecraftComponent message) {
        source.sendError(message.getBoxed());
    }

    @Override
    public void sendSuccess(MinecraftComponent message, boolean allowLogging) {
        source.sendFeedback(message.getBoxed());
    }
}
