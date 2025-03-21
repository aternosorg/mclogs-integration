package gs.mclo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import gs.mclo.Constants;
import gs.mclo.MclogsCommon;
import gs.mclo.components.ClickEventAction;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A platform-agnostic command
 * @param <ComponentType> The type of components used on the platform
 * @param <StyleType> The type of styles used on the platform
 * @param <ClickEventType> The type of click events used on the platform
 */
public abstract class Command<
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    /**
     * The Mclogs API client
     */
    protected final MclogsCommon common;
    /**
     * A component factory
     */
    protected final IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory;

    /**
     * Create a new command
     * @param common Common mod/plugin instance
     * @param componentFactory A component factory
     */
    public Command(
            MclogsCommon common,
            IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory
    ) {
        this.common = common;
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
     * Share a log or crash report
     * @param context The command context
     * @param buildContext The build context
     * @param filename The filename of the log or crash report
     * @return -1 if the input was invalid or an error occurred, 1 if the log was shared
     * @param <T> The command source type
     */
    public <T> int share(CommandContext<T> context, BuildContext<T, ComponentType> buildContext, String filename) {
        var source = buildContext.mapSource(context.getSource());

        var logDirectories = source.getLogDirectories().stream().map(LogDirectory::path).toArray(Path[]::new);

        Path path = null;
        for (var logDir : logDirectories) {
            path = logDir.resolve(filename);
            if (path.toFile().exists()) {
                break;
            }
        }

        if (path == null || !path.toFile().exists() || !isFileInAllowedDirectory(path, logDirectories)) {
            source.sendFailure(fileNotFoundMessage(filename, context));
            return -1;
        }

        Constants.LOG.info("Sharing {}", source.getRootDirectory().relativize(path));

        common.getApiClient().uploadLog(path).thenAccept(response -> {
            if (response.isSuccess()) {
                var link = componentFactory.literal(response.getUrl()).style(openUrlStyle(response.getUrl()));
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

    /**
     * Build a command string the user would input.
     * Uses the same root command that was used when executing the command.
     * @param context The command context
     * @param args The arguments to append to the command
     * @return The command string
     */
    protected String command(CommandContext<?> context, String... args) {
        StringBuilder command = new StringBuilder("/" + context.getNodes().getFirst().getNode().getName());

        for (var arg : args) {
            command.append(" ").append(arg);
        }

        return command.toString();
    }

    /**
     * Create a message for when a file is not found
     * @param filename The filename that was not found
     * @param context The command context
     * @return A component with the error message
     */
    protected ComponentType fileNotFoundMessage(String filename, CommandContext<?> context) {
        var command = command(context, "list");

        return componentFactory.literal("There's no log, crash- or network protocol error report with the name '" + filename + "'.")
                .append("\n")
                .append("Use ")
                .append(componentFactory.literal(command).style(runCommandStyle(command)))
                .append(" to list all logs.");
    }

    /**
     * Create a generic error message
     * @return A component with a generic error message
     */
    protected ComponentType genericErrorMessage() {
        return componentFactory.literal("An error occurred. Check your log for more details");
    }

    /**
     * Create a clickable style for running a command
     * @param command The command to run
     * @return The style
     */
    protected StyleType runCommandStyle(String command) {
        return clickableStyle(componentFactory.clickEvent(ClickEventAction.RUN_COMMAND, command));
    }

    /**
     * Create a clickable style for opening a URL
     * @param url The URL to open
     * @return The style
     */
    protected StyleType openUrlStyle(String url) {
        return clickableStyle(componentFactory.clickEvent(ClickEventAction.OPEN_URL, url));
    }

    /**
     * Create style with a click event
     * @param event The click event
     * @return The style
     */
    protected StyleType clickableStyle(ClickEventType event) {
        return componentFactory.style()
                .clickEvent(event)
                .underlined();
    }

    private boolean isFileInAllowedDirectory(Path file, Path... directories) {
        try {
            for (var directory : directories) {
                if (directory.toRealPath().equals(file.getParent().toRealPath())) {
                    return true;
                }
            }
        } catch (IOException ignored) {
        }
        return false;
    }
}
