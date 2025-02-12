package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.Constants;
import gs.mclo.MclogsCommon;
import gs.mclo.api.Log;
import gs.mclo.components.ClickEventAction;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Command<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    protected final MclogsCommon mclogs;
    protected final IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory;

    public Command(
            MclogsCommon mclogs,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        this.mclogs = mclogs;
        this.componentFactory = componentFactory;
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
            BuildContext<T, ComponentType> buildContext,
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
    public abstract <T> int execute(CommandContext<T> context, BuildContext<T, ComponentType> buildContext);

    public Path getLogsDirectory(ICommandSourceAccessor<?> source) {
        return source.getDirectory().resolve("logs");
    }

    public Path getCrashReportsDirectory(ICommandSourceAccessor<?> source) {
        return source.getDirectory().resolve("crash-reports");
    }

    public <T> int share(CommandContext<T> context, BuildContext<T, ComponentType> buildContext, String filename) {
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
        } catch (FileNotFoundException | IllegalArgumentException e) {
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
                var link = componentFactory.literal(response.getUrl()).setStyle(openUrlStyle(response.getUrl()));
                var message = componentFactory.literal("Your log has been uploaded: ").append(link);
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

    protected ComponentType fileNotFoundMessage(String filename, CommandContext<?> context) {
        var command = command(context, "list");

        return componentFactory.literal("There's no log or crash report with the name '" + filename + "'.")
                .append("\n")
                .append("Use ")
                .append(componentFactory.literal(command).setStyle(runCommandStyle(command)))
                .append(" to list all logs.");
    }

    protected ComponentType genericErrorMessage() {
        return componentFactory.literal("An error occurred. Check your log for more details");
    }

    protected StyleType runCommandStyle(String command) {
        return clickableStyle(componentFactory.clickEvent(ClickEventAction.RUN_COMMAND, command));
    }

    protected StyleType openUrlStyle(String url) {
        return clickableStyle(componentFactory.clickEvent(ClickEventAction.OPEN_URL, url));
    }

    protected StyleType clickableStyle(ClickEventType event) {
        return componentFactory.style()
                .clickEvent(event)
                .underlined();
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
