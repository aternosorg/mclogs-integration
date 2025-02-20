package gs.mclo.commands;

import gs.mclo.components.IComponent;

import java.nio.file.Path;
import java.util.Collection;

/**
 * An interface for platform-agnostic command source access.
 * @param <Component> The type of components used for messages.
 */
public interface ICommandSourceAccessor<Component extends IComponent<Component, ?, ?>> {
    /**
     * Checks if the source has the given permission.
     * @param permission The permission to check.
     * @return {@code true} if the source has the permission, {@code false} otherwise.
     */
    boolean hasPermission(Permission permission);

    /**
     * Get the directory of the Minecraft server/client.
     * @return the directory of the Minecraft server/client
     */
    Path getRootDirectory();

    /**
     * Get a collection of all log directories. These directories do not need to exist.
     * @return a collection of all directories that may contain logs.
     */
    Collection<LogDirectory> getLogDirectories();

    /**
     * Sends an error message to the source.
     * Error messages are typically red.
     * @param message The message to send.
     */
    void sendFailure(Component message);

    /**
     * Sends a success message to the source.
     * The color of these messages should not be modified.
     * @param message The message to send.
     */
    void sendSuccess(Component message, boolean allowLogging);

    /**
     * Get the file name of the current log file
     * @return the file name of the current log file
     */
    default String getCurrentLogFileName() {
        return "latest.log";
    }
}
