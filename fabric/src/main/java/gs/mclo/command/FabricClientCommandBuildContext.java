package gs.mclo.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import gs.mclo.commands.BuildContext;
import gs.mclo.commands.CommandEnvironment;
import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.components.MinecraftComponent;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class FabricClientCommandBuildContext extends BuildContext<FabricClientCommandSource, MinecraftComponent> {
    public FabricClientCommandBuildContext() {
        super(CommandEnvironment.CLIENT);
    }

    @Override
    public ICommandSourceAccessor<MinecraftComponent> mapSource(FabricClientCommandSource source) {
        return new FabricClientCommandSourceAccessor(source);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> literal(String name) {
        return ClientCommandManager.literal(name);
    }

    @Override
    public <V> RequiredArgumentBuilder<FabricClientCommandSource, V> argument(String name, ArgumentType<V> type) {
        return ClientCommandManager.argument(name, type);
    }
}
