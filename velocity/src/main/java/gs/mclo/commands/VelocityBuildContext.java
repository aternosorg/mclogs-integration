package gs.mclo.commands;

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
}
