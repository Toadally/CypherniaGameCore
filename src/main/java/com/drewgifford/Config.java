package com.drewgifford;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.drewgifford.utility.ItemStackSerializer;

public class Config {
	
	public static String playerCountMsg = "";
	public static String countdownMsg = "";
	public static String notEnoughPlayersMsg = "";
	public static int coinsPerKill = 10;
	public static int coinsPerWin = 50;
	public static boolean coinGainActive = false;
	public static List<String> killMessages = new ArrayList<String>();
	public static List<String> deathMessages = new ArrayList<String>();
	public static String coinGainMsg = "";
	public static ItemStack kitSelector;
	public static ItemStack hubSelector;
	public static int countdownTime;
	
	public static void parseConfig(JavaPlugin plugin) {
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		playerCountMsg = plugin.getConfig().getString("messages.playerCount");
		coinGainMsg = plugin.getConfig().getString("messages.coingain");
		killMessages = plugin.getConfig().getStringList("messages.killMessage");
		deathMessages = plugin.getConfig().getStringList("messages.deathMessage");
		countdownMsg = plugin.getConfig().getString("messages.countdown");
		notEnoughPlayersMsg = plugin.getConfig().getString("messages.lobby.notEnoughPlayers");
		kitSelector = ItemStackSerializer.deserialize(plugin.getConfig().getString("items.kitselector"));
		hubSelector = ItemStackSerializer.deserialize(plugin.getConfig().getString("items.hub"));
		countdownTime = plugin.getConfig().getInt("countdown");
	}
	
	public static String color(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
