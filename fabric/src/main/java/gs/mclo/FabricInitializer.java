package gs.mclo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FabricInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        MclogsFabric.INSTANCE.init();

        ServerLifecycleEvents.SERVER_STOPPED.register(event -> {
            if (event.isDedicatedServer()) {
                // For singleplayer this shutdown happens in the CLIENT_STOPPING event. See FabricClientInitializer.
                MclogsFabric.INSTANCE.shutdown();
            }
        });
    }
}
