package gs.mclo;

import gs.mclo.commands.ClientCommandSourceStackBuildContext;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class MclogsForge extends MclogsCommonMc {

    public MclogsForge() {
        this.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerClientCommands(RegisterClientCommandsEvent event) {
        registerCommandsOnDedicatedServer(event.getDispatcher(), new ClientCommandSourceStackBuildContext());
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        registerCommandsOnDedicatedServer(event.getDispatcher());
    }
}
