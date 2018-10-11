package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventLeave implements Listener {

    CypherniaMinigames plugin;

    public EventLeave(CypherniaMinigames plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){



        Player p = e.getPlayer();

        plugin.getGameManager().unregisterPlayer(p);

        e.setQuitMessage(plugin.color("&c&l- &f"+p.getName()));
        plugin.broadcast(plugin.color(plugin.playerCountMsg.replaceAll("%playercount%", "" + plugin.getGameManager().getIngamePlayers().size())));
        
        plugin.getGameManager().endgameCheck();
        
        if(plugin.ingame == false){

            plugin.getGameManager().lobbyCheck(Bukkit.getServer().getOnlinePlayers().size() - 1);

        }

    }


}
