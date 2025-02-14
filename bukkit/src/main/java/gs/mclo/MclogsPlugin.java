package gs.mclo;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.commands.BrigadierCommandExecutor;
import gs.mclo.components.AdventureComponentFactory;
import gs.mclo.commands.BukkitBuildContext;
import gs.mclo.platform.Services;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MclogsPlugin extends JavaPlugin {
    static {
        // Bukkit uses a different class loader for plugins than the default thread context class loader.
        Services.setClassLoader(MclogsPlugin.class.getClassLoader());
    }

    protected MclogsCommon mclogsCommon = new MclogsCommon();
    protected BukkitAudiences adventure;
    protected CommandDispatcher<CommandSender> dispatcher;

    @Override
    public void onEnable() {
        mclogsCommon.init();

        adventure = BukkitAudiences.create(this);

        registerCommands();
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private BukkitAudiences adventure() {
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
        var context = new BukkitBuildContext(this);
        var componentFactory = new AdventureComponentFactory();

        mclogsCommon.registerCommands(dispatcher, context, componentFactory);
        var executor = new BrigadierCommandExecutor(this, dispatcher);
        executor.register();
    }
}
