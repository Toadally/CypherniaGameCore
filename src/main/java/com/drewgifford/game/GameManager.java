package com.drewgifford.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.drewgifford.CypherniaMinigames;
import org.bukkit.scoreboard.ScoreboardManager;

public class GameManager {

	CypherniaMinigames pl;

	public GameManager(CypherniaMinigames pl){
		this.pl = pl;
	}

	private int lobbyCountdown = 20;
	private Integer taskId;
	private boolean cancel;
	private String cancelMsg;


	public boolean spectatorEnabled = true;

	public void setSpectatorEnabled(boolean b){
		this.spectatorEnabled = b;
	}

	public void endgameCheck(){
		int trues = 0;
		Player winner = null;
		for(Player p : ingamePlayers.keySet()){
			if(isInGame(p)) {
				trues++;
				winner = p;
			}
		}
		if(trues == 1){
			if(winner != null) {
				finishGame(winner);
			}
		}
	}

	public List<Player> getIngamePlayers(){
		List<Player> igp = new ArrayList<Player>();

		for(Player p : ingamePlayers.keySet()){
			if(isInGame(p)){
				igp.add(p);
			}
		}
		return igp;


	}

	public boolean countdownStarted = false;

	private HashMap<Player, Boolean> ingamePlayers = new HashMap<Player, Boolean>();
	public boolean isInGame(Player p){
		return ingamePlayers.get(p);
	}
	public void setIngame(Player p, boolean d){
		unregisterPlayer(p);
		ingamePlayers.put(p, d);
	}
	public void unregisterPlayer(Player p ){
		ingamePlayers.remove(p);
	}


	public void lobbyCheck(){

		int playerCount = Bukkit.getServer().getOnlinePlayers().size();

		if(countdownStarted){
			if(playerCount < pl.playersNeeded){
				cancelCountdown(pl.notEnoughPlayersMsg);
			}
		} else {
			if(playerCount >= pl.playersNeeded){
				startLobbyCountdown();
			}
		}

	}

	public void cancelCountdown(String msg){
		if(taskId != null) {
			if(msg != "" && msg != null) {
				pl.broadcast(msg);
			}
			cancel = true;
			cancelMsg = msg;
			countdownStarted = false;
			pl.getScoreboardManager().reset();
            pl.getScoreboardManager().addScore("&cWaiting for players...");
		}
	}


	public void registerKill(Player killer, Player victim){

	}


	int countdownTaskId = -1;
	public void startLobbyCountdown(){
		if(countdownStarted) return;
		countdownStarted = true;

		pl.getScoreboardManager().reset();

		// This won't cancel for me -Toad

		new BukkitRunnable(){
			@Override
			public void run(){


				if(cancel == true){
					pl.broadcast(cancelMsg);
					cancel = false;
					cancelTask(countdownTaskId);
					return;
				}

				for(Player p : Bukkit.getOnlinePlayers()){
					p.setLevel(lobbyCountdown);

				}
				if(lobbyCountdown > 0){
					pl.getScoreboardManager().removeScore(pl.color("&eGame starting in &f"+(lobbyCountdown+1)));
                    pl.getScoreboardManager().addScore(pl.color("&eGame starting in &f"+lobbyCountdown));

                }

				if(lobbyCountdown == 20 || lobbyCountdown == 10 || lobbyCountdown < 6 && lobbyCountdown > 0){
					for(Player p : Bukkit.getOnlinePlayers()){
						p.playSound(p.getLocation(), Sound.CLICK, 1f, 2f);
						p.sendMessage(pl.color(pl.countdownMsg.replaceAll("%time%", ""+lobbyCountdown)));
					}
				}

				if(lobbyCountdown == 0){
					pl.getScoreboardManager().reset();
					for(Player p : Bukkit.getOnlinePlayers()){
						setIngame(p, true);
						p.setLevel(0);
						p.playSound(p.getLocation(), Sound.CLICK, 1f, 2f);
						p.sendMessage(ChatColor.YELLOW + "GO!");
					}
					startGame(pl.registeredGame);
					//cancelTask(countdownTaskId);

					this.cancel();
				}


				lobbyCountdown--;
			}


		}.runTaskTimer(pl, 0l, 20l);
		//countdownTaskId = pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl, task, 0L, 20);


	}

	public void cancelTask(int id) {
		BukkitScheduler scheduler = pl.getServer().getScheduler();
		scheduler.cancelTasks(pl);
	}

	public void startGame(Game game){

		game.startGame();
		for(Player p : Bukkit.getOnlinePlayers()){

			if(Kit.getSelectedKit(p) != null){
				Kit k = Kit.getSelectedKit(p);
				for(ItemStack i : k.getItems()){

					p.getInventory().addItem(i);

				}
				p.updateInventory();
			}

		}

	}

	public void setSpectator(Player p ){
		p.setAllowFlight(true);
		p.setFlying(true);
		p.setGameMode(GameMode.SPECTATOR);
	}

	int fireworkCt = 10;

	int fireworkTaskId = -1;

	public void finishGame(final Player winner){

		Runnable task = new Runnable(){
			@Override
			public void run(){

				if(fireworkCt == 0){
					for(Player p : Bukkit.getOnlinePlayers()){

						p.kickPlayer("The game server is restarting.");
					};
					pl.registeredGame.runPostEvents();
					cancelTask(fireworkTaskId);
				}

				launchFirework(winner);



				fireworkCt--;

			}
		};
		fireworkTaskId = pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl, task, 0, 20);

	}

	public void launchFirework(Player p) {
		//Spawn the Firework, get the FireworkMeta.
		Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		//Our random generator
		Random r = new Random();

		//Get the type
		int rt = r.nextInt(4) + 1;
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) type = FireworkEffect.Type.BALL;
		if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
		if (rt == 3) type = FireworkEffect.Type.BURST;
		if (rt == 4) type = FireworkEffect.Type.CREEPER;
		if (rt == 5) type = FireworkEffect.Type.STAR;

		//Get our random colours
		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);

		//Create our effect with this
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

		//Then apply the effect to the meta
		fwm.addEffect(effect);

		//Generate some random power and set it
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		//Then apply this to our rocket
		fw.setFireworkMeta(fwm);
	}

	private Color getColor(int i) {
		Color c = null;
		if(i==1){
			c=Color.AQUA;
		}
		if(i==2){
			c=Color.BLACK;
		}
		if(i==3){
			c=Color.BLUE;
		}
		if(i==4){
			c=Color.FUCHSIA;
		}
		if(i==5){
			c=Color.GRAY;
		}
		if(i==6){
			c=Color.GREEN;
		}
		if(i==7){
			c=Color.LIME;
		}
		if(i==8){
			c=Color.MAROON;
		}
		if(i==9){
			c=Color.NAVY;
		}
		if(i==10){
			c=Color.OLIVE;
		}
		if(i==11){
			c=Color.ORANGE;
		}
		if(i==12){
			c=Color.PURPLE;
		}
		if(i==13){
			c=Color.RED;
		}
		if(i==14){
			c=Color.SILVER;
		}
		if(i==15){
			c=Color.TEAL;
		}
		if(i==16){
			c=Color.WHITE;
		}
		if(i==17){
			c=Color.YELLOW;
		}

		return c;
	}

	public void spreadPlayers(int minDistance, int maxRange){
		int x = 0;  // Marks the x coord of the centre of your map.
		int z = 0;  // Marks the y coord of the centre of your map.
		//int minDistance = 250;  // The minimum distance between players / teams.
		//int maxRange = 1500;  // The maximum range (applies to x and z coordinates.
		boolean respectTeams = false;  // Whether players in teams should be teleported to the same location (if applicable).
		String players = "@a";  // Here you specify a list of player names separated by spaces, or use commandblock specifiers.

		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.getServer().dispatchCommand(console, String.format("spreadplayers %d %d %d %d %b %s", x, z, minDistance, maxRange, respectTeams, players));

	}

}
