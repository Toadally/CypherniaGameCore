package com.drewgifford.event;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;
import com.drewgifford.game.KitSelector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventRightClick implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (p.getItemInHand().isSimilar(Config.kitSelector)){
				KitSelector.openSelector(CypherniaMinigames.getInstance(), p);
			} else if (p.getItemInHand().isSimilar(Config.hubSelector) && CypherniaMinigames.getInstance().getConfig().getBoolean("bungeecord.useBungee")) {
				CypherniaMinigames.getInstance().players.get(p.getUniqueId()).connectToBungeeServer(CypherniaMinigames.getInstance().getConfig().getString("bungeecord.fallback-server"));
			}
		}

	}
}
