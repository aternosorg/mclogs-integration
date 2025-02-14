package gs.mclo.commands;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.components.AdventureComponent;
import gs.mclo.components.AdventureStyle;
import gs.mclo.components.IComponentFactory;
import net.kyori.adventure.text.event.ClickEvent;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeBrigadierCommand extends Command implements TabExecutor {

    private final BrigadierExecutor<CommandSender, AdventureComponent, AdventureStyle, ClickEvent> executor;

    public BungeeBrigadierCommand(
            CommandDispatcher<CommandSender> dispatcher,
            BuildContext<CommandSender, AdventureComponent> buildContext,
            IComponentFactory<AdventureComponent, AdventureStyle, ClickEvent> componentFactory
    ) {
        super(CommandEnvironment.PROXY.commandName, Permission.BASE.node(), "mclogsb");
        this.executor = new BrigadierExecutor<>(dispatcher, buildContext, componentFactory);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        executor.executeCommand(sender, CommandEnvironment.PROXY.commandName, args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return executor.completeCommand(sender, CommandEnvironment.PROXY.commandName, args);
    }
}
