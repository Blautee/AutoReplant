package de.blautee.autoreplant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class AutoReplantCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (args.length == 0) {
				if (p.hasPermission(Settings.user_perm)) {
					// TODO Simplify
					// TOGGLE
					if (Settings.useAuto.containsKey(p.getUniqueId())) {
						if (Settings.useAuto.get(p.getUniqueId())) {
							// DEACT
							Settings.useAuto.put(p.getUniqueId(), false);
							Main.getPlugin().getConfig().set("config.player_list." + p.getUniqueId().toString(), false);
							Main.getPlugin().saveConfig();
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
									TextComponent.fromLegacyText(Settings.deactivated));
						} else {
							// ACT
							Settings.useAuto.put(p.getUniqueId(), true);
							Main.getPlugin().getConfig().set("config.player_list." + p.getUniqueId().toString(), true);
							Main.getPlugin().saveConfig();
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
									TextComponent.fromLegacyText(Settings.activated));
						}
					} else {
						// ACT
						Settings.useAuto.put(p.getUniqueId(), true);
						Main.getPlugin().getConfig().set("config.player_list." + p.getUniqueId().toString(), true);
						Main.getPlugin().saveConfig();
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
								TextComponent.fromLegacyText(Settings.activated));

					}
					return true;
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (p.hasPermission(Settings.admin_perm)) {
						Main.getPlugin().reloadConfig();
						Settings.reloadConfig();
						Settings.reloadLang();
						p.sendMessage(Settings.prefix + Settings.reload_done);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("toggleworld")) {
					if (p.hasPermission(Settings.admin_perm)) {
						String worldName = p.getWorld().getName();
						
						if (Settings.worldBlacklist.contains(worldName)) {
							Settings.worldBlacklist.remove(worldName);
							p.sendMessage(Settings.prefix + Settings.world_removed_from_blacklist);
						} else {
							Settings.worldBlacklist.add(worldName);
							p.sendMessage(Settings.prefix + Settings.world_added_to_blacklist);
						}
						
						Main.getPlugin().getConfig().set("config.world_blacklist", Settings.worldBlacklist);
						Main.getPlugin().saveConfig();
					}
				}
			}
			
			return false;

		} else {
			sender.sendMessage(Settings.prefix + "This only works as a Player!");
			return false;
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(Settings.admin_perm)) {
			List<String> completions = new ArrayList<String>();
			List<String> commands = new ArrayList<String>();
			
			if (args.length == 1) {
				commands.add("toggleworld");
				commands.add("reload");
			}
			
			StringUtil.copyPartialMatches(args[0], commands, completions);
			Collections.sort(completions);
			return completions;
		} else {
			return null;
		}
	}
}
