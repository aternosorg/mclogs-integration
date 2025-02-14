package gs.mclo;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.commands.BungeeBrigadierCommand;
import gs.mclo.commands.BungeeBuildContext;
import gs.mclo.components.AdventureComponentFactory;
import gs.mclo.platform.Services;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;

public class MclogsBungeePlugin extends Plugin {
    static {
        // Bukkit uses a different class loader for plugins than the default thread context class loader.
        Services.setClassLoader(MclogsBungeePlugin.class.getClassLoader());
    }

    protected MclogsCommon mclogsCommon = new MclogsCommon();
    protected BungeeAudiences adventure;
    protected CommandDispatcher<CommandSender> dispatcher;

    @Override
    public void onEnable() {
        mclogsCommon.init();
        adventure = BungeeAudiences.create(this);
        registerCommands();
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private BungeeAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Adventure platform is not initialized");
        }

        return adventure;
    }

    public Audience audience(CommandSender sender) {
        //noinspection resource
        return adventure().sender(sender);
    }

    protected void registerCommands() {
        dispatcher = new CommandDispatcher<>();
        var context = new BungeeBuildContext(this);
        var componentFactory = new AdventureComponentFactory();

        mclogsCommon.registerCommands(dispatcher, context, componentFactory);

        var command = new BungeeBrigadierCommand(dispatcher, context, componentFactory);
        this.getProxy().getPluginManager().registerCommand(this, command);
    }
}
