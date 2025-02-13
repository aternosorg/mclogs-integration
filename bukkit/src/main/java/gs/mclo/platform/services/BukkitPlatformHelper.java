package gs.mclo.platform.services;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import gs.mclo.MclogsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public class BukkitPlatformHelper implements IPlatformHelper {
    private static final Pattern BUKKIT_VERSION_PATTERN = Pattern.compile("(.*)-R\\d+\\.\\d+.*");
    protected final JavaPlugin plugin = JavaPlugin.getPlugin(MclogsPlugin.class);

    @Override
    public String getPlatformName() {
        return "Bukkit";
    }

    @Override
    public String getMinecraftVersion() {
        // e.g. 1.21.4-R0.1-SNAPSHOT
        var bukkitVersion = Bukkit.getBukkitVersion();

        var matcher = BUKKIT_VERSION_PATTERN.matcher(bukkitVersion);
        if (matcher.matches()) {
            return matcher.group(1);
        }

        return bukkitVersion;
    }

    @Override
    public String getModVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public GenericBuilder<Config, FileConfig> getConfig() {
        var dataFolder = plugin.getDataFolder();
        //noinspection ResultOfMethodCallIgnored
        dataFolder.mkdirs();
        return FileConfig.builder(dataFolder.toPath().resolve("config.toml"));
    }
}
