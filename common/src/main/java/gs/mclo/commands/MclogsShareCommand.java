package gs.mclo.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import gs.mclo.Constants;
import gs.mclo.MclogsCommon;
import gs.mclo.api.Util;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

import java.util.concurrent.CompletableFuture;

public class MclogsShareCommand<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > extends Command<ComponentType, StyleType, ClickEventType> {
    private static final String ARGUMENT_NAME = "filename";

    public MclogsShareCommand(
            MclogsCommon common,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        super(common, componentFactory);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(
            BuildContext<T, ComponentType> buildContext,
            LiteralArgumentBuilder<T> builder
    ) {
        var share = LiteralArgumentBuilder.<T>literal("share");

        if (buildContext.environment.hasPermissions) {
            share = share.requires(source -> buildContext.mapSource(source).hasPermission(Permission.SHARE_SPECIFIC));
        }

        var argument = RequiredArgumentBuilder.<T, String>argument(ARGUMENT_NAME, StringArgumentType.greedyString())
                .suggests((x, y) -> this.suggest(x, y, buildContext))
                .executes(context -> execute(context, buildContext));

        return builder.then(share.then(argument));
    }

    private <T> CompletableFuture<Suggestions> suggest(
            CommandContext<T> x,
            SuggestionsBuilder builder,
            BuildContext<T, ComponentType> buildContext
    ) {
        var source = buildContext.mapSource(x.getSource());
        var input = builder.getRemaining();

        try {
            for (LogDirectory dir : source.getLogDirectories()) {
                for (String file : Util.listFilesInDirectory(dir.path())) {
                    if (file.startsWith(input)) {
                        builder.suggest(file);
                    }
                }
            }

            return builder.buildFuture();
        } catch (Exception e) {
            Constants.LOG.error("An error occurred when listing your logs.", e);
            return Suggestions.empty();
        }
    }

    protected <T> int execute(CommandContext<T> context, BuildContext<T, ComponentType> buildContext) {
        return share(context, buildContext, context.getArgument(ARGUMENT_NAME, String.class));
    }
}
