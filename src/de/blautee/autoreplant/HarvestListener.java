package de.blautee.autoreplant;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class HarvestListener implements Listener {

	@EventHandler
	public void onHarvest(BlockBreakEvent e) {
		if (Settings.useAuto.get(e.getPlayer().getUniqueId())) {
			if ((!e.getPlayer().isSneaking())) {
				if (Settings.matList.contains(e.getBlock().getType())) {
					if (e.getBlock().getBlockData() instanceof Ageable) {
						Ageable age = (Ageable) e.getBlock().getBlockData();
						if (age.getAge() == age.getMaximumAge()) {
							System.out.println(e.getBlock().getDrops());
							System.out.println(e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand()));

							Location replant = e.getBlock().getLocation();

							Block plant = e.getBlock();
							Ageable plantData = (Ageable) plant.getBlockData();
							plantData.setAge(0);
							plant.setBlockData(plantData);

							replant.getBlock().setBlockData(plantData);

						} else {
							// NOT READY YET
							e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
									TextComponent.fromLegacyText(Settings.not_ready));
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
