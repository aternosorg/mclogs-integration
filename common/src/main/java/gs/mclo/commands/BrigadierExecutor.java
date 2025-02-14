package gs.mclo.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gs.mclo.Constants;
import gs.mclo.components.Color;
import gs.mclo.components.IComponent;
import gs.mclo.components.IComponentFactory;
import gs.mclo.components.IStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A Utility class to execute and tab complete brigadier commands on platforms that do not natively support brigadier
 * @param <T> The command source type
 * @param <ComponentType> The component type of the platform
 * @param <StyleType> The style type of the platform
 * @param <ClickEventType> The click event type of the platform
 */
public class BrigadierExecutor<
        T,
        ComponentType extends IComponent<ComponentType, StyleType, ClickEventType>,
        StyleType extends IStyle<StyleType, ClickEventType>,
        ClickEventType
        > {
    /**
     * The brigadier command dispatcher
     */
    protected final CommandDispatcher<T> dispatcher;

    /**
     * The build context for the command
     */
    protected final BuildContext<T, ComponentType> context;

    /**
     * The component factory for the platform
     */
    protected final IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory;

    /**
     * Create a new brigadier executor
     * @param dispatcher The brigadier command dispatcher
     * @param context The build context for the command
     * @param componentFactory The component factory for the platform
     */
    public BrigadierExecutor(CommandDispatcher<T> dispatcher,
                             BuildContext<T, ComponentType> context,
                             IComponentFactory<ComponentType, StyleType, ClickEventType> componentFactory) {
        this.dispatcher = dispatcher;
        this.context = context;
        this.componentFactory = componentFactory;
    }

    /**
     * Execute the command
     * @param source The command source
     * @param command The command name
     * @param args The command arguments
     */
    public void executeCommand(T source, String command, String[] args) {
        try {
            var parse = parse(command, args, source);
            dispatcher.execute(parse);
        } catch (CommandSyntaxException e) {
            var errorStyle = componentFactory.style().color(Color.RED);
            var contextMessage = componentFactory.literal(exceptionContext(e))
                    .style(errorStyle.copy().underlined());
            var error = componentFactory
                    .literal(e.getType().toString())
                    .style(errorStyle)
                    .append("\n")
                    .append(contextMessage)
                    .append(componentFactory
                            .literal("<--[HERE]")
                            .style(errorStyle.copy().italic())
                    );

            context.mapSource(source).sendFailure(error);
        }
    }

    /**
     * Get tab completions for the command
     * @param source The command source
     * @param command The command name
     * @param args The command arguments
     * @return A list of tab completions
     */
    public List<String> completeCommand(T source, String command, String[] args) {
        var parse = parse(command, args, source);
        try {
            var suggestions = dispatcher.getCompletionSuggestions(parse)
                    .get(1, TimeUnit.SECONDS);

            var result = new ArrayList<String>();

            for (var suggestion : suggestions.getList()) {
                result.add(suggestion.getText());
            }

            return result;
        } catch (Exception e) {
            Constants.LOG.error("Failed to get tab completions", e);
            return List.of();
        }
    }

    protected ParseResults<T> parse(String command, String[] args, T sender) {
        String input = command;
        if (args.length > 0) {
            input += " " + String.join(" ", args);
        }

        return dispatcher.parse(input, sender);
    }

    protected String exceptionContext(CommandSyntaxException exception) {
        final String input = exception.getInput();
        final StringBuilder builder = new StringBuilder();
        final int cursor = Math.min(input.length(), exception.getCursor());

        if (cursor > CommandSyntaxException.CONTEXT_AMOUNT) {
            builder.append("...");
        }

        builder.append(input, Math.max(0, cursor - CommandSyntaxException.CONTEXT_AMOUNT), cursor);

        return builder.toString();
    }
}
