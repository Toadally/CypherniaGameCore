package com.drewgifford.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.drewgifford.CypherniaMinigames;

@SuppressWarnings("deprecation")
public class EventPickupItem implements Listener {
	
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent event) {
		if (CypherniaMinigames.getInstance().players.get(event.getPlayer().getUniqueId()).isIngame() == false) {
			event.setCancelled(true);
		}
	}
	
}
