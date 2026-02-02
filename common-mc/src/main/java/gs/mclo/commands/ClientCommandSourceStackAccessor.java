package gs.mclo.commands;

import gs.mclo.components.MinecraftComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;

import java.nio.file.Path;

public class ClientCommandSourceStackAccessor extends CommandSourceStackAccessor {
    public ClientCommandSourceStackAccessor(CommandSourceStack source) {
        super(source);
    }

    @Override
    public Path getRootDirectory() {
        return Minecraft.getInstance().gameDirectory.toPath();
    }

    @Override
    public void sendFailure(MinecraftComponent message) {
        Minecraft.getInstance().submit(() -> super.sendFailure(message));
    }

    @Override
    public void sendSuccess(MinecraftComponent message, boolean allowLogging) {
        Minecraft.getInstance().submit(() -> super.sendSuccess(message, allowLogging));
    }
}
