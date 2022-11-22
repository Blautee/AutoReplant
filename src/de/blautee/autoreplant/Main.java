package de.blautee.autoreplant;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main plugin;
	
	public boolean WG = false;
	public boolean WE = false;
	public static boolean useWorldGuard = false;

	public void onEnable() {
		plugin = this;

		saveDefaultConfig();

		checkLangUpdate();

		Settings.reloadConfig();
		Settings.reloadLang();

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new HarvestListener(), plugin);

		getCommand("autoreplant").setExecutor(new AutoReplantCommand());

		
		if (pm.getPlugin("WorldGuard") != null) {
			WG = true;
		}

		if (pm.getPlugin("WorldEdit") != null) {
			WE = true;
		}
		
		if (WG && WE) {
			useWorldGuard = true;
		}

	}

	public static Main getPlugin() {
		return plugin;
	}

	public void checkLangUpdate() {
		FileConfiguration cfg = plugin.getConfig();
		cfg.options().copyDefaults(true);
		saveConfig();
	}
}
