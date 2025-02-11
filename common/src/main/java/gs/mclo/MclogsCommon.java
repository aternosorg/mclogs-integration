package gs.mclo;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.serde.ObjectDeserializer;
import com.electronwill.nightconfig.core.serde.ObjectSerializer;
import gs.mclo.api.Instance;
import gs.mclo.api.MclogsClient;
import gs.mclo.platform.Services;

public class MclogsCommon {
    public final MclogsClient client = new MclogsClient("mclogs-mc/" + Services.PLATFORM.getPlatformName(), Services.PLATFORM.getModVersion());
    protected FileConfig configFile;
    protected Configuration config = new Configuration();

    public void init() {
        configFile = Services.PLATFORM.getConfig()
                .autoreload()
                .onAutoReload(this::onConfigLoaded)
                .autosave()
                .build();
        configFile.load();
        migrateOldConfigFields();
        onConfigLoaded();

        ObjectSerializer.standard().serializeFields(config, configFile);
        configFile.save();
    }

    protected void migrateOldConfigFields() {
        if (configFile.get("viewLogsUrl") == null) {
            configFile.set("viewLogsUrl", configFile.get("view-logs-url"));
        }

        if (configFile.get("apiBaseUrl") == null) {
            configFile.set("apiBaseUrl", configFile.get("api-base-url"));
        }
    }

    protected void onConfigLoaded() {
        ObjectDeserializer.standard().deserializeFields(configFile, config);
        var instance = new Instance(config.apiBaseUrl, config.viewLogsUrl);
        client.setInstance(instance);
    }
}
