package command;

import gs.mclo.commands.ICommandSourceAccessor;
import gs.mclo.components.AdventureComponent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class AdventureCommandSourceAccessor implements ICommandSourceAccessor<AdventureComponent> {
    @Override
    public final void sendFailure(AdventureComponent message) {
        getAudience().sendMessage(message.getBoxed().color(NamedTextColor.RED));
    }

    @Override
    public final void sendSuccess(AdventureComponent message, boolean allowLogging) {
        getAudience().sendMessage(message.getBoxed());
    }

    /**
     * Get an adventure audience for the command source.
     * @return An adventure audience for the command source.
     */
    protected abstract Audience getAudience();
}
