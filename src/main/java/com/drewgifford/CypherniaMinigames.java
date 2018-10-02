package com.drewgifford;

import com.drewgifford.PlayerData.PlayerManager;
import com.drewgifford.event.EventsHandler;
import com.drewgifford.game.Game;
import com.drewgifford.game.GameManager;
import com.drewgifford.utility.ItemStackSerializer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class CypherniaMinigames extends JavaPlugin {

    Logger log = Bukkit.getLogger();

    public HashMap<UUID, PlayerManager> playermanager = new HashMap<UUID, PlayerManager>();
    public boolean usePlayerCountOnKills = false;
    public String playerCountMsg = "";
    public String countdownMsg = "";
    public String notEnoughPlayersMsg = "";
    public int pointsPerKill = 2;
    private Connection connection;
    private String host, database, username, password;
    private int port;
    public Location spawnLoc;
    public boolean allowJoins = true;
    public ItemStack kitSelector;
    public boolean ingame = false;
    private GameManager gm = new GameManager(this);

    public GameManager getGameManager(){
        return gm;
    }

    //OPTIONS PER GAME
    public boolean kitsEnabled = false;
    public int playersNeeded = 2;
    public Game registeredGame;
    public List<String> killMessages = new ArrayList<String>();
    public List<String> deathMessages = new ArrayList<String>();
    public String coinGainMsg = "";


    public String gameId = "";

    public void onEnable(){
        log.info("Cyphernia Game Core enabled.");
        loadConfig();
        parseConfig();
    }

    public void onDisable(){


    }


    public CypherniaMinigames getThis(){
        return this;
    }

    public void openPlayerJoins(){
        allowJoins = true;
    }

    public void registerMinigame(Game game){

        this.registeredGame = game;

        EventsHandler handler = new EventsHandler(this);
        log.info("Successfully registered minigame "+game.getName()+".");
        allowJoins = false;
        registeredGame.runPreEvents();
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();

    }




    public void parseConfig(){
        this.playerCountMsg= getConfig().getString("messages.playerCount");
        this.coinGainMsg= getConfig().getString("messages.coingain");
        this.killMessages = getConfig().getStringList("messages.killMessage");
        this.deathMessages = getConfig().getStringList("messages.deathMessage");
        this.countdownMsg = getConfig().getString("messages.countdown");

        this.host = getConfig().getString("mysql.host");
        this.port = getConfig().getInt("mysql.port");
        this.database = getConfig().getString("mysql.database");
        this.username = getConfig().getString("mysql.username");
        this.password = getConfig().getString("mysql.password");

        this.kitSelector = ItemStackSerializer.deserialize(getConfig().getString("items.kitselector"));

        this.spawnLoc = new Location(Bukkit.getWorld(getConfig().getString("spawnLocation.world")), getConfig().getDouble("spawnLocation.x"), getConfig().getDouble("spawnLocation.y"), getConfig().getDouble("spawnLocation.z"));

        this.notEnoughPlayersMsg = getConfig().getString("messages.lobby.notEnoughPlayers");

        try{
            openConnection();
        } catch(Exception e) {
            log.info("ERROR: Could not connect to MySQL Database. Is the information entered in correctly?");
            e.printStackTrace();
            //getServer().getPluginManager().disablePlugin(this);
        }


    }




    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }


    // General functions
    public String color(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }
    public void broadcast(String msg){

        Bukkit.broadcastMessage(color(msg));

    }




}
