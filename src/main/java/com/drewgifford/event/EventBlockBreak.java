package com.drewgifford.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.drewgifford.CypherniaMinigames;

public class EventBlockBreak implements Listener {

	private CypherniaMinigames cm;
	public EventBlockBreak(CypherniaMinigames cm) {
		this.cm = cm;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (cm.getGameManager().getIngamePlayers().size() == 0) {
			event.setCancelled(true);
		}
	}
}

