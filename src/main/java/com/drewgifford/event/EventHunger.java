package com.drewgifford.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.drewgifford.CypherniaMinigames;

public class EventHunger implements Listener {
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
    	if (CypherniaMinigames.getInstance().getGameManager().getIngamePlayers().size() == 0) {
    		event.setCancelled(true);
    		return;
    	}
	}
	
}
