package com.drewgifford.game;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String name;
    private String description;
    public String version;
    CypherniaMinigames cm;
    public List<String> killMessages = new ArrayList<String>();
    public List<String> deathMessages = new ArrayList<String>();

    private boolean playerCountOnDeath = false;
    private boolean singleLife = false;
    private int minPlayers = 2;
    private int maxPlayers = 8;

    public Game(String name, String description, String version, CypherniaMinigames cm){
        this.name = name;
        this.cm = cm;
        this.description = description;
        this.version = version;
    }

    public void setMinPlayers(int minplayers){
        this.minPlayers = minplayers;
    }
    public int getMinPlayers(){
        return minPlayers;

    }
    public void setMaxPlayers(int maxplayers){
        this.maxPlayers = maxplayers;

    }
    public int getMaxPlayers(){
        return maxPlayers;

    }

    public void setSingleLife(boolean b){
        this.singleLife = b;
    }
    public boolean isSingleLife(){
        return this.singleLife;
    }
    public void setShowPlayerCountOnDeaths(boolean b){
        this.playerCountOnDeath = b;
    }
    public boolean isShowingPlayerCountOnDeaths(){
        return this.playerCountOnDeath;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }

    //Run Pregame events
    public void runPreEvents(){
        cm.openPlayerJoins();
    }

    //Start the game
    public void startGame(){

    }

    //Run Postgame Events
    public void runPostEvents(){


        Bukkit.getServer().shutdown();

    }
}
