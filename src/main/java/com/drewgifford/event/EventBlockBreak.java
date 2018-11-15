package com.drewgifford.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.drewgifford.CypherniaMinigames;

public class EventBlockBreak implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (CypherniaMinigames.getInstance().getGameManager().isInGame(event.getPlayer()) == false) {
			event.setCancelled(true);
		}
	}
}

