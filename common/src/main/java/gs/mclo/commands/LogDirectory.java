package gs.mclo.commands;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * A directory that might contain log files.
 */
public class LogDirectory {
    /**
     * The path to the directory.
     */
    private final Path path;

    /**
     * The type of logs that are stored in this directory.
     */
    private final LogType type;

    /**
     * Creates a new log directory.
     * @param path The path to the directory.
     * @param type The type of logs that are stored in this directory.
     */
    public LogDirectory(Path path, LogType type) {
        this.path = path;
        this.type = type;
    }

    /**
     * Get the path to the directory.
     * @return The path to the directory.
     */
    public Path path() {
        return path;
    }

    /**
     * Return the title for listing files in this directory.
     * @return The title for listing files in this directory.
     */
    public String title() {
        return type.title + "s:";
    }

    /**
     * Get the log directories of a server/client that uses the vanilla log directory structure.
     * @param root The root directory of the server/client.
     * @return The log directories of the server/client.
     */
    public static Collection<LogDirectory> getVanillaLogDirectories(Path root) {
        return List.of(
                new LogDirectory(root.resolve("logs"), LogType.LOG),
                new LogDirectory(root.resolve("crash-reports"), LogType.CRASH_REPORT),
                new LogDirectory(root.resolve("debug"), LogType.NETWORK_PROTOCOL_ERROR_REPORT)
        );
    }
}
