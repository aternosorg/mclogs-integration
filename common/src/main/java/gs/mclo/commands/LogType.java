package gs.mclo.commands;

public enum LogType {
    LOG("Log"),
    CRASH_REPORT("Crash Report"),
    NETWORK_PROTOCOL_ERROR_REPORT("Network Protocol Error Report"),
    ;
    public final String title;

    LogType(String title) {
        this.title = title;
    }
}
