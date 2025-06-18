package gs.mclo;

import gs.mclo.commands.ClientCommandSourceStackBuildContext;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class MclogsForge extends MclogsCommonMc {

    public MclogsForge() {
        this.init();
        RegisterClientCommandsEvent.BUS.addListener(this::registerClientCommands);
        RegisterCommandsEvent.BUS.addListener(this::registerCommands);
    }

    public void registerClientCommands(RegisterClientCommandsEvent event) {
        registerCommandsOnDedicatedServer(event.getDispatcher(), new ClientCommandSourceStackBuildContext());
    }

    public void registerCommands(RegisterCommandsEvent event) {
        registerCommandsOnDedicatedServer(event.getDispatcher());
    }
}
