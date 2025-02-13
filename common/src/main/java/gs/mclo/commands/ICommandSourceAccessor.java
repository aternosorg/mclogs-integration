package gs.mclo.commands;

import gs.mclo.components.IComponent;

import java.nio.file.Path;

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
     * Gets the directory of the server/client.
     * This should contain both the {@code logs} and {@code crash-report} directories.
     * @return The directory of the server/client.
     */
    Path getDirectory();

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
}
