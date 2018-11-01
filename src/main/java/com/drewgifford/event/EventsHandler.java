package com.drewgifford.event;

import org.bukkit.plugin.PluginManager;

import com.drewgifford.CypherniaMinigames;

public class EventsHandler {

    public EventsHandler(CypherniaMinigames plugin){

        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new EventJoin(plugin), plugin);
        pm.registerEvents(new EventLeave(plugin), plugin);
        pm.registerEvents(new EventDamage(plugin), plugin);
        pm.registerEvents(new EventRightClick(plugin), plugin);
        pm.registerEvents(new EventHunger(plugin), plugin);
        pm.registerEvents(new EventSelectKit(plugin), plugin);
        pm.registerEvents(new EventPickupItem(plugin), plugin);
        pm.registerEvents(new EventBlockBreak(plugin), plugin);
    }
}
