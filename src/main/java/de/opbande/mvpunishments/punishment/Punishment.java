package de.opbande.mvpunishments.punishment;

import java.io.Serializable;
import java.util.UUID;

public class Punishment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID playerUUID;
    private String playerName;
    private String playerIP;
    private PunishmentReason reason;
    private long startTime;
    private long endTime;
    private boolean active;

    public Punishment(UUID playerUUID, String playerName, String playerIP, PunishmentReason reason) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.playerIP = playerIP;
        this.reason = reason;
        this.startTime = System.currentTimeMillis();
        
        if (reason.isPermanent()) {
            this.endTime = -1;
            this.active = true;
        } else {
            this.endTime = startTime + (reason.getDurationMinutes() * 60 * 1000);
            this.active = true;
        }
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerIP() {
        return playerIP;
    }

    public PunishmentReason getReason() {
        return reason;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        if (!active) return false;
        if (reason.isPermanent()) return true;
        return System.currentTimeMillis() < endTime;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getRemainingMillis() {
        if (reason.isPermanent()) return -1;
        return endTime - System.currentTimeMillis();
    }

    public int getRemainingDays() {
        if (reason.isPermanent()) return -1;
        long millis = getRemainingMillis();
        if (millis < 0) return 0;
        return (int) (millis / (1000 * 60 * 60 * 24)) + 1;
    }
}