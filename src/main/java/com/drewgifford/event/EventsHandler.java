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

	public EventsHandler(){

		joinEvent = new EventJoin();
		leaveEvent = new EventLeave();
		damageEvent = new EventDamage();
		rightClickEvent = new EventRightClick();
		hungerEvent = new EventHunger();
		selectKitEvent = new EventSelectKit();
		pickupItemEvent = new EventPickupItem();
		blockBreakEvent = new EventBlockBreak();

		PluginManager pm = CypherniaMinigames.getInstance().getServer().getPluginManager();

		pm.registerEvents(joinEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(leaveEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(damageEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(rightClickEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(hungerEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(selectKitEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(pickupItemEvent, CypherniaMinigames.getInstance());
		pm.registerEvents(blockBreakEvent, CypherniaMinigames.getInstance());
	}
}
