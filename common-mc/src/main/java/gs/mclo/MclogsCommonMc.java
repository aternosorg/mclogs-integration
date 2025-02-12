package gs.mclo;

import com.mojang.brigadier.CommandDispatcher;
import gs.mclo.commands.*;
import gs.mclo.components.MinecraftComponent;
import gs.mclo.components.MinecraftComponentFactory;
import net.minecraft.commands.CommandSourceStack;

public class MclogsCommonMc extends MclogsCommon {
    protected <T> void registerCommandsOnDedicatedServer(
            CommandDispatcher<T> dispatcher,
            BuildContext<T, MinecraftComponent> context
    ) {
        registerCommands(dispatcher, context, new MinecraftComponentFactory());
    }

    protected void registerCommandsOnDedicatedServer(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        var context = new CommandSourceStackBuildContext(CommandEnvironment.DEDICATED_SERVER);
        registerCommandsOnDedicatedServer(dispatcher, context);
    }
}
