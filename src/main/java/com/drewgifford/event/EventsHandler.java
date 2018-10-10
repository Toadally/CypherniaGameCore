package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.plugin.PluginManager;

public class EventsHandler {

    public EventsHandler(CypherniaMinigames plugin){

        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new EventJoin(plugin), plugin);
        pm.registerEvents(new EventLeave(plugin), plugin);
        pm.registerEvents(new EventDamage(plugin), plugin);
        pm.registerEvents(new EventRightClick(plugin), plugin);
        pm.registerEvents(new EventHunger(plugin), plugin);

    }
}
