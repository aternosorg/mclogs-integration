package gs.mclo.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gs.mclo.Constants;
import gs.mclo.MclogsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrigadierCommandExecutor implements CommandExecutor, TabCompleter {
    private static final Style.Builder ERROR_STYLE = Style.style().color(NamedTextColor.RED);
    private final MclogsPlugin plugin;
    private final CommandDispatcher<CommandSender> dispatcher;

    public BrigadierCommandExecutor(@NotNull MclogsPlugin plugin,
                                    @NotNull CommandDispatcher<CommandSender> dispatcher) {
        this.plugin = plugin;
        this.dispatcher = dispatcher;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        try {
            // TODO: better error if permissions are missing
            var parse = parse(command, args, sender);
            return dispatcher.execute(parse) >= 0;
        } catch (CommandSyntaxException e) {
            var audience = plugin.audience(sender);

            var context = Component.text(exceptionContext(e))
                    .style(ERROR_STYLE)
                    .decorate(TextDecoration.UNDERLINED);
            var error = Component
                    .text(e.getType().toString())
                    .style(ERROR_STYLE)
                    .appendNewline()
                    .append(context)
                    .append(Component
                            .text("<--[HERE]")
                            .style(ERROR_STYLE).decorate(TextDecoration.ITALIC)
                    );

            audience.sendMessage(error);
            return true;
        }
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        var parse = parse(command, args, sender);
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

    protected ParseResults<CommandSender> parse(@NotNull Command command,
                                                @NotNull String[] args,
                                                @NotNull CommandSender sender) {
        String input = command.getName();
        if (args.length > 0) {
            input += " " + String.join(" ", args);
        }

        return dispatcher.parse(input, sender);
    }

    public void register() {
        for (var commandNode : dispatcher.getRoot().getChildren()) {
            var command = plugin.getCommand(commandNode.getName());

            if (command == null) {
                Constants.LOG.warn("Command {} is missing from plugin.yml, skipping...", commandNode.getName());
                continue;
            }

            command.setExecutor(this);
            command.setTabCompleter(this);
        }
    }
}
