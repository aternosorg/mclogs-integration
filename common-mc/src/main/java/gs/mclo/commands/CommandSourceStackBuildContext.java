package gs.mclo.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import gs.mclo.components.MinecraftComponent;
import net.minecraft.commands.CommandSourceStack;

public class CommandSourceStackBuildContext extends BuildContext<CommandSourceStack, MinecraftComponent> {
    public CommandSourceStackBuildContext(CommandEnvironment environment) {
        super(environment);
    }

    @Override
    public ICommandSourceAccessor<MinecraftComponent> mapSource(CommandSourceStack source) {
        return new CommandSourceStackAccessor(source);
    }

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    @Override
    public <V> RequiredArgumentBuilder<CommandSourceStack, V> argument(String name, ArgumentType<V> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    @Override
    public boolean supportsClickEvents() {
        return true;
    }
}
