package de.opbande.mvpunishments.commands;

import de.opbande.mvpunishments.MVpunishments;
import de.opbande.mvpunishments.punishment.PunishmentReason;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunshipCommand implements CommandExecutor {
    private final MVpunishments plugin;

    public PunshipCommand(MVpunishments plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c/punship <Spielername>");
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("§c§lSpieler nicht gefunden!");
            return true;
        }

        String playerIP = target.getAddress().getAddress().getHostAddress();
        
        // IP-Ban hinzufügen und Serververbot setzen
        plugin.getDatabase().addPunishment(target.getUniqueId(), target.getName(), playerIP, PunishmentReason.SERVERVERBOT);
        plugin.getDatabase().addIPBan(playerIP);

        target.kickPlayer("§c§lOPBANDE\n" +
                "§7Du wurdest permanent vom Netzwerk ausgeschlossen.\n" +
                "§7Grund: §6IP-Ban\n\n" +
                "§7Website: §bOpbande.lovable.app\n" +
                "§7Unban: §bs.opbande@gmx.de");

        sender.sendMessage("§6§l" + target.getName() + " §aIP-Ban erfolgreich!");
        Bukkit.broadcastMessage("§6§l" + target.getName() + " §cwurde IP-gebannt!");

        return true;
    }
}