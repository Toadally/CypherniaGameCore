package com.drewgifford.game;

import com.drewgifford.CypherniaMinigames;

public class Game {


    private String name;
    CypherniaMinigames pl;

    public Game(String name, CypherniaMinigames pl){
        this.name = name;
        this.pl = pl;
    }


    public String getName(){
        return this.name;
    }
    //Run Pregame events
    public void runPreEvents(){
        pl.openPlayerJoins();
    }

    //Start the game
    public void startGame(){

    }

    //Run Postgame Events
    public void runPostEvents(){

    }
}
