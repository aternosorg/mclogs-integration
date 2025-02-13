package gs.mclo;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.serde.ObjectDeserializer;
import com.electronwill.nightconfig.core.serde.ObjectSerializer;
import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.api.Instance;
import gs.mclo.api.MclogsClient;
import gs.mclo.commands.*;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;
import gs.mclo.platform.Services;

import java.util.Collection;
import java.util.List;

public class MclogsCommon {
    protected MclogsClient apiClient;
    protected FileConfig configFile;
    protected Configuration config = new Configuration();

    public void init() {
        apiClient = new MclogsClient("mclogs-mc/" + Services.platform().getPlatformName(), Services.platform().getModVersion());
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
        if (configFile.get("viewLogsUrl") == null) {
            configFile.set("viewLogsUrl", configFile.get("view-logs-url"));
        }

        if (configFile.get("apiBaseUrl") == null) {
            configFile.set("apiBaseUrl", configFile.get("api-base-url"));
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
                new MclogsCommand<>(apiClient, this, componentFactory),
                new MclogsListCommand<>(apiClient, this, componentFactory),
                new MclogsShareCommand<>(apiClient, this, componentFactory)
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
        Constants.LOG.info("Registering command {}", context.environment.getCommandName());
        var builder = context.literal(context.environment.getCommandName());

        for (var command : getCommands(componentFactory)) {
            dispatcher.register(command.build(context, builder));
        }
    }
}
