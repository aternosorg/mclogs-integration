package gs.mclo.platform;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.GenericBuilder;
import gs.mclo.Constants;
import gs.mclo.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public String getMinecraftVersion() {
        return FMLLoader.versionInfo().mcVersion();
    }

    @Override
    public String getModVersion() {
        return ModList.get().getModFileById(Constants.MOD_ID).versionString();
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public GenericBuilder<Config, FileConfig> getConfig() {
        var configFile = FMLLoader.getGamePath().resolve("config")
                .resolve(Constants.MOD_ID + ".toml");

        return FileConfig.builder(configFile);
    }
}
