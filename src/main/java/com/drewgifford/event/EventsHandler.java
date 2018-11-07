package com.drewgifford.event;

import org.bukkit.plugin.PluginManager;

import com.drewgifford.CypherniaMinigames;

public class EventsHandler {

	public static EventJoin joinEvent;
	public static EventLeave leaveEvent;
	public static EventDamage damageEvent;
	public static EventRightClick rightClickEvent;
	public static EventHunger hungerEvent;
	public static EventSelectKit selectKitEvent;
	public static EventPickupItem pickupItemEvent;
	public static EventBlockBreak blockBreakEvent;

	public EventsHandler(CypherniaMinigames plugin){

		joinEvent = new EventJoin(plugin);
		leaveEvent = new EventLeave(plugin);
		damageEvent = new EventDamage(plugin);
		rightClickEvent = new EventRightClick(plugin);
		hungerEvent = new EventHunger(plugin);
		selectKitEvent = new EventSelectKit(plugin);
		pickupItemEvent = new EventPickupItem(plugin);
		blockBreakEvent = new EventBlockBreak(plugin);

		PluginManager pm = plugin.getServer().getPluginManager();

		pm.registerEvents(joinEvent, plugin);
		pm.registerEvents(leaveEvent, plugin);
		pm.registerEvents(damageEvent, plugin);
		pm.registerEvents(rightClickEvent, plugin);
		pm.registerEvents(hungerEvent, plugin);
		pm.registerEvents(selectKitEvent, plugin);
		pm.registerEvents(pickupItemEvent, plugin);
		pm.registerEvents(blockBreakEvent, plugin);
	}
}
