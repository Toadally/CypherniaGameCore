package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventLeave {

    CypherniaMinigames plugin;

    public EventLeave(CypherniaMinigames plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        Player p = e.getPlayer();

        Bukkit.broadcastMessage(plugin.color("&c&l- &f"+p.getName()));

        if(plugin.ingame == false){

            plugin.getGameManager().lobbyCheck();

        }

    }


}
