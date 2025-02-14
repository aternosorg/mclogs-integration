package gs.mclo.command;

import gs.mclo.commands.BuildContext;
import gs.mclo.commands.CommandEnvironment;
import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.components.MinecraftComponent;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class FabricClientCommandBuildContext extends BuildContext<FabricClientCommandSource, MinecraftComponent> {
    public FabricClientCommandBuildContext() {
        super(CommandEnvironment.CLIENT);
    }

    @Override
    public ICommandSourceAccessor<MinecraftComponent> mapSource(FabricClientCommandSource source) {
        return new FabricClientCommandSourceAccessor(source);
    }
}
