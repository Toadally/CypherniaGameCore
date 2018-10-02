package com.drewgifford.PlayerData;

import java.util.UUID;

public class PlayerManager {

    private UUID uuid;
    private boolean ingame;
    private boolean isdead;

    private int kills;
    private int deaths;

    public PlayerManager(UUID uuid, boolean ingame, boolean isdead, int kills, int deaths){
        this.setUuid(uuid);
        this.setIngame(ingame);
        this.setDead(isdead);
        this.setKills(kills);
        this.setDeaths(deaths);

    }

    public UUID getUuid(){
        return uuid;
    }
    public void setUuid(UUID uuid){
        this.uuid = uuid;
    }
    public boolean isIngame(){
        return ingame;

    }
    public void setIngame(boolean ingame){
        this.ingame = ingame;
    }
    public boolean isDead(){
        return this.isdead;
    }
    public void setDead(boolean isdead){
        this.isdead = isdead;
    }
    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


}
