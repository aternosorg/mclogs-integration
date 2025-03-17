package gs.mclo;

import gs.mclo.command.FabricClientCommandBuildContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class FabricClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((
                dispatcher,
                dedicated
        ) -> MclogsFabric.INSTANCE.registerCommandsOnDedicatedServer(
                dispatcher, new FabricClientCommandBuildContext()
        ));
    }
}
