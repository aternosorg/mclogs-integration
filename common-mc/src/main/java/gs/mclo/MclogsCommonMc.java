package gs.mclo;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.commands.*;
import net.minecraft.commands.CommandSourceStack;

import java.util.Collection;
import java.util.List;

public class MclogsCommonMc extends MclogsCommon {
    protected final Collection<Command> commands = List.of(
            new MclogsCommand(this),
            new MclogsListCommand(this),
            new MclogsShareCommand(this)
    );

    protected <T> void registerCommands(
            CommandDispatcher<T> dispatcher,
            BuildContext<T> context
    ) {
        var builder = context.literal(context.environment.getCommandName());

        for (var command : commands) {
            dispatcher.register(command.build(context, builder));
        }
    }

    protected void registerCommands(
            CommandEnvironment environment,
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        Constants.LOG.info("Registering command {}", environment.getCommandName());
        registerCommands(dispatcher, new CommandSourceStackBuildContext(environment));
    }
}
