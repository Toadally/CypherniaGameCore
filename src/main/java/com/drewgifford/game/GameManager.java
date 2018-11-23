package com.drewgifford.game;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;

public class GameManager {

	public int lobbyCountdown;
	private boolean cancel = false;
	private String cancelMsg = "";
	int fireworkCt = 10;
	public boolean countdownStarted = false;
	public boolean canceledFireworks = false;
	public BukkitRunnable fireworksRunnable;

	public GameManager(){
		this.lobbyCountdown = Integer.valueOf(Config.countdownTime);
	}

	public void reset() {
		this.lobbyCountdown = Integer.valueOf(Config.countdownTime);
		this.cancel = false;
		this.cancelMsg = "";
		this.fireworkCt = 10;
		this.countdownStarted = false;
		for (UUID uuid : CypherniaMinigames.getInstance().players.keySet()) {
			CypherniaMinigames.getInstance().players.get(uuid).setIngame(false);
		}
		this.canceledFireworks = false;
	}

	public void endgameCheck(){
		int trues = 0;
		Player winner = null;
		for(UUID uuid : CypherniaMinigames.getInstance().players.keySet()){
			if(CypherniaMinigames.getInstance().players.get(uuid).isIngame()) {
				trues++;
				winner = Bukkit.getPlayer(uuid);
			}
		}
		if(trues == 1){
			if(winner != null) {
				finishGame(winner);
			}
		}
	}

	public void lobbyCheck(int playerCount){

		if(countdownStarted){
			if(playerCount < CypherniaMinigames.getInstance().getGame().getMinPlayers()){
				cancelCountdown(Config.notEnoughPlayersMsg);
			}
		} else {
			if(playerCount >= CypherniaMinigames.getInstance().getGame().getMinPlayers()) {
				startLobbyCountdown();
			}
		}

	}
	
	public boolean isInGame(Player player) {
		return CypherniaMinigames.getInstance().players.get(player.getUniqueId()).isIngame();
	}

	public int getInGamePlayers() {
		int ingamePlayers = 0;
		for (UUID uuid : CypherniaMinigames.getInstance().players.keySet()) {
			if (CypherniaMinigames.getInstance().players.get(uuid).isIngame()) {
				ingamePlayers++;
			}
		}
		return ingamePlayers;
	}
	
	public void cancelCountdown(String msg){
		if(msg != "" && msg != null) {
			CypherniaMinigames.getInstance().broadcast(msg);
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setExp(0);
		}
		cancel = true;
		cancelMsg = msg;
		countdownStarted = false;
		lobbyCountdown = CypherniaMinigames.getInstance().getConfig().getInt("countdown");
		CypherniaMinigames.getInstance().getScoreboardManager().reset();
		CypherniaMinigames.getInstance().getScoreboardManager().addScore("&cWaiting for players...");
	}


	public void startLobbyCountdown(){
		if(countdownStarted) return;
		countdownStarted = true;

		CypherniaMinigames.getInstance().getScoreboardManager().reset();

		new BukkitRunnable(){
			@Override
			public void run(){
				if(cancel == true){
					CypherniaMinigames.getInstance().broadcast(cancelMsg);
					cancel = false;
					this.cancel();
					return;
				}

				for(Player p : Bukkit.getOnlinePlayers()){
					p.setLevel(lobbyCountdown);

				}
				if(lobbyCountdown > 0){
					CypherniaMinigames.getInstance().getScoreboardManager().removeScore(Config.color("&eGame starting in &f"+(lobbyCountdown+1)));
					CypherniaMinigames.getInstance().getScoreboardManager().addScore(Config.color("&eGame starting in &f"+lobbyCountdown));

				}

				if(lobbyCountdown == 20 || lobbyCountdown == 10 || lobbyCountdown < 6 && lobbyCountdown > 0){
					for(Player p : Bukkit.getOnlinePlayers()){
						p.playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1f, 2f);
						p.sendMessage(Config.color(Config.countdownMsg.replaceAll("%time%", ""+lobbyCountdown)));
					}
				}

				if(lobbyCountdown == 0){
					CypherniaMinigames.getInstance().getScoreboardManager().reset();
					for(Player p : Bukkit.getOnlinePlayers()){
						CypherniaMinigames.getInstance().players.get(p.getUniqueId()).setIngame(true);
						p.setLevel(0);
						p.setFireTicks(0);
						p.playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1f, 2f);
						p.sendMessage(ChatColor.YELLOW + "GO!");
					}
					startGame(CypherniaMinigames.getInstance().registeredGame);

					this.cancel();
				}


				lobbyCountdown--;
			}


		}.runTaskTimer(CypherniaMinigames.getInstance(), 0l, 20l);


	}

	public void startGame(Game game){
		game.startGame();
		for(Player p : Bukkit.getOnlinePlayers()){
			p.getInventory().clear();
			p.updateInventory();
			if(Kit.getSelectedKit(p) != null){
				Kit k = Kit.getSelectedKit(p);
				for(ItemStack i : k.getItems()){

					p.getInventory().addItem(i);

				}
				p.updateInventory();
			}

		}

	}

	public void finishGame(final Player winner){

		if (Config.coinGainActive) {
			CypherniaMinigames.getInstance().players.get(winner.getUniqueId()).addCoins(Config.coinsPerWin);
		}

		fireworksRunnable = new BukkitRunnable(){
			@Override
			public void run(){

				if(fireworkCt == 0){
					for(Player p : Bukkit.getOnlinePlayers()){

						if (CypherniaMinigames.getInstance().getConfig().getBoolean("bungeecord.useBungee")) {

							CypherniaMinigames.getInstance().players.get(p.getUniqueId()).connectToBungeeServer(CypherniaMinigames.getInstance().getConfig().getString("bungeecord.fallback-server"));
						} else {
							p.kickPlayer("The game server is restarting.");
						}
					};
					CypherniaMinigames.getInstance().registeredGame.runPostEvents();
					canceledFireworks = true;
					this.cancel();
				}

				launchFirework(winner.getLocation());



				fireworkCt--;

			}
		};
		fireworksRunnable.runTaskTimer(CypherniaMinigames.getInstance(), 0, 20);

	}

	public void launchFirework(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		Random r = new Random();
		int rt = r.nextInt(4) + 1;
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) type = FireworkEffect.Type.BALL;
		if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
		if (rt == 3) type = FireworkEffect.Type.BURST;
		if (rt == 4) type = FireworkEffect.Type.CREEPER;
		if (rt == 5) type = FireworkEffect.Type.STAR;
		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(effect);
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);
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

}
