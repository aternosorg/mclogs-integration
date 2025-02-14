package gs.mclo.commands;

import gs.mclo.Constants;
import net.neoforged.fml.loading.FMLLoader;

import java.util.regex.Pattern;

public class NeoForgeClientCommandSourceStackBuildContext extends ClientCommandSourceStackBuildContext {
    /**
     * A regex pattern for parsing NeoForge version strings.
     */
    private static final Pattern NEOFORGE_VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(?:-beta)?");
    /**
     * Are click events with client commands supported in this version?
     */
    private boolean supportsClickEvents = false;

    /**
     * Constructs a new build context for client commands on NeoForge.
     */
    public NeoForgeClientCommandSourceStackBuildContext() {
        try {
            if (isMinVersion(new int[]{21, 4, 5})) {
                supportsClickEvents = true;
            }
        } catch (Exception e) {
            Constants.LOG.error("Failed to parse NeoForge version", e);
        }
    }

    @Override
    public boolean supportsClientCommandClickEvents() {
        return supportsClickEvents;
    }

    /**
     * Is a minimum version of NeoForge installed?
     * @param minVersions The minimum version to check for. (Minecraft minor version, Minecraft patch version, NeoForge patch version)
     * @return True if the minimum version is installed, false otherwise.
     */
    protected boolean isMinVersion(int[] minVersions) {
        var version = FMLLoader.versionInfo().neoForgeVersion();
        var matcher = NEOFORGE_VERSION_PATTERN.matcher(version);

        if (!matcher.matches()) {
            throw new IllegalStateException("Failed to parse NeoForge version: " + version);
        }

        if (matcher.groupCount() != minVersions.length) {
            throw new IllegalStateException("Expected match group count : " + version);
        }

        for (int i = 0; i < minVersions.length; i++) {
            var minVersion = minVersions[i];
            var foundVersion = Integer.parseInt(matcher.group(i + 1));
            if (foundVersion > minVersion) {
                return true;
            } else if (foundVersion < minVersion) {
                return false;
            }
        }
        return true;
    }
}
