package gs.mclo.platform;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import gs.mclo.MclogsPlugin;
import gs.mclo.platform.services.IPlatformHelper;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlatformHelper implements IPlatformHelper {
    protected final JavaPlugin plugin = JavaPlugin.getPlugin(MclogsPlugin.class);

    @Override
    public String getPlatformName() {
        return "Bukkit";
    }

    @Override
    public String getModVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return false;
    }

    @Override
    public GenericBuilder<Config, FileConfig> getConfig() {
        var dataFolder = plugin.getDataFolder();
        //noinspection ResultOfMethodCallIgnored
        dataFolder.mkdirs();
        return FileConfig.builder(dataFolder.toPath().resolve("config.toml"));
    }
}
