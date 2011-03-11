package com.luugiathuy.games.android.framework;

import javax.microedition.khronos.opengles.GL10;

public abstract class GameObject {
	
	/**
	 * Draw the object into screen
	 * @param gl
	 */
	public abstract void draw(GL10 gl);
	
	/**
	 * Update the object
	 * @param elapsedTime in seconds
	 */
	public abstract void update(float elapsedTime);
}
