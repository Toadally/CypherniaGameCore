package com.drewgifford.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;

public class EventLeave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e){

		Player p = e.getPlayer();
		p.setExp(0);

		CypherniaMinigames.getInstance().getGameManager().unregisterPlayer(p);
		if (!CypherniaMinigames.getInstance().playersQuit.contains(e.getPlayer().getUniqueId())) {
			e.setQuitMessage(Config.color("&c&l- &f"+p.getName()));
			if (CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size() != 0) {
				CypherniaMinigames.getInstance().broadcast(Config.color(Config.playerCountMsg.replaceAll("%playercount%", "" + CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size())));
			}
		} else {
			e.setQuitMessage("");
			CypherniaMinigames.getInstance().playersQuit.remove(e.getPlayer().getUniqueId());
		}
		if(CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size() == 0){
			CypherniaMinigames.getInstance().getGameManager().lobbyCheck(Bukkit.getServer().getOnlinePlayers().size() - 1);
		}
	}
}
