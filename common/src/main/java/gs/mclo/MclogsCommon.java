package gs.mclo;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import gs.mclo.api.MclogsClient;
import gs.mclo.commands.*;
import gs.mclo.platform.Services;
import net.minecraft.commands.CommandSourceStack;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class MclogsCommon {
    public final MclogsClient client = new MclogsClient("mclogs-mc/" + Services.PLATFORM.getPlatformName(), Services.PLATFORM.getModVersion());
    protected final Collection<Command> commands = List.of(
            new MclogsCommand(this),
            new MclogsListCommand(this),
            new MclogsShareCommand(this)
    );

    public void init() {
    }

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