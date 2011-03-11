package com.luugiathuy.games.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.luugiathuy.games.android.framework.GameObject;
import com.luugiathuy.games.android.framework.manager.GameViewManager;

public class Bubble extends GameObject{
	// The vertices to draw circle
	private float[] vertices = new float[720];
	// Our vertex buffer.
	private FloatBuffer vertexBuffer;
	
	private float mRadius;
	
	private float mRed;
	private float mGreen;
	private float mBlue;
	
	private float mPosX;
	private float mPosY;
	
	private int mVelX;
	private int mVelY;
	
	// Constants
	private static final int MIN_RADIUS = 10;
	private static final int MAX_RARIUS = 50;
	private static final int MAX_VELOCITY = 200;
	private static final float ALPHA_VALUE = 0.5f;

	public Bubble() {
		mRadius = MIN_RADIUS + (float)(Math.random() * (MAX_RARIUS-MIN_RADIUS));
		
		for (int i = 0; i < 720; i += 2) {
		    // x value
		    vertices[i]   = (float)Math.cos(degreesToRadian(i)) * mRadius;
		    // y value
		    vertices[i+1] = (float)Math.sin(degreesToRadian(i)) * mRadius;
		}
		
		mRed = (float)Math.random();
		mGreen = (float)Math.random();
		mBlue = (float)Math.random();
		
		mPosX = (float)(Math.random() * GameViewManager.sScreenWidth);
		mPosY = (float)(Math.random() * GameViewManager.sScreenHeight);
		
		mVelX = (int)(Math.random() * 2 * MAX_VELOCITY) - MAX_VELOCITY;
		mVelY = (int)(Math.random() * 2 * MAX_VELOCITY) - MAX_VELOCITY;	
		
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}

	/**
	 * This function draws our square on screen.
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK); // OpenGL docs

		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, // OpenGL docs
                                 vertexBuffer);

		gl.glPushMatrix();
		// Translates 4 units into the screen.
		gl.glTranslatef(mPosX, mPosY, 0.0f);
		gl.glColor4f(mRed, mGreen, mBlue, ALPHA_VALUE);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 360);
		gl.glPopMatrix();
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
	}

	@Override
	public void update(float elapsedTime) {
		int screenWidth = GameViewManager.sScreenWidth;
		int screenHeight = GameViewManager.sScreenHeight;
		
		mPosX += elapsedTime * mVelX;
		mPosY += elapsedTime * mVelY;
		
		if (mPosX - mRadius < 0) {
			mPosX = mRadius + (mRadius - mPosX);
			mVelX = -mVelX;
		}
		
		if (mPosY - mRadius < 0) {
			mPosY = mRadius + (mRadius - mPosY);
			mVelY = -mVelY;
		}
		
		if (mPosX + mRadius > screenWidth) {
			mPosX = screenWidth - mRadius -(mPosX + mRadius - screenWidth);
			mVelX = -mVelX;
		}
		
		if (mPosY + mRadius > screenHeight) {
			mPosY = screenHeight - mRadius - (mPosY + mRadius - screenHeight);
			mVelY = -mVelY;
		}
	}
	
	private float degreesToRadian(int degree) {
		return (3.14159265358979323846f * degree / 180.0f);
	}

}
