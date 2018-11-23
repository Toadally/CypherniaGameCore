package com.drewgifford.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.drewgifford.CypherniaMinigames;

public class EventInventory implements Listener {

	@EventHandler
	public void onDropItem(PlayerDropItemEvent event) {
		if (CypherniaMinigames.getInstance().getGameManager().getInGamePlayers() == 0) {
			event.setCancelled(true);

		}
	}

	@EventHandler
	public void onInteract(InventoryInteractEvent event) {
		if (CypherniaMinigames.getInstance().getGameManager().getInGamePlayers() == 0) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (CypherniaMinigames.getInstance().getGameManager().getInGamePlayers() == 0) {
			event.setCancelled(true);
		}
	}

}
