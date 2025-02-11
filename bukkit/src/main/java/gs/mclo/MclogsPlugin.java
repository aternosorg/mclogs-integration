package gs.mclo;

import org.bukkit.plugin.java.JavaPlugin;

public class MclogsPlugin extends JavaPlugin {
    protected MclogsCommon mclogsCommon = new MclogsCommon();

    @Override
    public void onEnable() {
        mclogsCommon.init();
    }
}
