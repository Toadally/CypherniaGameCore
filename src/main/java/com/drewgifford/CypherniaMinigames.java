package com.drewgifford;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.drewgifford.PlayerData.PlayerManager;
import com.drewgifford.event.EventsHandler;
import com.drewgifford.game.Game;
import com.drewgifford.game.GameManager;
import com.drewgifford.game.GameScoreboard;
import com.drewgifford.game.Kit;
import com.drewgifford.utility.ItemStackSerializer;

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
	public ItemStack hubSelector;
	private GameManager gm = new GameManager(this);
	private GameScoreboard gs;
	private Scoreboard scoreboard;
	public List<UUID> playersQuit = new ArrayList<UUID>();
	public EventsHandler handler;

	//OPTIONS PER GAME
	public boolean kitsEnabled = false;
	public int playersNeeded = 2;
	public Game registeredGame;
	public List<String> killMessages = new ArrayList<String>();
	public List<String> deathMessages = new ArrayList<String>();
	public String coinGainMsg = "";


	public String gameId = "";

	private List<Kit> kits = new ArrayList<Kit>();

	public List<Kit> getKits(){
		return this.kits;
	}
	public void addKit(Kit k){
		kits.add(k);
	}

	public Scoreboard getScoreboard(){
		return this.scoreboard;
	}
	public Game getGame(){
		return this.registeredGame;
	}

	public ItemStack getKitSelector() {
		return kitSelector;
	}

	public ItemStack getHubSelector() {
		return hubSelector;
	}

	public GameManager getGameManager(){
		return gm;
	}
	public GameScoreboard getScoreboardManager(){
		return gs;
	}

	public void onEnable(){
		log.info("Cyphernia Game Core enabled.");
		loadConfig();
		parseConfig();
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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

		this.handler = new EventsHandler(this);
		log.info("Successfully registered minigame "+game.getName()+".");
		allowJoins = false;
		registeredGame.runPreEvents();
		gs = new GameScoreboard(this, game.getName());
		gs.reset();
		gs.addScore(color("&cWaiting for players..."));

	}

	public void reloadMinigame(Game game) {
		this.playermanager = new HashMap<UUID, PlayerManager>();
		this.registeredGame = game;
		allowJoins = false;
		if (getGameManager().canceledFireworks == false) {
			getGameManager().fireworksRunnable.cancel();
		}
		getGameManager().reset();
		this.registeredGame.runPreEvents();
		gs.reset();
		gs.addScore(color("&cWaiting for players..."));
	}

	private void loadConfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void parseConfig(){
		this.playerCountMsg = getConfig().getString("messages.playerCount");
		this.coinGainMsg = getConfig().getString("messages.coingain");
		this.killMessages = getConfig().getStringList("messages.killMessage");
		this.deathMessages = getConfig().getStringList("messages.deathMessage");
		this.countdownMsg = getConfig().getString("messages.countdown");

		this.host = getConfig().getString("mysql.host");
		this.port = getConfig().getInt("mysql.port");
		this.database = getConfig().getString("mysql.database");
		this.username = getConfig().getString("mysql.username");
		this.password = getConfig().getString("mysql.password");

		this.kitSelector = ItemStackSerializer.deserialize(getConfig().getString("items.kitselector"));
		this.hubSelector = ItemStackSerializer.deserialize(getConfig().getString("items.hub"));

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
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(color(msg));
		}

	}

	public void sendActionbar(Player p, String message) {
		net.minecraft.server.v1_8_R3.IChatBaseComponent icbc = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
				ChatColor.translateAlternateColorCodes('&', message) + "\"}");
		net.minecraft.server.v1_8_R3.PacketPlayOutChat bar = new net.minecraft.server.v1_8_R3.PacketPlayOutChat(icbc, (byte)2);
		((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	}

	public boolean connectToBungeeServer(Player player, String server) {
		try {
			if (server.length() == 0) {
				player.kickPlayer("There was an issue sending you back to the hub. We are kicking you from this server.");
				Bukkit.getLogger().warning("FATAL ERROR: Could not send player back to the hub. Reason: Invalid server name");
				return false;
			}
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(byteArray);
			out.writeUTF("Connect");
			out.writeUTF(server);
			player.sendPluginMessage(this, "BungeeCord", byteArray.toByteArray());
		} catch (Exception ex) {
			Bukkit.getLogger().warning("Could not handle BungeeCord command from " + player.getName() + ": tried to connect to \"" + server + "\".");
			ex.printStackTrace();
			return false;
		}
		return true;
	}

}
