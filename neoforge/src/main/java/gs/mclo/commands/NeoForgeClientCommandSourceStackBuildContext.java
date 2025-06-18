package gs.mclo.commands;

import gs.mclo.Constants;
import net.neoforged.fml.loading.FMLLoader;

import java.util.regex.Pattern;

public class NeoForgeClientCommandSourceStackBuildContext extends ClientCommandSourceStackBuildContext {

    /**
     * Constructs a new build context for client commands on NeoForge.
     */
    public NeoForgeClientCommandSourceStackBuildContext() {
    }

    @Override
    public boolean supportsClientCommandClickEvents() {
        return true;
    }
}
