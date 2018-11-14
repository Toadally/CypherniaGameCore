package com.drewgifford.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.drewgifford.CypherniaMinigames;

@SuppressWarnings("deprecation")
public class EventPickupItem implements Listener {
	
	private CypherniaMinigames cm;
	public EventPickupItem(CypherniaMinigames cm) {
		this.cm = cm;
	}
	
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent event) {
		if (cm.getGameManager().getIngamePlayers().size() == 0) {
			event.setCancelled(true);
		}
	}
	
}
