package gs.mclo.bukkit;

import gs.mclo.Constants;
import org.bukkit.plugin.java.JavaPlugin;

public class MclogsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Constants.LOG.info("Using SLF4J");
        getLogger().info("Using bukkit logger");
    }
}
