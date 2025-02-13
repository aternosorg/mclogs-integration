package gs.mclo.commands;

public enum Permission {
    BASE(2, "mclogs"),
    SHARE_SPECIFIC(2, "mclogs.share"),
    LIST(2, "mclogs.list"),
    ;

    private final int level;
    private final String node;

    Permission(int level, String node) {
        this.level = level;
        this.node = node;
    }

    public int level() {
        return level;
    }

    /**
     * Returns the permission node for this permission.
     *
     * @return permission node
     */
    public String node() {
        return node;
    }
}
