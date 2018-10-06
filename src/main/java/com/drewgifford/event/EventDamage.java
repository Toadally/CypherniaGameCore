package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EventDamage implements Listener {

    CypherniaMinigames plugin;

    public EventDamage(CypherniaMinigames plugin){
        this.plugin = plugin;
    }

    @EventHandler
	public void onDamage(EntityDamageEvent event) {
    	
		if (event.getEntity() instanceof Player) {
			
			boolean exists = false;
	    	for (Player p : this.plugin.getGameManager().getIngamePlayers()) {
	    		if (p.getName().equalsIgnoreCase(((Player) event.getEntity()).getName())) {
	    			exists = true;
	    		}
	    	}
	    	if (exists == false) {
	    		event.setCancelled(true);
	    		return;
	    	}

			
			Player player = (Player) event.getEntity();
			if (player.getHealth() - event.getDamage() <= 0) {
				player.sendMessage("You have died! You are now in spectator mode!");
				player.setHealth(20.0);
				player.getInventory().clear();
				player.updateInventory();
	            if(plugin.getGameManager().spectatorEnabled){
	            	plugin.getGameManager().setSpectator(player);
	            	plugin.getGameManager().setIngame(player, false);
	            	plugin.getGameManager().endgameCheck();

	            	plugin.broadcast(plugin.color(plugin.playerCountMsg.replaceAll("%playercount%", "" + plugin.getGameManager().getIngamePlayers().size())));
	            }
                InstantDeathEvent instantDeathEvent = new InstantDeathEvent(event);
                Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}
	}
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){



    }
}
