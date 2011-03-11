package com.luugiathuy.games.android.framework.manager;

import javax.microedition.khronos.opengles.GL10;

import com.luugiathuy.games.android.view.GameView;

public class GameViewManager {
	
	public static GameViewManager instance = new GameViewManager();
	
	public static int sScreenWidth;
	public static int sScreenHeight;
	
	private GameView mIngameView;
	
	private GameViewManager(){
	}

	
	public void setGameView(GameView ingameView){
		mIngameView = ingameView;
	}
	
	public void draw(GL10 gl){
		mIngameView.draw(gl);
	} 
}
