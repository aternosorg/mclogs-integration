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
    public boolean supportsClientCommandClickEvents() {
        // TODO: Test if this is still up-to-date
        // Forge 1.21(51.0.33): Still broken
        // NeoForge 21.0.37-beta: Still broken
        // NeoForge 21.0.167: Still broken
        return true;
    }
}
