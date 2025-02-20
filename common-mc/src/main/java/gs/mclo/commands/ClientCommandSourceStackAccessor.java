package gs.mclo.commands;

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
}
