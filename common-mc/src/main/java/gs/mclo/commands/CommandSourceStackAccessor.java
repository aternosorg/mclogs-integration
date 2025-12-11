package gs.mclo.commands;

import gs.mclo.components.MinecraftComponent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permission.HasCommandLevel;
import net.minecraft.server.permissions.PermissionLevel;

import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class CommandSourceStackAccessor implements ICommandSourceAccessor<MinecraftComponent> {
    private final CommandSourceStack source;

    public CommandSourceStackAccessor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return source.permissions().hasPermission(new HasCommandLevel(PermissionLevel.byId(permission.level())))
                || source.permissions().hasPermission(new net.minecraft.server.permissions.Permission.Atom(Identifier.fromNamespaceAndPath("mclogs", permission.node())));
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
    public CompletableFuture<Void> sendFailure(MinecraftComponent message) {
        source.sendFailure(message.getBoxed());
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> sendSuccess(MinecraftComponent message, boolean allowLogging) {
        source.sendSuccess(message::getBoxed, allowLogging);
        return CompletableFuture.completedFuture(null);
    }
}
