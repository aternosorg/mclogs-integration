package gs.mclo.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import gs.mclo.Constants;
import gs.mclo.MclogsCommon;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

import java.util.concurrent.CompletableFuture;

public class MclogsDeleteCommand<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > extends Command<ComponentType, StyleType, ClickEventType> {
    private static final String ARGUMENT_NAME = "id";

    public MclogsDeleteCommand(
            MclogsCommon common,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        super(common, componentFactory);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(BuildContext<T, ComponentType> buildContext, LiteralArgumentBuilder<T> builder) {
        var delete = LiteralArgumentBuilder.<T>literal("delete");

        if (buildContext.environment.hasPermissions) {
            delete = delete.requires(source -> buildContext.mapSource(source).hasPermission(Permission.DELETE));
        }

        var argument = RequiredArgumentBuilder.<T, String>argument(ARGUMENT_NAME, StringArgumentType.word())
                .suggests((x, y) -> this.suggest(y))
                .executes(context -> execute(context, buildContext));

        return builder.then(delete.then(argument));
    }

    private <T> CompletableFuture<Suggestions> suggest(
            SuggestionsBuilder builder
    ) {
        var input = builder.getRemaining();

        for (String id : common.getSharedLogs().keySet()) {
            if (id.startsWith(input)) {
                builder.suggest(id);
            }
        }

        return builder.buildFuture();
    }

    protected <T> int execute(CommandContext<T> context, BuildContext<T, ComponentType> buildContext) {
        var source = buildContext.mapSource(context.getSource());
        var id = context.getArgument(ARGUMENT_NAME, String.class);
        var logs = common.getSharedLogs();

        if (!logs.containsKey(id)) {
            source.sendFailure(componentFactory.literal("No log with ID " + id + " found."));
            return -1;
        }

        logs.get(id).delete().thenAccept(v -> {
            logs.remove(id);
            source.sendSuccess(componentFactory.literal("Log with ID " + id + " has been deleted."), true);
        }).exceptionally(t -> {
            Constants.LOG.error("An error occurred when uploading your log", t);
            source.sendFailure(genericErrorMessage());
            return null;
        });
        return 1;
    }
}
