package gs.mclo.commands;

import gs.mclo.components.MinecraftComponent;
import net.minecraft.commands.CommandSourceStack;

public class ClientCommandSourceStackBuildContext extends CommandSourceStackBuildContext {
    public ClientCommandSourceStackBuildContext() {
        super(CommandEnvironment.CLIENT);
    }

    @Override
    public ICommandSourceAccessor<MinecraftComponent> mapSource(CommandSourceStack source) {
        return new ClientCommandSourceStackAccessor(source);
    }

    @Override
    public boolean supportsClickEvents() {
        return false;
    }
}
