package de.opbande.mvpunishments;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import de.opbande.mvpunishments.commands.PunishCommand;
import de.opbande.mvpunishments.commands.PunshipCommand;
import de.opbande.mvpunishments.commands.LookingCommand;
import de.opbande.mvpunishments.listeners.ChatListener;
import de.opbande.mvpunishments.database.Database;

public class MVpunishments extends JavaPlugin {

    private static MVpunishments instance;
    private Database database;

    @Override
    public void onEnable() {
        instance = this;
        
        // Konfiguration erstellen
        saveDefaultConfig();
        
        // Datenbank initialisieren
        database = new Database(this);
        database.initialize();
        
        // Commands registrieren
        getCommand("punish").setExecutor(new PunishCommand(this));
        getCommand("punship").setExecutor(new PunshipCommand(this));
        getCommand("looking").setExecutor(new LookingCommand(this));
        
        // Listener registrieren
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        
        getLogger().info("§6MVpunishments §aaktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("§6MVpunishments §cdeaktiviert!");
    }

    public static MVpunishments getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }
}