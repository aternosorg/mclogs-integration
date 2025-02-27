package gs.mclo.commands;

import com.velocitypowered.api.command.CommandSource;
import gs.mclo.command.AdventureCommandSourceAccessor;
import net.kyori.adventure.audience.Audience;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class VelocityCommandSourceAccessor extends AdventureCommandSourceAccessor {
    /**
     * The original velocity command source
     */
    protected final CommandSource source;

    /**
     * Create a new velocity command source accessor.
     *
     * @param source The original velocity command source.
     */
    public VelocityCommandSourceAccessor(CommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return source.hasPermission(permission.node());
    }

    @Override
    public Path getRootDirectory() {
        return Path.of(".");
    }

    @Override
    public Collection<LogDirectory> getLogDirectories() {
        return List.of(
                new LogDirectory(getRootDirectory().resolve("logs"), LogType.LOG)
        );
    }

    @Override
    protected Audience getAudience() {
        return source;
    }
}
