package gs.mclo;

import gs.mclo.command.FabricClientCommandBuildContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class FabricClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((
                dispatcher,
                dedicated
        ) -> MclogsFabric.INSTANCE.registerCommandsOnDedicatedServer(
                dispatcher, new FabricClientCommandBuildContext()
        ));

        ClientLifecycleEvents.CLIENT_STOPPING.register(_ -> MclogsFabric.INSTANCE.shutdown());
    }
}
