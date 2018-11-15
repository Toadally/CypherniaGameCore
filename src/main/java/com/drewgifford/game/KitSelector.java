package com.drewgifford.game;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;
import com.drewgifford.utility.ItemUtils;

public class KitSelector {

	CypherniaMinigames pl;
	public KitSelector(CypherniaMinigames pl){
		this.pl = pl;
	}

	public static void openSelector(CypherniaMinigames cmg, Player p){

		List<Kit> kits = cmg.getKits();

		int inventorySize = toNineDenom(kits.size());
		if(inventorySize > 54){ inventorySize = 54; };

		Inventory inv = Bukkit.createInventory(null, inventorySize, Config.color("&8&l&nKit Selector"));
		int index = 0;
		for(Kit kit : kits){
			ItemStack is = kit.getBuiltIcon();
			Kit selectedKit = Kit.getSelectedKit(p);
			if(selectedKit != null){
				if(is.isSimilar(selectedKit.getBuiltIcon())){
					is = ItemUtils.addGlow(is);
				}
			}
			inv.setItem(index, is);
			index++;
		}
		p.closeInventory();
		p.openInventory(inv);
	}
	private static int toNineDenom(int from) {
		return ((((from / 9) + ((from % 9 == 0) ? 0 : 1)) * 9));
	}

}
