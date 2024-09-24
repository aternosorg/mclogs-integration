package gs.mclo.commands;

import net.minecraft.commands.CommandSourceStack;

public class ClientCommandSourceStackBuildContext extends CommandSourceStackBuildContext {
    public ClientCommandSourceStackBuildContext() {
        super(CommandEnvironment.CLIENT);
    }

    @Override
    public CommandSourceAccessor mapSource(CommandSourceStack source) {
        return new ClientCommandSourceStackAccessor(source);
    }

    @Override
    public boolean supportsClickEvents() {
        return false;
    }
}
