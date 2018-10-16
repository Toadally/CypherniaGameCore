package com.drewgifford.game;

import com.drewgifford.CypherniaMinigames;
import com.drewgifford.utility.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class KitSelector implements Listener {

    CypherniaMinigames pl;
    public KitSelector(CypherniaMinigames pl){
        this.pl = pl;
    }

    public static void openSelector(CypherniaMinigames cmg, Player p){

        List<Kit> kits = cmg.getKits();

        int inventorySize = toNineDenom(kits.size());
        if(inventorySize > 54){ inventorySize = 54; };


        Inventory inv = Bukkit.createInventory(null, inventorySize, cmg.color("&8&l&nKit Selector"));

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
    protected static int toNineDenom(int from)
    {
        return ((((from / 9) + ((from % 9 == 0) ? 0 : 1)) * 9));
    }

    @EventHandler
    public void clickItem(InventoryClickEvent e){
        Inventory i = e.getClickedInventory();

        if(!i.getName().equalsIgnoreCase(pl.color("&8&l&nKit Selector"))) return;

        ItemStack item = e.getCurrentItem();
        String name = item.getItemMeta().getDisplayName();
        Player p = (Player) e.getWhoClicked();

        for(Kit k : pl.getKits()){
            ItemStack x = k.getBuiltIcon();

            if(name.equalsIgnoreCase(x.getItemMeta().getDisplayName())){

                Kit.setSelectedKit(p, k);

                p.sendMessage(pl.color("&6Selected kit &e"+ ChatColor.stripColor(name)));
                p.closeInventory();





            }
            e.setCancelled(true);

        }


    }
}
