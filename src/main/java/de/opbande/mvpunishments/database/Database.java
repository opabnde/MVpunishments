package de.opbande.mvpunishments.database;

import de.opbande.mvpunishments.MVpunishments;
import de.opbande.mvpunishments.punishment.Punishment;
import de.opbande.mvpunishments.punishment.PunishmentReason;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.*;

public class Database {
    private final MVpunishments plugin;
    private final File dataFolder;
    private final File punishmentsFile;
    private final Map<UUID, List<Punishment>> punishments;

    public Database(MVpunishments plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder();
        this.punishmentsFile = new File(dataFolder, "punishments.dat");
        this.punishments = new HashMap<>();
    }

    public void initialize() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        loadPunishments();
        startCheckTask();
    }

    public void addPunishment(UUID playerUUID, String playerName, String playerIP, PunishmentReason reason) {
        Punishment punishment = new Punishment(playerUUID, playerName, playerIP, reason);
        punishments.computeIfAbsent(playerUUID, k -> new ArrayList<>()).add(punishment);
        savePunishments();
    }

    public void addIPBan(String playerIP) {
        // IP-Ban speichern
        File ipBanFile = new File(dataFolder, "ip-bans.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(ipBanFile, true))) {
            writer.println(playerIP + ":" + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isIPBanned(String playerIP) {
        File ipBanFile = new File(dataFolder, "ip-bans.txt");
        if (!ipBanFile.exists()) return false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ipBanFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(playerIP + ":")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Punishment> getPunishments(UUID playerUUID) {
        return punishments.getOrDefault(playerUUID, new ArrayList<>());
    }

    public boolean isBanned(UUID playerUUID) {
        List<Punishment> playerPunishments = getPunishments(playerUUID);
        for (Punishment p : playerPunishments) {
            if (p.isActive()) {
                return true;
            }
        }
        return false;
    }

    public Punishment getActiveBan(UUID playerUUID) {
        List<Punishment> playerPunishments = getPunishments(playerUUID);
        for (Punishment p : playerPunishments) {
            if (p.isActive()) {
                return p;
            }
        }
        return null;
    }

    private void startCheckTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (List<Punishment> pList : punishments.values()) {
                for (Punishment p : pList) {
                    if (!p.isPermanent() && !p.isActive() && p.getRemainingMillis() < 0) {
                        p.setActive(false);
                    }
                }
            }
            savePunishments();
        }, 20L, 20L * 60); // Jede Minute prüfen
    }

    private void savePunishments() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(punishmentsFile))) {
            oos.writeObject(punishments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPunishments() {
        if (!punishmentsFile.exists()) return;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(punishmentsFile))) {
            Map<UUID, List<Punishment>> loaded = (Map<UUID, List<Punishment>>) ois.readObject();
            punishments.putAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}