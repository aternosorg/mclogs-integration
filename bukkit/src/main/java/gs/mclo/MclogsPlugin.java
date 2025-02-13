package gs.mclo;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.commands.BrigadierCommandExecutor;
import gs.mclo.components.AdventureComponentFactory;
import gs.mclo.commands.BukkitBuildContext;
import gs.mclo.platform.Services;
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

    protected void registerCommands() {
        dispatcher = new CommandDispatcher<>();
        var context = new BukkitBuildContext(this, adventure);
        var componentFactory = new AdventureComponentFactory();

        mclogsCommon.registerCommands(dispatcher, context, componentFactory);

        for (var commandNode : dispatcher.getRoot().getChildren()) {
            var command = getCommand(commandNode.getName());

            if (command == null) {
                Constants.LOG.warn("Command {} is missing from plugin.yml, skipping...", commandNode.getName());
                continue;
            }

            command.setExecutor(new BrigadierCommandExecutor(dispatcher, commandNode));
        }
    }
}
