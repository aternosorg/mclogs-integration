package gs.mclo.command;

import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.components.AdventureComponent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.concurrent.CompletableFuture;

/**
 * Adventure implementation of {@link ICommandSourceAccessor}.
 * Provides methods to send messages to the command source.
 */
public abstract class AdventureCommandSourceAccessor implements ICommandSourceAccessor<AdventureComponent> {
    @Override
    public final CompletableFuture<Void> sendFailure(AdventureComponent message) {
        getAudience().sendMessage(message.getBoxed().color(NamedTextColor.RED));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public final CompletableFuture<Void> sendSuccess(AdventureComponent message, boolean allowLogging) {
        getAudience().sendMessage(message.getBoxed());
        return CompletableFuture.completedFuture(null);
    }

    /**
     * Get an adventure audience for the command source.
     * @return An adventure audience for the command source.
     */
    protected abstract Audience getAudience();
}
