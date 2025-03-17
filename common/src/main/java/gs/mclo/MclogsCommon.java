package gs.mclo;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.serde.ObjectDeserializer;
import com.electronwill.nightconfig.core.serde.ObjectSerializer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import gs.mclo.api.Instance;
import gs.mclo.api.MclogsClient;
import gs.mclo.commands.*;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;
import gs.mclo.platform.Services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MclogsCommon {
    protected MclogsClient apiClient;
    protected FileConfig configFile;
    protected Configuration config = new Configuration();

    public void init() {
        apiClient = new MclogsClient(
                "mclogs-integration/" + Services.platform().getPlatformName(),
                Services.platform().getModVersion()
        );
        apiClient.setMinecraftVersion(Services.platform().getMinecraftVersion());
        configFile = Services.platform().getConfig()
                .autoreload()
                .onAutoReload(this::onConfigLoaded)
                .autosave()
                .build();
        configFile.load();
        migrateOldConfigFields();
        onConfigLoaded(false);

        ObjectSerializer.standard().serializeFields(config, configFile);
        configFile.save();
    }

    protected void migrateOldConfigFields() {
        for (Map.Entry<String, String> entry : Map.of(
                "view-logs-url", "viewLogsUrl",
                "api-base-url", "apiBaseUrl"
        ).entrySet()) {
            String oldKey = entry.getKey();
            String newKey = entry.getValue();

            if (configFile.get(newKey) == null) {
                Object value = configFile.get(oldKey);
                if (value != null) {
                    configFile.set(newKey, configFile.get(oldKey));
                    configFile.remove(oldKey);
                }
            }
        }
    }

    protected void onConfigLoaded() {
        onConfigLoaded(true);
    }

    protected void onConfigLoaded(boolean log) {
        ObjectDeserializer.standard().deserializeFields(configFile, config);
        var instance = new Instance(config.apiBaseUrl, config.viewLogsUrl);
        apiClient.setInstance(instance);

        if (log) {
            Constants.LOG.info("Reloaded configuration.");
        }
    }

    protected <
            ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
            StyleType extends IStyle<StyleType, ClickEventType>,
            ClickEventType
            > Collection<Command<ComponentType, StyleType, ClickEventType>> getCommands(
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        return List.of(
                new MclogsCommand<>(this, componentFactory),
                new MclogsListCommand<>(this, componentFactory),
                new MclogsShareCommand<>(this, componentFactory)
        );
    }

    protected <
            T,
            ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
            StyleType extends IStyle<StyleType, ClickEventType>,
            ClickEventType
            > void registerCommands(
            CommandDispatcher<T> dispatcher,
            BuildContext<T, ComponentType> context,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        Constants.LOG.info("Registering command {}", context.environment.commandName);
        var builder = LiteralArgumentBuilder.<T>literal(context.environment.commandName);

        for (var command : getCommands(componentFactory)) {
            dispatcher.register(command.build(context, builder));
        }
    }

    public MclogsClient getApiClient() {
        return apiClient;
    }
}
