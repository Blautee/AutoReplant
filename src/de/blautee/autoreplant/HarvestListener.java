package de.blautee.autoreplant;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class HarvestListener implements Listener {
	
	@EventHandler
	public void onJoinActivation(PlayerJoinEvent e) {
		if (Settings.autoactivate) {
			if (!Settings.useAuto.keySet().contains(e.getPlayer().getUniqueId())) {
				Settings.useAuto.put(e.getPlayer().getUniqueId(), true);
				Main.getPlugin().getConfig().set("config.player_list." + e.getPlayer().getUniqueId().toString(), true);
				Main.getPlugin().saveConfig();
			}
		}
	}

	@EventHandler
	public void onHarvest(BlockBreakEvent e) {
		if (Main.useWorldGuard) {
			Location loc = BukkitAdapter.adapt(e.getBlock().getLocation());
			RegionContainer c = WorldGuard.getInstance().getPlatform().getRegionContainer();
			RegionQuery q = c.createQuery();
			ApplicableRegionSet set = q.getApplicableRegions(loc);
			ProtectedRegion region = null;
			
			if (set.getRegions().size() == 1) {
				region = (ProtectedRegion) set.getRegions().toArray()[0];
			} else if (set.getRegions().size() > 1) {
				for (ProtectedRegion rg : set.getRegions()) {
					if (region == null) {
						region = rg;
					}
					
					if (rg.getPriority() > region.getPriority()) {
						region = rg;
					}
				}
			}
			
			if (region != null) {
				if (region.getOwners().contains(e.getPlayer().getUniqueId())) {
					// User has Owner rights, continue
				} else if (region.getMembers().contains(e.getPlayer().getUniqueId())) {
					// User has Member rights, continue
				} else {
					// User is not allowed to harvest here!
					// Return and let WorldGuard do its thing!
					return;
				}
			}
		}		
		
		if (Settings.worldBlacklist.contains(e.getPlayer().getWorld().getName())) {
			return;
		}
		if (Settings.useAuto.get(e.getPlayer().getUniqueId()) != null) {
			if (Settings.useAuto.get(e.getPlayer().getUniqueId())) {
				if ((!e.getPlayer().isSneaking())) {
					if (Settings.matList.contains(e.getBlock().getType())) {
						if (e.getBlock().getBlockData() instanceof Ageable) {
							Ageable age = (Ageable) e.getBlock().getBlockData();
							if (age.getAge() == age.getMaximumAge()) {
								
								Block harvest = e.getBlock();
								Collection<ItemStack> drops = harvest
										.getDrops(e.getPlayer().getInventory().getItemInMainHand());

								e.setDropItems(false);

								int dropCount = 1;

								for (ItemStack itemStack : drops) {
									int dropAmount = itemStack.getAmount();
									if (dropCount == 2) {
										dropAmount = itemStack.getAmount() - 1;
									}

									if (dropAmount != 0) {
										ItemStack drop = new ItemStack(itemStack.getType(), dropAmount);
									}
									
									harvest.getWorld().dropItemNaturally(harvest.getLocation(), drop);
									dropCount++;
								}

								e.setCancelled(true);
								age.setAge(0);
								e.getBlock().setBlockData(age);

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
}
