package gs.mclo;


import gs.mclo.commands.ClientCommandSourceStackBuildContext;
import gs.mclo.commands.CommandEnvironment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(Constants.MOD_ID)
public class MclogsNeoForge extends MclogsCommonMc {

    public MclogsNeoForge(IEventBus eventBus) {
        this.init();
        NeoForge.EVENT_BUS.register(this);
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
