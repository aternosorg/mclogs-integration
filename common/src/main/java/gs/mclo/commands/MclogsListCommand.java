package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.Constants;
import gs.mclo.api.MclogsClient;
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

    @Override
    public <T> int execute(CommandContext<T> context, BuildContext<T, ComponentType> buildContext) {
        var source = buildContext.mapSource(context.getSource());
        try {
            var directory = buildContext.mapSource(context.getSource()).getDirectory();
            int total = 0;
            var message = componentFactory.empty();

            var logs = apiClient.listLogsInDirectory(directory);
            total += list(message, logs, "Available Log Files:", context, buildContext, false);
            var reports = apiClient.listCrashReportsInDirectory(directory);
            total += list(message, reports, "Available Crash Reports:", context, buildContext, true);

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

    protected int list(
            ComponentType message,
            String[] items,
            String title,
            CommandContext<?> context,
            BuildContext<?, ComponentType> buildContext,
            boolean addLeadingNewLine
    ) {
        if (items.length > 0) {
            if (addLeadingNewLine) {
                message.append("\n");
            }

            message.append(title(title));
            for (String log : items) {
                message.append(item(log, context, buildContext));
            }
        }
        return items.length;
    }

    protected ComponentType title(String title) {
        return componentFactory.literal(title).setStyle(componentFactory.style().underlined());
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
            component.setStyle(componentFactory.style().clickEvent(clickEvent));
        }

        return component;
    }
}
