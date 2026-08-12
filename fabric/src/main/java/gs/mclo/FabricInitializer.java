package gs.mclo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class FabricInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        MclogsFabric.INSTANCE.init();

        ClientLifecycleEvents.CLIENT_STOPPING.register(_ -> MclogsFabric.INSTANCE.shutdown());
    }
}
