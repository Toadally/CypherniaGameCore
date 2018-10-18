package com.drewgifford.game;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Kit {



    private String name;
    private Material icon;
    private List<String> lore;
    private ItemStack[] items;
    private CypherniaMinigames pl;

    public Kit(String name, List<String> lore, Material icon, CypherniaMinigames pl, ItemStack... items){
        this.name = name;
        this.lore = lore;
        this.icon = icon;
        this.items = items;
        this.pl =pl;
    }

    private static HashMap<Player, Kit> selectedKit = new HashMap<Player, Kit>();

    public static Kit getSelectedKit(Player p){
        return selectedKit.get(p);
    }
    public static void setSelectedKit(Player p, Kit k){
        selectedKit.put(p, k);
    }

    public ItemStack[] getItems(){
        return this.items;
    }
    public Material geticon(){
        return this.icon;
    }
    public List<String> getLore(){
        return this.lore;
    }
    public String getName(){
        return this.name;
    }
    public ItemStack getBuiltIcon(){
        ItemStack builtIcon = new ItemStack(icon, 1);

        ItemMeta m = builtIcon.getItemMeta();
        m.setDisplayName(pl.color("&e"+this.name));
        m.setLore(this.lore);
        builtIcon.setItemMeta(m);

        return builtIcon;

    }
}
