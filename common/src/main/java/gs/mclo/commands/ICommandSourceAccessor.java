package gs.mclo.commands;

import gs.mclo.components.IComponent;

import java.nio.file.Path;

public interface ICommandSourceAccessor<Component extends IComponent<Component, ?, ?>> {
    boolean hasPermission(Permission permission);

    String getMinecraftVersion();

    Path getDirectory();

    void sendFailure(Component message);

    void sendSuccess(Component message, boolean allowLogging);
}
