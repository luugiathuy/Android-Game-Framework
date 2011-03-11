package com.luugiathuy.games.android.framework.manager;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class InputManager {
	
	public static InputManager instance = new InputManager();
	
	private static final int m_NUM_KEYS = 256;
	
	public boolean isTrackballUsed;
	
	public MotionEvent motionEvent;
	
	public boolean[] currKeyState = new boolean[m_NUM_KEYS];
	
	private InputManager(){
		
	}
	
	public void update() {
		isTrackballUsed = false;
	}
	
	public boolean onTrackballEvent(MotionEvent event) {
		motionEvent = event;
		isTrackballUsed = true;
		return true;
	}
	
	public boolean OnKeyUp(int keyCode, KeyEvent event)
	{
		currKeyState[keyCode] = false;
		return true;
	}
	
	public boolean OnKeyDown(int keyCode, KeyEvent event) {
		currKeyState[keyCode] = true;
		return true;
	}
}