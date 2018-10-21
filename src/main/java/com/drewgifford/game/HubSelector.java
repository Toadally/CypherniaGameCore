package com.drewgifford.game;

import org.bukkit.entity.Player;

import com.drewgifford.CypherniaMinigames;

public class HubSelector {
	
	private static CypherniaMinigames pl;
    public HubSelector(CypherniaMinigames plugin){
        pl = plugin;
    }
    
    public static void movePlayerToLobby(Player player) {
    	pl.connectToBungeeServer(player, pl.getConfig().getString("bungeecord.fallback-server"));
    }
	
}
