package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import com.drewgifford.game.KitSelector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventRightClick implements Listener {

    CypherniaMinigames
    plugin;


    public EventRightClick(CypherniaMinigames plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(p.getItemInHand().isSimilar(plugin.getKitSelector())){

                KitSelector.openSelector(plugin, p);

            }

        }

    }
}
