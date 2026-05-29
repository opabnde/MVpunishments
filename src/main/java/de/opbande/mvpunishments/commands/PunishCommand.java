package de.opbande.mvpunishments.commands;

import de.opbande.mvpunishments.MVpunishments;
import de.opbande.mvpunishments.punishment.PunishmentReason;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandExecutor {
    private final MVpunishments plugin;

    public PunishCommand(MVpunishments plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c/punish <Spielername> <Grund>");
            return true;
        }

        String targetName = args[0];
        String reasonString = args[1];

        PunishmentReason reason = PunishmentReason.getByName(reasonString);
        if (reason == null) {
            sender.sendMessage("§c§lUngültiger Grund! Verfügbare Gründe:");
            for (PunishmentReason r : PunishmentReason.values()) {
                sender.sendMessage("§c- " + r.getDisplayName());
            }
            return true;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            sender.sendMessage("§c§lSpieler nicht gefunden!");
            return true;
        }

        String playerIP = target.getAddress().getAddress().getHostAddress();
        plugin.getDatabase().addPunishment(target.getUniqueId(), target.getName(), playerIP, reason);

        target.kickPlayer(getBanMessage(reason, target.getName()));
        sender.sendMessage("§6§l" + target.getName() + " §aerfolgreich gebannt!");
        Bukkit.broadcastMessage("§6§l" + target.getName() + " §cwurde gebannt. Grund: §6" + reason.getDisplayName());

        return true;
    }

    private String getBanMessage(PunishmentReason reason, String playerName) {
        if (reason.isPermanent()) {
            return "§c§lOPBANDE\n" +
                    "§7Du wurdest permanent vom Netzwerk ausgeschlossen.\n" +
                    "§7Grund: §6" + reason.getDisplayName() + "\n\n" +
                    "§7Website: §bOpbande.lovable.app\n" +
                    "§7Unban: §bs.opbande@gmx.de";
        } else {
            int days = reason.getDurationDays();
            return "§c§lOPBANDE\n" +
                    "§7Du wurdest für §6" + days + " Tag(e) §7vom Netzwerk ausgeschlossen.\n" +
                    "§7Grund: §6" + reason.getDisplayName() + "\n\n" +
                    "§7Website: §bOpbande.lovable.app\n" +
                    "§7Unban: §bs.opbande@gmx.de";
        }
    }
}