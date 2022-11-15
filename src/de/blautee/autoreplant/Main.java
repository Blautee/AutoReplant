package de.blautee.autoreplant;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main plugin;

	public void onEnable() {
		plugin = this;
		
		saveDefaultConfig();
		
		checkLangUpdate();

		Settings.reloadConfig();
		Settings.reloadLang();

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new HarvestListener(), plugin);

		getCommand("autoreplant").setExecutor(new AutoReplantCommand());

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
