package gs.mclo;

import net.fabricmc.api.ModInitializer;

public class Mclogs implements ModInitializer {
    
    @Override
    public void onInitialize() {
        // TODO: Remove
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        MclogsCommon.init();
    }
}
