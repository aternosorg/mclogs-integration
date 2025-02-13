package gs.mclo.commands;

public enum Permission {
    SHARE_CURRENT(2, "mclogs"),
    SHARE_SPECIFIC(2, "mclogs", "share"),
    LIST(2, "mclogs", "list"),
    ;

    private final int level;
    private final String[] nodeParts;

    Permission(int level, String... nodeParts) {
        this.level = level;
        this.nodeParts = nodeParts;
    }

    public int level() {
        return level;
    }

    /**
     * Returns the permission node for this permission.
     * Join the parts with a dot to get the full permission node.
     *
     * @return an array of permission node parts.
     */
    public String[] nodeParts() {
        return nodeParts;
    }
}
