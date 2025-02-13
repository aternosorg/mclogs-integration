package gs.mclo.platform;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import gs.mclo.Constants;
import gs.mclo.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public String getMinecraftVersion() {
        return getModVersion("minecraft");
    }

    @Override
    public String getModVersion() {
        return getModVersion(Constants.MOD_ID);
    }

    protected String getModVersion(String modId) {
        return FabricLoader
                .getInstance()
                .getModContainer(modId)
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("Unknown");
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public GenericBuilder<Config, FileConfig> getConfig() {
        var configDir = FabricLoader.getInstance().getConfigDir();
        var configFile = configDir.resolve(Constants.MOD_ID + ".toml");

        return FileConfig.builder(configFile);
    }
}
