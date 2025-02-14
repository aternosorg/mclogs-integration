package gs.mclo.commands;

public enum CommandEnvironment {
    CLIENT(false, "mclogsc"),
    DEDICATED_SERVER(true, "mclogs"),
    PROXY(true, "mclogsp")
    ;

    public final boolean hasPermissions;
    public final String commandName;

    CommandEnvironment(boolean hasPermissions, String commandName) {
        this.hasPermissions = hasPermissions;
        this.commandName = commandName;
    }
}
