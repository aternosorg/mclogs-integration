package gs.mclo;

import net.fabricmc.api.ModInitializer;

public class FabricInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        MclogsFabric.INSTANCE.init();
    }
}
