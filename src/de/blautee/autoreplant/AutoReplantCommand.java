package de.blautee.autoreplant;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class AutoReplantCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (p.hasPermission(Settings.admin_perm)) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						Settings.reloadConfig();
						Settings.reloadLang();
						p.sendMessage(Settings.prefix + Settings.reload_done);
						return true;
					}
					return false;
				}
				return false;
			}

			if (args.length == 0 && p.hasPermission(Settings.user_perm)) {
				// TODO
				// TOGGLE
				if (Settings.useAuto.get(p.getUniqueId())) {
					//DEACT
					Settings.useAuto.put(p.getUniqueId(), false);
					Main.getPlugin().getConfig().set("config.player_list" + p.getUniqueId().toString(), false);
					Main.getPlugin().saveConfig();
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Settings.deactivated));
				} else {
					//ACT
					Settings.useAuto.put(p.getUniqueId(), true);
					Main.getPlugin().getConfig().set("config.player_list" + p.getUniqueId().toString(), true);
					Main.getPlugin().saveConfig();
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							TextComponent.fromLegacyText(Settings.activated));
				}
				return true;
			}

			return false;

		} else {
			sender.sendMessage(Settings.prefix + "This only works as a Player!");
			return false;
		}
	}
}
