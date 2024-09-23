package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.MclogsCommon;

public class MclogsCommand extends Command {
    public MclogsCommand(MclogsCommon mclogs) {
        super(mclogs);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(
            BuildContext<T> buildContext,
            LiteralArgumentBuilder<T> builder
    ) {
        if (buildContext.environment == CommandEnvironment.DEDICATED_SERVER) {
            builder = builder.requires(source -> buildContext.mapSource(source).hasPermission(2));
        }

        return builder.executes(context -> execute(context, buildContext));
    }

    @Override
    public <T> int execute(CommandContext<T> context, BuildContext<T> buildContext) {
        return share(context, buildContext, "latest.log");
    }
}
