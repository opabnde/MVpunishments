package de.opbande.mvpunishments.commands;

import de.opbande.mvpunishments.MVpunishments;
import de.opbande.mvpunishments.punishment.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LookingCommand implements CommandExecutor {
    private final MVpunishments plugin;

    public LookingCommand(MVpunishments plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c/looking <Spielername>");
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("§c§lSpieler nicht gefunden!");
            return true;
        }

        List<Punishment> punishments = plugin.getDatabase().getPunishments(target.getUniqueId());

        sender.sendMessage("§6§l════════════════════════════════════════════");
        sender.sendMessage("§6§lBestrafungen von §c" + target.getName());
        sender.sendMessage("§6§l════════════════════════════════════════════");

        if (punishments.isEmpty()) {
            sender.sendMessage("§a§lKeine Bestrafungen vorhanden!");
        } else {
            for (int i = 0; i < punishments.size(); i++) {
                Punishment p = punishments.get(i);
                String status = p.isActive() ? "§a✓ AKTIV" : "§c✗ ABGELAUFEN";
                
                if (p.getReason().isPermanent()) {
                    sender.sendMessage("§7[§6#" + (i + 1) + "§7] " + p.getReason().getDisplayName() + " (§4PERMANENT§7) - " + status);
                } else {
                    sender.sendMessage("§7[§6#" + (i + 1) + "§7] " + p.getReason().getDisplayName() + " (" + p.getRemainingDays() + "d) - " + status);
                }
            }
        }

        sender.sendMessage("§6§l════════════════════════════════════════════");
        return true;
    }
}