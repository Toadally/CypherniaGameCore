package com.drewgifford.event;

import com.drewgifford.CypherniaMinigames;
import com.drewgifford.PlayerData.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.UUID;

public class EventJoin implements Listener {

    CypherniaMinigames plugin;

    public EventJoin(CypherniaMinigames plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){


        if(plugin.allowJoins == false){
        	if (plugin.getConfig().getBoolean("bungeecord.useBungee")) {
        		e.getPlayer().sendMessage("§6The server is still initiating. Join back in a few seconds");
        		plugin.connectToBungeeServer(e.getPlayer(), plugin.getConfig().getString("bungeecord.fallback-server"));
        	} else {
        		e.getPlayer().kickPlayer("The server is still initiating. Join back in a few seconds");
        		e.setJoinMessage("The server is still initiating. Join back in a few seconds");
        	}
            return;

        }
        Player p = e.getPlayer();
        p.setAllowFlight(false);
        p.setFlying(false);
        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        p.updateInventory();
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setExp(0.0f);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        p.getInventory().setItem(4, plugin.getKitSelector());
        p.updateInventory();
        p.setFireTicks(0);

        UUID uuid = p.getUniqueId();

        plugin.getScoreboardManager().addPlayer(p);
        plugin.getGameManager().setIngame(p, false);

        plugin.playermanager.put(uuid, new PlayerManager(uuid, false, false, 0, 0));


        if(plugin.getGameManager().getIngamePlayers().size() == 0){
            e.setJoinMessage(plugin.color("&a&l+ &f"+p.getName()));
            plugin.getGameManager().lobbyCheck(Bukkit.getServer().getOnlinePlayers().size());
        } else {
        	if (plugin.getConfig().getBoolean("bungeecord.useBungee")) {
        		e.getPlayer().sendMessage("§cThis game has already started!");
        		plugin.connectToBungeeServer(e.getPlayer(), plugin.getConfig().getString("bungeecord.fallback-server"));
        	} else {
        		p.kickPlayer("The game has already started.");
                e.setJoinMessage("The game has already started.");
        	}
        }

    }
}
