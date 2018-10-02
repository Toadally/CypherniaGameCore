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
            p.spigot().respawn();
            e.getDrops().clear();
            if(plugin.getGameManager().spectatorEnabled){
                plugin.getGameManager().setSpectator(p);
                plugin.getGameManager().setIngame(p, false);
                plugin.getGameManager().endgameCheck();
            }
        }


    }
}
