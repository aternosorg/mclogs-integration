package gs.mclo.platform.services;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import gs.mclo.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Optional;

public class VelocityPlatformHelper implements IPlatformHelper {
    /**
     * The velocity proxy
     * {@code null} if the plugin has not been constructed yet
     */
    @Nullable
    protected static ProxyServer velocity;

    /**
     * The data directory of the plugin
     * {@code null} if the plugin has not been constructed yet
     */
    @Nullable
    protected static Path dataDirectory;

    /**
     * Initialize the helper with some information about the platform
     * @param proxy the velocity proxy
     * @param dataDirectory the data directory of the plugin
     */
    public static void init(@NotNull ProxyServer proxy, @NotNull Path dataDirectory) {
        velocity = proxy;
        VelocityPlatformHelper.dataDirectory = dataDirectory;
    }

    @Override
    public String getPlatformName() {
        return "Velocity";
    }

    @Override
    public String getMinecraftVersion() {
        return null;
    }

    @Override
    public String getModVersion() {
        return getPluginContainer()
                .flatMap(container -> container.getDescription().getVersion())
                .orElse("Unknown");
    }

    @Override
    public GenericBuilder<Config, FileConfig> getConfig() {
        if (dataDirectory == null) {
            throw new IllegalStateException("Tried to load config before data directory was set");
        }

        //noinspection ResultOfMethodCallIgnored
        dataDirectory.toFile().mkdirs();
        return FileConfig.builder(dataDirectory.resolve("config.toml"));
    }

    protected Optional<PluginContainer> getPluginContainer() {
        if (velocity == null) {
            return Optional.empty();
        }

        return velocity.getPluginManager().getPlugin(Constants.MOD_ID);
    }
}
