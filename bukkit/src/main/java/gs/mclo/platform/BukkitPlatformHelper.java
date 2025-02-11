package gs.mclo.platform;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import gs.mclo.MclogsPlugin;
import gs.mclo.platform.services.IPlatformHelper;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Bukkit";
    }

    @Override
    public String getModVersion() {
        return JavaPlugin.getPlugin(MclogsPlugin.class).getDescription().getVersion();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return false;
    }

    @Override
    public GenericBuilder<Config, FileConfig> getConfig() {
        return FileConfig.builder("config.yml");
    }
}
