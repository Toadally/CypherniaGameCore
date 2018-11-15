package com.drewgifford.game;

public interface Game {
	
	public String getName();
	public String getVersion();
	public String getDescription();
	
	public int getMinPlayers();
	public boolean singleLife();
	public void runPreEvents();
	public void startGame();
	public void runPostEvents();
	public boolean kitsEnabled();

}
