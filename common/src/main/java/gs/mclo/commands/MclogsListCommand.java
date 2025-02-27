package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.Constants;
import gs.mclo.api.MclogsClient;
import gs.mclo.api.Util;
import gs.mclo.components.ClickEventAction;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

public class MclogsListCommand<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > extends Command<ComponentType, StyleType, ClickEventType> {
    public MclogsListCommand(
            MclogsClient apiClient,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        super(apiClient, componentFactory);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(
            BuildContext<T, ComponentType> buildContext,
            LiteralArgumentBuilder<T> builder
    ) {
        var list = LiteralArgumentBuilder.<T>literal("list");

        if (buildContext.environment.hasPermissions) {
            list = list.requires(source -> buildContext.mapSource(source).hasPermission(Permission.LIST));
        }

        list.executes(context -> execute(context, buildContext));

        return builder.then(list);
    }

    protected <T> int execute(CommandContext<T> context, BuildContext<T, ComponentType> buildContext) {
        var source = buildContext.mapSource(context.getSource());
        try {
            int total = 0;
            var message = componentFactory.empty();

            boolean first = true;
            for (var logDir : source.getLogDirectories()) {
                var files = Util.listFilesInDirectory(logDir.path());

                if (files.length > 0) {
                    if (!first) {
                        message.append("\n");
                    }

                    message.append(title(logDir.title()));
                    for (String log : files) {
                        message.append(item(log, context, buildContext));
                    }
                }

                total += files.length;
                first = false;
            }

            if (total == 0) {
                message = componentFactory.literal("No logs or crash reports found.");
            }

            source.sendSuccess(message, false);
            return total;
        } catch (Exception e) {
            Constants.LOG.error("An error occurred when listing your logs.", e);
            source.sendFailure(genericErrorMessage());
            return -1;
        }
    }

    protected ComponentType title(String title) {
        return componentFactory.literal(title).style(componentFactory.style().underlined());
    }

    protected ComponentType item(
            String filename,
            CommandContext<?> context,
            BuildContext<?, ComponentType> buildContext
    ) {
        var command = command(context, "share", filename);
        Constants.LOG.info("Command: {}", command);

        var component = componentFactory.literal("\n" + filename);

        if (buildContext.supportsClientCommandClickEvents()) {
            var clickEvent = componentFactory.clickEvent(ClickEventAction.RUN_COMMAND, command);
            component.style(componentFactory.style().clickEvent(clickEvent));
        }

        return component;
    }
}
