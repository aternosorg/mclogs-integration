package gs.mclo;

import gs.mclo.commands.ClientCommandSourceStackBuildContext;
import gs.mclo.commands.CommandEnvironment;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class MclogsForge extends MclogsCommon {

    public MclogsForge() {
        this.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerClientCommands(RegisterClientCommandsEvent event) {
        registerCommands(event.getDispatcher(), new ClientCommandSourceStackBuildContext());
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        registerCommands(CommandEnvironment.DEDICATED_SERVER, event.getDispatcher());
    }
}