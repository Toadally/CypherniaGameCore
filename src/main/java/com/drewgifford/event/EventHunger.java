package com.drewgifford.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.drewgifford.CypherniaMinigames;

public class EventHunger implements Listener {
	
	private CypherniaMinigames plugin;
	public EventHunger(CypherniaMinigames plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		boolean exists = false;
    	for (Player p : this.plugin.getGameManager().getIngamePlayers()) {
    		if (p.getName().equalsIgnoreCase(((Player) event.getEntity()).getName())) {
    			exists = true;
    		}
    	}
    	if (exists == false) {
    		event.setCancelled(true);
    		return;
    	}
	}
	
}
