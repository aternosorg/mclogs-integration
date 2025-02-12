package gs.mclo.command;

import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.components.MinecraftComponent;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;

public class FabricClientCommandSourceAccessor implements ICommandSourceAccessor<MinecraftComponent> {
    private final FabricClientCommandSource source;

    public FabricClientCommandSourceAccessor(FabricClientCommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(int level) {
        return source.hasPermission(level);
    }

    @Override
    public String getMinecraftVersion() {
        return source.getClient().getLaunchedVersion();
    }

    @Override
    public Path getDirectory() {
        return source.getClient().gameDirectory.toPath();
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
