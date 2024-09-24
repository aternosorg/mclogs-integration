package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.Constants;
import gs.mclo.MclogsCommon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class MclogsListCommand extends Command {
    public MclogsListCommand(MclogsCommon mclogs) {
        super(mclogs);
    }

    @Override
    public <T> LiteralArgumentBuilder<T> build(
            BuildContext<T> buildContext,
            LiteralArgumentBuilder<T> builder
    ) {
        var list = buildContext.literal("list");

        if (buildContext.environment == CommandEnvironment.DEDICATED_SERVER) {
            list = list.requires(source -> buildContext.mapSource(source).hasPermission(2));
        }

        list.executes(context -> execute(context, buildContext));

        return builder.then(list);
    }

    @Override
    public <T> int execute(CommandContext<T> context, BuildContext<T> buildContext) {
        var source = buildContext.mapSource(context.getSource());
        try {
            var directory = buildContext.mapSource(context.getSource()).getDirectory();
            int total = 0;
            var message = Component.empty();

            var logs = mclogs.client.listLogsInDirectory(directory);
            total += list(message, logs, "Logs", context, buildContext);
            var reports = mclogs.client.listCrashReportsInDirectory(directory);
            total += list(message, reports, "Crash Reports", context, buildContext);

            if (total == 0) {
                message = Component.literal("No logs or crash reports found.");
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
            MutableComponent message,
            String[] items,
            String title,
            CommandContext<?> context,
            BuildContext<?> buildContext
    ) {
        if (items.length > 0) {
            message.append(title(title));
            for (String log : items) {
                message.append(item(log, context, buildContext));
            }
        }
        return items.length;
    }

    protected Component title(String title) {
        return Component.literal(title).setStyle(Style.EMPTY.applyFormat(ChatFormatting.UNDERLINE));
    }

    protected Component item(String filename, CommandContext<?> context, BuildContext<?> buildContext) {
        var command = command(context, "share", filename);
        Constants.LOG.info("Command: {}", command);

        var component = Component.literal("\n" + filename);

        if (buildContext.supportsClickEvents()) {
            var clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, command);

            component.setStyle(Style.EMPTY.withClickEvent(clickEvent));
        }

        return component;
    }
}
