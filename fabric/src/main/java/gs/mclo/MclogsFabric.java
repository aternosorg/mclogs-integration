package gs.mclo;

import gs.mclo.command.FabricClientCommandBuildContext;
import gs.mclo.commands.CommandEnvironment;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class MclogsFabric extends MclogsCommon implements ModInitializer, DedicatedServerModInitializer, ClientModInitializer {
    
    @Override
    public void onInitialize() {
        this.init();
    }

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            registerCommands(dispatcher, new FabricClientCommandBuildContext());
        });
    }

    @Override
    public void onInitializeServer() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            registerCommands(CommandEnvironment.DEDICATED_SERVER, dispatcher);
        }));
    }
}
