package com.drewgifford.game;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import com.drewgifford.CypherniaMinigames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameScoreboard {

    private Sidebar sidebar;
    private CypherniaMinigames pl;

    HashMap<String, SidebarString> scores = new HashMap<String, SidebarString>();

    public GameScoreboard(CypherniaMinigames pl){
        this.pl = pl;


        this.sidebar = new Sidebar("", pl, 2);
        sidebar.addEntry(new SidebarString(""));




    }

    public void addScore(String s){

        SidebarString sbs = new SidebarString(pl.color(s));
        scores.put(pl.color(s), sbs);

        sidebar.addEntry(sbs);

    }
    public void removeScore(String s){

        if(scores.get(pl.color(s)) != null){
            sidebar.removeEntry(scores.get(pl.color(s)));
        }

    }

    public void addPlayer(Player p){
        sidebar.showTo(p);
    }

    public void reset(){

        for(SidebarString s : scores.values()){
            sidebar.removeEntry(s);
        }
    }


    //This section is for setting if players' names will be grayed on the scoreboard when they die. Use these methods for single-death game modes.
    public void setAlive(Player p){
        removeScore("&7"+p.getName());
        addScore("&f"+p.getName());
    }
    public void setDead(Player p){
        removeScore("&f"+p.getName());
        addScore("&7"+p.getName());
    }



}
