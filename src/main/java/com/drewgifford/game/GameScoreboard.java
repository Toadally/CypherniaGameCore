package com.drewgifford.game;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import com.drewgifford.CypherniaMinigames;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameScoreboard {

    private Sidebar sidebar;
    private CypherniaMinigames pl;
    public String gamename;

    HashMap<String, SidebarString> scores = new HashMap<String, SidebarString>();

    public GameScoreboard(CypherniaMinigames pl, String gamename){
        this.pl = pl;
        this.gamename = gamename;


        this.sidebar = new Sidebar(gamename, pl, 1);
        sidebar.addEntry(new SidebarString(""));




    }

    public void addScore(String s){

        SidebarString sbs = new SidebarString(pl.color(s));
        scores.put(pl.color(s), sbs);

        sidebar.addEntry(sbs);

    }
    public void addScoreFrames(String... s){

        String[] s1 = new String[s.length];
        int x = 0;
        for(String ss : s){
            s1[x] = pl.color(ss);
            x++;
        }

        SidebarString sbs = new SidebarString(s1);

        scores.put(ChatColor.stripColor(s1[0]), sbs);

        sidebar.addEntry(sbs);

    }
    public void removeScore(String s){

        if(scores.get(pl.color(s)) != null){
            sidebar.removeEntry(scores.get(pl.color(s)));

            scores.remove(pl.color(s));
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
        addScore("&f"+p.getName());
    }
    public void setDead(Player p){
        SidebarString sbs = scores.get(pl.color("&f"+p.getName()));

        sbs.addVariation(pl.color("&7"+p.getName()));
        sbs.removeVariation(pl.color("&f"+p.getName()));
    }



}
