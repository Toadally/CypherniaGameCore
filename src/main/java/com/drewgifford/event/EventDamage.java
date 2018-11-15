package com.drewgifford.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;

public class EventDamage implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (CypherniaMinigames.getInstance().players.get(((Player) event.getEntity()).getUniqueId()).isIngame() == false) {
				event.setCancelled(true);
				return;
			}
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
				CypherniaMinigames.getInstance().players.get(player.getUniqueId()).setIngame(false);
				CypherniaMinigames.getInstance().getGameManager().endgameCheck();
				CypherniaMinigames.getInstance().broadcast(Config.color(Config.playerCountMsg.replaceAll("%playercount%", "" + CypherniaMinigames.getInstance().getGameManager().getInGamePlayers())));
			}
			GameDeathEvent instantDeathEvent = new GameDeathEvent(event);
			Bukkit.getServer().getPluginManager().callEvent(instantDeathEvent);
		}
	}

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (CypherniaMinigames.getInstance().players.get(((Player) event.getDamager()).getUniqueId()).isIngame() == false) {
				event.setCancelled(true);
				return;
			}
		}
		if (event.getEntity() instanceof Player) {
			if (CypherniaMinigames.getInstance().players.get(((Player) event.getEntity()).getUniqueId()).isIngame() == false) {
				event.setCancelled(true);
				return;
			}
		}
		if (event.getDamager() instanceof Player) {
			if (Config.coinGainActive) {
				if (event.getEntity() instanceof Player) {
					if (((Player) event.getEntity()).getHealth() - event.getDamage() <= 0) {
						CypherniaMinigames.getInstance().players.get(((Player) event.getDamager()).getUniqueId()).addCoins(Config.coinsPerKill);
					}
				}
			}
		}

	}

}
