package gs.mclo;

import com.google.inject.Inject;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import gs.mclo.commands.CommandEnvironment;
import gs.mclo.commands.VelocityBuildContext;
import gs.mclo.components.AdventureComponentFactory;
import gs.mclo.platform.Services;
import gs.mclo.platform.services.VelocityPlatformHelper;

import java.nio.file.Path;

public class MclogsVelocityPlugin extends MclogsCommon {
    static {
        Services.setClassLoader(MclogsVelocityPlugin.class.getClassLoader());
    }

    private final ProxyServer proxy;

    @Inject
    public MclogsVelocityPlugin(ProxyServer proxy, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        VelocityPlatformHelper.init(proxy, dataDirectory);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        init();

        registerCommands();
    }

    protected void registerCommands() {
        Constants.LOG.info("Registering commands");
        var commandManager = proxy.getCommandManager();

        var componentFactory = new AdventureComponentFactory();
        var commandMeta = commandManager.metaBuilder(CommandEnvironment.PROXY.commandName)
                .aliases("mclogsv")
                .plugin(this)
                .build();

        var context = new VelocityBuildContext();
        var builder = context.literal(context.environment.commandName);

        for (var command : getCommands(componentFactory)) {
            commandManager.register(commandMeta, new BrigadierCommand(command.build(context, builder)));
        }
    }
}
