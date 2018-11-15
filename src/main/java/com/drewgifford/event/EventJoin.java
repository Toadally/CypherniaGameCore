package com.drewgifford.event;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;
import com.drewgifford.PlayerData.PlayerManager;

public class EventJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){

		Player p = e.getPlayer();

		if(CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size() == 0){
			e.setJoinMessage(Config.color("&a&l+ &f"+p.getName()));
			CypherniaMinigames.getInstance().getGameManager().lobbyCheck(Bukkit.getServer().getOnlinePlayers().size());
		} else {
			if (CypherniaMinigames.getInstance().getConfig().getBoolean("bungeecord.useBungee")) {
				e.getPlayer().sendMessage("§cThis game has already started!");
				e.setJoinMessage("");
				CypherniaMinigames.getInstance().playersQuit.add(e.getPlayer().getUniqueId());
				new BukkitRunnable() {
					@Override
					public void run() {
						CypherniaMinigames.getInstance().playermanager.get(e.getPlayer().getUniqueId()).connectToBungeeServer(CypherniaMinigames.getInstance().getConfig().getString("bungeecord.fallback-server"));
					}
				}.runTaskLater(CypherniaMinigames.getInstance(), 10L);

			} else {
				p.kickPlayer("The game has already started.");
				e.setJoinMessage("The game has already started.");
			}
			return;
		}

		if(CypherniaMinigames.getInstance().allowJoins == false){
			if (CypherniaMinigames.getInstance().getConfig().getBoolean("bungeecord.useBungee")) {
				e.getPlayer().sendMessage("§6The server is still initiating. Join back in a few seconds");
				e.setJoinMessage("");
				CypherniaMinigames.getInstance().playersQuit.add(e.getPlayer().getUniqueId());
				new BukkitRunnable() {
					@Override
					public void run() {
						CypherniaMinigames.getInstance().playermanager.get(e.getPlayer().getUniqueId()).connectToBungeeServer(CypherniaMinigames.getInstance().getConfig().getString("bungeecord.fallback-server"));
					}
				}.runTaskLater(CypherniaMinigames.getInstance(), 10L);
			} else {
				e.getPlayer().kickPlayer("The server is still initiating. Join back in a few seconds");
				e.setJoinMessage("The server is still initiating. Join back in a few seconds");
			}
			return;
		}

		p.setAllowFlight(false);
		p.setFlying(false);
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.updateInventory();
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setExp((float) 0);
		for (PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}
		if (CypherniaMinigames.getInstance().getGame().kitsEnabled()) {
			p.getInventory().setItem(4, Config.kitSelector);
		}
		if (CypherniaMinigames.getInstance().getConfig().getBoolean("bungeecord.useBungee")) { 
			p.getInventory().setItem(8, Config.hubSelector);
		}
		p.updateInventory();
		p.setFireTicks(0);

		UUID uuid = p.getUniqueId();

		CypherniaMinigames.getInstance().getScoreboardManager().addPlayer(p);
		CypherniaMinigames.getInstance().getGameManager().setIngame(p, false);

		CypherniaMinigames.getInstance().playermanager.put(uuid, new PlayerManager(uuid, false, false, CypherniaMinigames.getInstance()));
	}
}
