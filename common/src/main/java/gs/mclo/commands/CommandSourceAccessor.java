package gs.mclo.commands;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.nio.file.Path;

public interface CommandSourceAccessor {
    boolean hasPermission(int level);

    String getMinecraftVersion();

    Path getDirectory();

    void sendFailure(Component message);

    void sendSuccess(Component message, boolean allowLogging);
}
