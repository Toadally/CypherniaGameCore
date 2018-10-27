package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventLeave implements Listener {

	CypherniaMinigames plugin;

	public EventLeave(CypherniaMinigames plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e){

		Player p = e.getPlayer();
		p.setExp(0);

		plugin.getGameManager().unregisterPlayer(p);
		if (!plugin.playersQuit.contains(e.getPlayer().getUniqueId())) {
			e.setQuitMessage(plugin.color("&c&l- &f"+p.getName()));
			if (plugin.getGameManager().getIngamePlayers().size() != 0) {
				plugin.broadcast(plugin.color(plugin.playerCountMsg.replaceAll("%playercount%", "" + plugin.getGameManager().getIngamePlayers().size())));
			}
		} else {
			e.setQuitMessage("");
			plugin.playersQuit.remove(e.getPlayer().getUniqueId());
		}


		plugin.getGameManager().endgameCheck();

		if(plugin.getGameManager().getIngamePlayers().size() == 0){

			plugin.getGameManager().lobbyCheck(Bukkit.getServer().getOnlinePlayers().size() - 1);

		}

	}


}
