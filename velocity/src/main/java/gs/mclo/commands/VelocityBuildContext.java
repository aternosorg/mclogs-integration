package gs.mclo.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import gs.mclo.components.AdventureComponent;

public class VelocityBuildContext extends BuildContext<CommandSource, AdventureComponent> {
    /**
     * Constructs a new velocity build context.
     */
    public VelocityBuildContext() {
        super(CommandEnvironment.PROXY);
    }

    @Override
    public VelocityCommandSourceAccessor mapSource(CommandSource source) {
        return new VelocityCommandSourceAccessor(source);
    }

    @Override
    public LiteralArgumentBuilder<CommandSource> literal(String name) {
        return BrigadierCommand.literalArgumentBuilder(name);
    }

    @Override
    public <V> RequiredArgumentBuilder<CommandSource, V> argument(String name, ArgumentType<V> type) {
        return BrigadierCommand.requiredArgumentBuilder(name, type);
    }

    @Override
    public boolean supportsClickEvents() {
        return true;
    }
}
