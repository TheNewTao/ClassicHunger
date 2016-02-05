package me.thenewtao.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.thenewtao.classichunger.ClassicHungerMain;

public class EventListener implements Listener {

	ClassicHungerMain main;

	public EventListener(ClassicHungerMain main) {
		this.main = main;
	}

	@EventHandler
	public void onEat(FoodLevelChangeEvent e) {
		if ((e.getEntity() instanceof Player)) {
			e.setFoodLevel(19);
		}
	}

	@EventHandler
	public void cancelRegen(EntityRegainHealthEvent e) {
		if (((e.getEntity() instanceof Player))
				&& (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onLog(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			public void run() {
				p.getPlayer().setFoodLevel(19);
			}
		}, 10);

	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (e.getPlayer() instanceof Player) {
			final Player p = e.getPlayer();
			Bukkit.getScheduler().runTaskLater(main, new Runnable() {
				public void run() {
					p.getPlayer().setFoodLevel(19);
				}
			}, 10);
		}
	}

	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		long mygetCooldown = (main.getConfig().getInt("getCooldown()") * 1000);
		if (main.getCooldown().containsKey(p.getUniqueId())) {
			if (System.currentTimeMillis() - main.getCooldown().get(p.getUniqueId()) > mygetCooldown) {

				main.getCooldown().remove(p.getUniqueId());

				if (p.getHealth() > (20 - main.getFoodType().get(e.getItem().getType()))) {
					p.setHealth(20);
				} else {
					p.setHealth(p.getHealth() + main.getFoodType().get(e.getItem().getType()));
				}
				main.getCooldown().put(p.getUniqueId(), System.currentTimeMillis());

			}

			else if (System.currentTimeMillis() - main.getCooldown().get(p.getUniqueId()) < 60000) {
				p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "========================");
				p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "The cooldown is still active! ");
				double leftT = main.getConfig().getInt("cooldown")
						- ((System.currentTimeMillis() - main.getCooldown().get(p.getUniqueId())) / 1000);
				String timeRemaining = String.valueOf(leftT);
				p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + timeRemaining + " seconds left!");
				p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "========================");
			}
		}
		if (!main.getCooldown().containsKey(p.getUniqueId())) {

			if (p.getHealth() > (20 - main.getFoodType().get(e.getItem().getType()))) {
				p.setHealth(20);
			} else {
				p.setHealth(p.getHealth() + main.getFoodType().get(e.getItem().getType()));
			}
			main.getCooldown().put(p.getUniqueId(), System.currentTimeMillis());
		}
	}
}
