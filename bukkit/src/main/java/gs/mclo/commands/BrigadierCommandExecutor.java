package gs.mclo.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BrigadierCommandExecutor implements CommandExecutor {
    private final CommandDispatcher<CommandSender> dispatcher;
    private final CommandNode<CommandSender> commandNode;

    public BrigadierCommandExecutor(CommandDispatcher<CommandSender> dispatcher,
                                    CommandNode<CommandSender> commandNode) {
        this.dispatcher = dispatcher;
        this.commandNode = commandNode;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String input = command.getName();
        if (args.length > 0) {
            input += " " + String.join(" ", args);
        }
        try {
            // TODO: better error if permissions are missing
            var parse = dispatcher.parse(input, sender);
            return dispatcher.execute(parse) >= 0;
        } catch (CommandSyntaxException e) {
            return false;
        }
    }
}
