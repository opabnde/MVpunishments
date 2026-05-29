package de.opbande.mvpunishments.listeners;

import de.opbande.mvpunishments.MVpunishments;
import de.opbande.mvpunishments.punishment.Punishment;
import de.opbande.mvpunishments.punishment.PunishmentReason;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Arrays;
import java.util.List;

public class ChatListener implements Listener {
    private final MVpunishments plugin;
    private static final List<String> RACIST_WORDS = Arrays.asList(
            // Rassistische und diskriminierende Begriffe
            "neger", "n-wort", "nigga", "nigger",
            "schwarzer", "dummes negerkind",
            "jude", "judensau", "judenschwein",
            "moslem", "muselmann", "paki",
            "zigeuner", "sinti",
            "schwuchtel", "flasche", "homo",
            "behinderter", "spasti", "downie",
            "krüppel", "mongo", "blödmann"
            // Füge weitere Wörter hinzu, die erkannt werden sollen
    );

    public ChatListener(MVpunishments plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        for (String word : RACIST_WORDS) {
            if (message.contains(word)) {
                event.setCancelled(true);
                
                // Automatischer Ban für Rechtsbruch
                String playerIP = player.getAddress().getAddress().getHostAddress();
                plugin.getDatabase().addPunishment(player.getUniqueId(), player.getName(), playerIP, PunishmentReason.RECHTSBRUCH);

                player.kickPlayer("§c§lOPBANDE\n" +
                        "§7Du wurdest permanent vom Netzwerk ausgeschlossen.\n" +
                        "§7Grund: §6Rechtsbruch (Rassistische Inhalte)\n\n" +
                        "§7Website: §bOpbande.lovable.app\n" +
                        "§7Unban: §bs.opbande@gmx.de");

                Bukkit.broadcastMessage("§6§l" + player.getName() + " §cwurde für rassistische Inhalte gebannt!");
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String playerIP = event.getRealAddress().getHostAddress();

        // IP-Ban prüfen
        if (plugin.getDatabase().isIPBanned(playerIP)) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§c§lOPBANDE\n" +
                    "§7Du wurdest permanent vom Netzwerk ausgeschlossen.\n" +
                    "§7Grund: §6IP-Ban\n\n" +
                    "§7Website: §bOpbande.lovable.app\n" +
                    "§7Unban: §bs.opbande@gmx.de");
            return;
        }

        // Normalen Ban prüfen
        if (plugin.getDatabase().isBanned(player.getUniqueId())) {
            Punishment ban = plugin.getDatabase().getActiveBan(player.getUniqueId());
            if (ban != null) {
                String message;
                if (ban.getReason().isPermanent()) {
                    message = "§c§lOPBANDE\n" +
                            "§7Du wurdest permanent vom Netzwerk ausgeschlossen.\n" +
                            "§7Grund: §6" + ban.getReason().getDisplayName() + "\n\n" +
                            "§7Website: §bOpbande.lovable.app\n" +
                            "§7Unban: §bs.opbande@gmx.de";
                } else {
                    message = "§c§lOPBANDE\n" +
                            "§7Du wurdest für §6" + ban.getRemainingDays() + " Tag(e) §7vom Netzwerk ausgeschlossen.\n" +
                            "§7Grund: §6" + ban.getReason().getDisplayName() + "\n\n" +
                            "§7Website: §bOpbande.lovable.app\n" +
                            "§7Unban: §bs.opbande@gmx.de";
                }
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
            }
        }
    }
}