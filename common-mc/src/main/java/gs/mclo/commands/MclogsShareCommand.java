package gs.mclo.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import gs.mclo.Constants;
import gs.mclo.MclogsCommonMc;

import java.util.concurrent.CompletableFuture;

public class MclogsShareCommand extends Command {
    private static final String ARGUMENT_NAME = "filename";

    public MclogsShareCommand(MclogsCommonMc mclogs) {
        super(mclogs);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(
            BuildContext<T> buildContext,
            LiteralArgumentBuilder<T> builder
    ) {
        var share = buildContext.literal("share");

        if (buildContext.environment == CommandEnvironment.DEDICATED_SERVER) {
            share = share.requires(source -> buildContext.mapSource(source).hasPermission(2));
        }

        var argument = buildContext.argument(ARGUMENT_NAME, StringArgumentType.greedyString())
                .suggests((x, y) -> this.suggest(x, y, buildContext))
                .executes(context -> execute(context, buildContext));

        return builder.then(share.then(argument));
    }

    private <T> CompletableFuture<Suggestions> suggest(CommandContext<T> x, SuggestionsBuilder builder, BuildContext<T> buildContext) {
        var source = buildContext.mapSource(x.getSource());
        var input = builder.getRemaining();

        try {
            for (String log : mclogs.client.listLogsInDirectory(source.getDirectory())) {
                if (log.startsWith(input)) {
                    builder.suggest(log);
                }
            }

            for (String report : mclogs.client.listCrashReportsInDirectory(source.getDirectory())) {
                if (report.startsWith(input)) {
                    builder.suggest(report);
                }
            }
            return builder.buildFuture();
        } catch (Exception e) {
            Constants.LOG.error("An error occurred when listing your logs.", e);
            return Suggestions.empty();
        }
    }

    @Override
    public <T> int execute(CommandContext<T> context, BuildContext<T> buildContext) {
        return share(context, buildContext, context.getArgument(ARGUMENT_NAME, String.class));
    }
}
