package gs.mclo.platform.services;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import gs.mclo.Constants;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlatformHelper implements IPlatformHelper {
    protected final ProxyServer proxyServer = ProxyServer.getInstance();
    protected final Plugin plugin = proxyServer.getPluginManager().getPlugin(Constants.MOD_ID);

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public String getMinecraftVersion() {
        return null;
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
