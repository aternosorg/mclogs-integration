package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.Constants;
import gs.mclo.MclogsCommonMc;
import gs.mclo.api.Log;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Command {
    protected final MclogsCommonMc mclogs;

    public Command(MclogsCommonMc mclogs) {
        this.mclogs = mclogs;
    }

    /**
     * Build a brigadier command
     *
     * @param buildContext Contains the environment and various methods to build the command.
     * @param builder      the builder to add the command to
     * @param <T>          the command source type
     * @return the built command
     */
    public abstract <T> LiteralArgumentBuilder<T> build(
            BuildContext<T> buildContext,
            LiteralArgumentBuilder<T> builder
    );

    /**
     * Execute the command
     *
     * @param context      the command context
     * @param buildContext Contains the environment and various methods to execute the command.
     * @param <T>          the command source type
     * @return the result of the command
     */
    public abstract <T> int execute(CommandContext<T> context, BuildContext<T> buildContext);

    public Path getLogsDirectory(CommandSourceAccessor source) {
        return source.getDirectory().resolve("logs");
    }

    public Path getCrashReportsDirectory(CommandSourceAccessor source) {
        return source.getDirectory().resolve("crash-reports");
    }

    public <T> int share(CommandContext<T> context, BuildContext<T> buildContext, String filename) {
        var source = buildContext.mapSource(context.getSource());

        mclogs.client.setMinecraftVersion(source.getMinecraftVersion());

        var logs = getLogsDirectory(source);
        var crashReports = getCrashReportsDirectory(source);

        var path = logs.resolve(filename);
        if (!path.toFile().exists()) {
            path = crashReports.resolve(filename);
        }

        if (!path.toFile().exists() || !isFileInAllowedDirectory(path, logs, crashReports)) {
            source.sendFailure(fileNotFoundMessage(filename, context));
            return -1;
        }

        Log log;
        try {
            log = new Log(path);
        } catch (FileNotFoundException|IllegalArgumentException e) {
            source.sendFailure(fileNotFoundMessage(filename, context));
            return -1;
        } catch (IOException e) {
            Constants.LOG.error("Failed to read log", e);
            source.sendFailure(genericErrorMessage());
            return -1;
        }

        Constants.LOG.info("Sharing {}", source.getDirectory().relativize(path));

        mclogs.client.uploadLog(log).thenAccept(response -> {
            if (response.isSuccess()) {
                var link = Component.literal(response.getUrl()).setStyle(openUrlStyle(response.getUrl()));
                var message = Component.literal("Your log has been uploaded: ").append(link);
                source.sendSuccess(message, true);
            } else {
                Constants.LOG.error("An error occurred when uploading your log: {}", response.getError());
                source.sendFailure(genericErrorMessage());
            }
        }).exceptionally(e -> {
            Constants.LOG.error("An error occurred when uploading your log", e);
            source.sendFailure(genericErrorMessage());
            return null;
        });
        return 1;
    }

    protected String command(CommandContext<?> context, String... args) {
        StringBuilder command = new StringBuilder("/" + context.getNodes().getFirst().getNode().getName());

        for (var arg : args) {
            command.append(" ").append(arg);
        }

        return command.toString();
    }

    protected Component fileNotFoundMessage(String filename, CommandContext<?> context) {
        var command = command(context, "list");

        return Component.literal("There's no log or crash report with the name '" + filename + "'.")
                .append("\n")
                .append("Use ")
                .append(Component.literal(command).setStyle(runCommandStyle(command)))
                .append(" to list all logs.");
    }

    protected Component genericErrorMessage() {
        return Component.literal("An error occurred. Check your log for more details");
    }

    protected Style runCommandStyle(String command) {
        return clickableStyle(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
    }

    protected Style openUrlStyle(String url) {
        return clickableStyle(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
    }

    protected Style clickableStyle(ClickEvent event) {
        return Style.EMPTY
                .withClickEvent(event)
                .applyFormat(ChatFormatting.UNDERLINE);
    }

    private boolean isFileInAllowedDirectory(Path file, Path... directories) {
        try {
            for (var directory : directories) {
                if (file.toRealPath().startsWith(directory.toRealPath())) {
                    return true;
                }
            }
        } catch (IOException ignored) {
        }
        return false;
    }
}
