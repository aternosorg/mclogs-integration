package gs.mclo;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricServerInitializer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        CommandRegistrationCallback.EVENT.register(((
                dispatcher,
                registryAccess,
                environment
        ) -> MclogsFabric.INSTANCE.registerCommandsOnDedicatedServer(dispatcher)));
    }
}
