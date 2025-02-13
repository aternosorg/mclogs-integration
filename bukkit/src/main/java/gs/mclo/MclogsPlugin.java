package gs.mclo;

import org.bukkit.plugin.java.JavaPlugin;

public class MclogsPlugin extends JavaPlugin {
    static {
        // Bukkit uses a different class loader for plugins than the default thread context class loader.
        Services.setClassLoader(MclogsPlugin.class.getClassLoader());
    }

    protected MclogsCommon mclogsCommon = new MclogsCommon();

    @Override
    public void onEnable() {
        mclogsCommon.init();
    }
}
