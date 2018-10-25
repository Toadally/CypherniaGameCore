package com.drewgifford.event;

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
    	if (this.plugin.getGameManager().getIngamePlayers().size() == 0) {
    		event.setCancelled(true);
    		return;
    	}
	}
	
}
