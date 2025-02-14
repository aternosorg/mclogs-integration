package gs.mclo.commands;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.Constants;
import gs.mclo.MclogsBukkitPlugin;
import gs.mclo.components.AdventureComponent;
import gs.mclo.components.AdventureStyle;
import gs.mclo.components.IComponentFactory;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BukkitBrigadierCommand implements CommandExecutor, TabCompleter {
    private final MclogsBukkitPlugin plugin;
    private final BrigadierExecutor<CommandSender, AdventureComponent, AdventureStyle, ClickEvent> executor;
    private final CommandDispatcher<CommandSender> dispatcher;

    public BukkitBrigadierCommand(
            MclogsBukkitPlugin plugin,
            CommandDispatcher<CommandSender> dispatcher,
            BuildContext<CommandSender, AdventureComponent> buildContext,
            IComponentFactory<AdventureComponent, AdventureStyle, ClickEvent> componentFactory
    ) {
        this.plugin = plugin;
        this.dispatcher = dispatcher;
        this.executor = new BrigadierExecutor<>(dispatcher, buildContext, componentFactory);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        executor.executeCommand(sender, label, args);
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        return executor.completeCommand(sender, label, args);
    }

    public void register() {
        for (var commandNode : dispatcher.getRoot().getChildren()) {
            var command = plugin.getCommand(commandNode.getName());

            if (command == null) {
                Constants.LOG.warn("Command {} is missing from plugin.yml, skipping...", commandNode.getName());
                continue;
            }

            command.setExecutor(this);
            command.setTabCompleter(this);
        }
    }
}
