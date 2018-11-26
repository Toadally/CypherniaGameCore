package com.drewgifford;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.drewgifford.PlayerData.PlayerManager;
import com.drewgifford.event.EventsHandler;
import com.drewgifford.game.Game;
import com.drewgifford.game.GameManager;
import com.drewgifford.game.GameScoreboard;
import com.drewgifford.game.Kit;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class CypherniaMinigames extends JavaPlugin {

	public HashMap<UUID, PlayerManager> players = new HashMap<UUID, PlayerManager>();
	public boolean allowJoins = true;
	private GameManager gm;
	private GameScoreboard gs;
	private Scoreboard scoreboard;
	public List<UUID> playersQuit = new ArrayList<UUID>();
	public EventsHandler handler;
	public Game registeredGame;
	private List<Kit> kits = new ArrayList<Kit>();
	
	private static CypherniaMinigames plugin;

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

	public GameManager getGameManager(){
		return gm;
	}
	public GameScoreboard getScoreboardManager(){
		return gs;
	}

	public void onEnable(){
		plugin = this;
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Config.parseConfig(this);
		gm = new GameManager();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	public void openPlayerJoins(){
		allowJoins = true;
	}

	public void registerMinigame(Game game){

		this.registeredGame = game;

		this.handler = new EventsHandler();
		allowJoins = false;
		registeredGame.runPreEvents();
		gs = new GameScoreboard(this, game.getName());
		gs.reset();
		gs.addScore(Config.color("&cWaiting for players..."));

	}
	

	public static void sendPluginMessage(Player p, String data, String channel) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(channel);
		out.writeUTF(data);
		p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
	
	public static CypherniaMinigames getInstance() {
		return plugin;
	}

	public void reloadMinigame(Game game) {
		this.players = new HashMap<UUID, PlayerManager>();
		this.registeredGame = game;
		allowJoins = false;
		if (getGameManager().canceledFireworks == false) {
			getGameManager().fireworksRunnable.cancel();
		}
		getGameManager().reset();
		this.registeredGame.runPreEvents();
		gs.reset();
		gs.addScore(Config.color("&cWaiting for players..."));
	}
	public void broadcast(String msg){
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(Config.color(msg));
		}
	}

}
