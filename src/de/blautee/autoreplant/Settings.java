package de.blautee.autoreplant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

	public static String user_perm;
	public static String admin_perm;

	public static Map<UUID, Boolean> useAuto;
	public static List<Material> matList;

	public static String prefix;

	public static String reload_done;

	public static String activated;
	public static String deactivated;

	public static String not_ready;

	public static void reloadConfig() {
		FileConfiguration cfg = Main.getPlugin().getConfig();

		user_perm = cfg.getString("config.user_perm");
		admin_perm = cfg.getString("config.admin_perm");

		matList = new ArrayList<Material>();
		for (String s : cfg.getStringList("config.material_list")) {
			try {
				matList.add(Material.valueOf(s));
			} catch (Exception ex) {
				Bukkit.getLogger().log(Level.WARNING,
						"Error with Material " + s + " in your config.material_list! " + ex);
			}
		}
		
		useAuto = new HashMap<UUID, Boolean>();
		
		for (String s : cfg.getConfigurationSection("config.player_list").getKeys(false)) {
			boolean use = cfg.getBoolean("config.player_list." + s);
			try {
				UUID id = UUID.fromString(s);
				useAuto.put(id, use);
			} catch (Exception ex) {
				Bukkit.getLogger().log(Level.WARNING, "Error with UUID " + s + "in your config.player_list! " + ex);
			}
		}
	}

	public static void reloadLang() {
		prefix = colorMe("lang.prefix");
		reload_done = colorMe("lang.reload_done");
		activated = colorMe("lang.activated");
		deactivated = colorMe("lang.deactivated");
		not_ready = colorMe("lang.not_ready");
	}

	public static String colorMe(String s) {
		FileConfiguration cfg = Main.getPlugin().getConfig();
		String out;
		try {
			out = ChatColor.translateAlternateColorCodes('&', cfg.getString(s));
			if (out == null) {
				out = "Configuration Error!";
			}
			return out;
		} catch (Exception ex) {
			out = cfg.getString(s);
			if (out == null) {
				out = "Configuration Error!";
			}
			return out;
		}
	}

}