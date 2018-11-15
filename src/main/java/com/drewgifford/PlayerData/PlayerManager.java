package com.drewgifford.PlayerData;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.drewgifford.Config;
import com.drewgifford.CypherniaMinigames;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerManager {

	private UUID uuid;
	private boolean ingame;
	private boolean isdead;

	private int coins = 0;

	private CypherniaMinigames plugin;

	public PlayerManager(UUID uuid, boolean ingame, boolean isdead, CypherniaMinigames plugin){
		this.setUuid(uuid);
		this.setIngame(ingame);
		this.setDead(isdead);
		this.plugin = plugin;
		this.updateCoins();
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

	public void addCoins(int coins) {
		Bukkit.getPlayer(this.uuid).sendMessage(Config.coinGainMsg.replaceAll("%coins%", "" + coins));
		this.coins += coins;
		this.updateCoins();
	}
	private void updateCoins() {
		this.plugin.getConfig().set("coins." + this.uuid.toString(), this.coins);
	}

	public void sendActionbar(Player p, String message) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}

	public boolean connectToBungeeServer(String server) {
		Player player = Bukkit.getPlayer(this.uuid);
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
			player.sendPluginMessage(this.plugin, "BungeeCord", byteArray.toByteArray());
		} catch (Exception ex) {
			Bukkit.getLogger().warning("Could not handle BungeeCord command from " + player.getName() + ": tried to connect to \"" + server + "\".");
			ex.printStackTrace();
			return false;
		}
		return true;
	}

}
