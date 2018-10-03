package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class EventDamage implements Listener {

    CypherniaMinigames plugin;

    public EventDamage(CypherniaMinigames plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(e.getEntity() instanceof Player){

            Player p = (Player) e.getEntity();
            p.setBedSpawnLocation(p.getLocation());
            e.getDrops().clear();
            if(plugin.getGameManager().spectatorEnabled){
                plugin.getGameManager().setSpectator(p);
                plugin.getGameManager().setIngame(p, false);
                plugin.getGameManager().endgameCheck();

                plugin.broadcast(plugin.color(plugin.playerCountMsg.replaceAll("%playercount%", "" + plugin.getGameManager().getIngamePlayers().size())));
            }
        }


    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){



    }
}
