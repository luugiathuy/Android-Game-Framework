package com.luugiathuy.games.android.framework.manager;

import com.luugiathuy.games.android.Bubble;
import com.luugiathuy.games.android.framework.GameObject;

public class GameObjectManager {
	
	// The unique instance of this class
	public static GameObjectManager instance = new GameObjectManager();
	
	// type of game objects
	public static final int BUBBLE = 0;
	
	/** Private constructor */
	private GameObjectManager() {
		
	}
	
	public GameObject createGameObject(int type) {
		GameObject newObj = null;
		switch (type) {
		case BUBBLE:
			newObj = new Bubble();
			break;
		default:
			newObj = new Bubble();
			break;
		}
		return newObj;
	}

}
