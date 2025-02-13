package gs.mclo.platform.services;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;

public interface IPlatformHelper {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Get the version of the Minecraft server.
     *
     * @return The version of the server.
     */
    String getMinecraftVersion();

    /**
     * Get the version of the mclo.gs mod.
     *
     * @return The version of the mod.
     */
    String getModVersion();

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the configuration file for the plugin/mod.
     *
     * @return The configuration file.
     */
    GenericBuilder<Config, FileConfig> getConfig();
}
