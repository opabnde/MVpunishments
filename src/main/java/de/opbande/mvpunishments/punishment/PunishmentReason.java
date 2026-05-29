package de.opbande.mvpunishments.punishment;

public enum PunishmentReason {
    AUSZEIT("Auszeit", 15, false),
    BAUWERK("Bauwerk", 2 * 24 * 60, false),
    CHEATING("Cheating", 7 * 24 * 60, false),
    SCAMMING("Scamming", 30 * 24 * 60, false),
    RECHTSBRUCH("Rechtsbruch", -1, true),
    MULTIACCOUNT("Multiaccount", -1, true),
    USERNAME_SKIN("Username_Skin", -1, true),
    SERVERMANIPULATION("Servermanipulation", -1, true),
    ECHTGELDHANDEL("Echtgeldhandel", -1, true),
    HAUSVERBOT("Hausverbot", -1, true),
    SERVERVERBOT("Serververbot", -1, true);

    private final String displayName;
    private final int durationMinutes; // -1 = permanent
    private final boolean permanent;

    PunishmentReason(String displayName, int durationMinutes, boolean permanent) {
        this.displayName = displayName;
        this.durationMinutes = durationMinutes;
        this.permanent = permanent;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public int getDurationDays() {
        if (permanent) return -1;
        return durationMinutes / (24 * 60);
    }

    public static PunishmentReason getByName(String name) {
        try {
            return PunishmentReason.valueOf(name.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}