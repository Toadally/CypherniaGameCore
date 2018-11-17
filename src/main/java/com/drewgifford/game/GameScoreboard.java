package com.drewgifford.game;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;

public class GameScoreboard {

    private Sidebar sidebar;
    public String gamename;

    HashMap<String, SidebarString> scores = new HashMap<String, SidebarString>();

    public GameScoreboard(CypherniaMinigames pl, String gamename){
        this.gamename = gamename;
        this.sidebar = new Sidebar(gamename, pl, 1);
        sidebar.addEntry(new SidebarString(""));
    }

    public void addScore(String s){

    	SidebarString sbs;
    	if (s.length() > 24) {
    		sbs = SidebarString.generateScrollingAnimation(s, 18);
    	} else {
    		sbs = new SidebarString(Config.color(s));
    	}
        
        scores.put(Config.color(s), sbs);

        sidebar.addEntry(sbs);

    }
    public void addScoreFrames(String... s){

        String[] s1 = new String[s.length];
        int x = 0;
        for(String ss : s){
            s1[x] = Config.color(ss);
            x++;
        }

        SidebarString sbs = new SidebarString(s1);

        scores.put(ChatColor.stripColor(s1[0]), sbs);

        sidebar.addEntry(sbs);

    }
    public void removeScore(String s){

        if(scores.get(Config.color(s)) != null){
            sidebar.removeEntry(scores.get(Config.color(s)));

            scores.remove(Config.color(s));
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
        addScore("&7[&aALIVE&7] &r" + p.getName());
    }
    public void setDead(Player p){
        SidebarString sbs = scores.get(Config.color("&f[&aALIVE&f] " + p.getName()));

        sbs.addVariation(Config.color("&7[&cDEAD&7] " + p.getName()));
        sbs.removeVariation(Config.color("&f[&aALIVE&f] " + p.getName()));
    }



}
