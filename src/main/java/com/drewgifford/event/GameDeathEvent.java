package com.drewgifford.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private EntityDamageEvent e;


    public GameDeathEvent(EntityDamageEvent e){
        this.e = e;
    }

    public EntityDamageEvent getOriginalDamageEvent(){
        return this.e;
    }
    public Player getPlayer(){
        return (Player) this.e.getEntity();
    }
}
