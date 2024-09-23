package gs.mclo.commands;

public enum CommandEnvironment {
    CLIENT,
    DEDICATED_SERVER,
    ;

    public String getCommandName() {
        return switch (this) {
            case CLIENT -> "mclogsc";
            case DEDICATED_SERVER -> "mclogs";
        };
    }
}
