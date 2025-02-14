package gs.mclo.commands;

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
}
