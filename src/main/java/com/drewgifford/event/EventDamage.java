package com.drewgifford.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;

public class EventDamage implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageEvent event) {

		if (event.getEntity() instanceof Player) {
			if (CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size() == 0) {
				event.setCancelled(true);
				return;
			}
			Player player = (Player) event.getEntity();
			if (player.getHealth() - event.getDamage() <= 0) {
				if (CypherniaMinigames.getInstance().getGame().singleLife() == false) {
					player.sendMessage("You have died! You are now in spectator mode!");
					player.setHealth(20.0);
					player.getInventory().clear();
					player.updateInventory();
					player.setGameMode(GameMode.SPECTATOR);
					player.sendTitle(Config.color("&c&lYou died"), Config.color("&7You are now in spectator mode."));
					CypherniaMinigames.getInstance().getGameManager().setIngame(player, false);
					CypherniaMinigames.getInstance().getGameManager().endgameCheck();

					CypherniaMinigames.getInstance().broadcast(Config.color(Config.playerCountMsg.replaceAll("%playercount%", "" + CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size())));
				}
				GameDeathEvent instantDeathEvent = new GameDeathEvent(event);
				Bukkit.getServer().getPluginManager().callEvent(instantDeathEvent);
			}
		}
	}

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			boolean exists = false;
			for (Player p : CypherniaMinigames.getInstance().getGameManager().getIngamePlayers()) {
				if (p.getName().equalsIgnoreCase(((Player) event.getDamager()).getName())) {
					exists = true;
				}
			}
			if (exists == false) {
				event.setCancelled(true);
				return;
			}
			if (Config.coinGainActive) {
				if (event.getEntity() instanceof Player) {
					if (((Player) event.getEntity()).getHealth() - event.getDamage() <= 0) {
						CypherniaMinigames.getInstance().playermanager.get(((Player) event.getDamager()).getUniqueId()).addCoins(Config.coinsPerKill);
					}
				}
			}
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){



	}
}
