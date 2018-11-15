package com.drewgifford.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;
import com.drewgifford.game.Kit;

public class EventSelectKit implements Listener {

	@EventHandler
	public void clickItem(InventoryClickEvent e){
		try {
			Inventory i = e.getClickedInventory();
			if(!i.getName().equalsIgnoreCase(Config.color("&8&l&nKit Selector"))) return;
			ItemStack item = e.getCurrentItem();
			String name = item.getItemMeta().getDisplayName();
			Player p = (Player) e.getWhoClicked();
			for(Kit k : CypherniaMinigames.getInstance().getKits()){
				ItemStack x = k.getBuiltIcon();

				if(name.equalsIgnoreCase(x.getItemMeta().getDisplayName())){

					Kit.setSelectedKit(p, k);

					p.sendMessage(Config.color("&6Selected kit &e"+ ChatColor.stripColor(name)));
					p.closeInventory();
				}
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
	}
}
