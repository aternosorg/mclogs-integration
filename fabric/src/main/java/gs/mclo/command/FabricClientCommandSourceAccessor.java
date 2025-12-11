package gs.mclo.command;

import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.commands.LogDirectory;
import gs.mclo.commands.Permission;
import gs.mclo.components.MinecraftComponent;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.server.permissions.Permission.HasCommandLevel;
import net.minecraft.server.permissions.PermissionLevel;

import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class FabricClientCommandSourceAccessor implements ICommandSourceAccessor<MinecraftComponent> {
    private final FabricClientCommandSource source;

    public FabricClientCommandSourceAccessor(FabricClientCommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return source.permissions().hasPermission(new HasCommandLevel(PermissionLevel.byId(permission.level())));
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
    public CompletableFuture<Void> sendFailure(MinecraftComponent message) {
        return Minecraft.getInstance().submit(() -> {
            source.sendError(message.getBoxed());
        });
    }

    @Override
    public CompletableFuture<Void> sendSuccess(MinecraftComponent message, boolean allowLogging) {
        return Minecraft.getInstance().submit(() -> {
            source.sendFeedback(message.getBoxed());
        });
    }
}
