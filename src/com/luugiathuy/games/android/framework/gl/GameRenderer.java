package com.luugiathuy.games.android.framework.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.luugiathuy.games.android.framework.manager.GameViewManager;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class GameRenderer implements Renderer {
	
	public GameRenderer() {
		
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		// clear the color and depth buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		// draw the current game view
		GameViewManager.instance.draw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// set current viewport to the new size
		gl.glViewport(0, 0, width, height);
		
		// select projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// reset projection matrix
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0.0f, width, 0.0f, height);
		//gl.glOrthof(0.0f, width, 0.0f, height, 0.0f, 1.0f);
		// Calculate the aspect ratio of the window
//		GLU.gluPerspective(gl, 45.0f, (float) width / (float)height
//							, 0.1f, 100.f);
		
		// Select model view matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the model view matrix
		gl.glLoadIdentity();
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black (rgba)
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// enable smooth shading
		gl.glShadeModel(GL10.GL_SMOOTH);
		
		// depth buffer setup
		gl.glClearDepthf(1.0f);
		// enable depth testing
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// the type of depth testing
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		// Enable blend
		gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(1.f, 1.f, 1.f, 1.f);
		
		// enable texture 2d
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		/*
         * By default, OpenGL enables features that improve quality but reduce
         * performance. One might want to tweak that especially on software
         * renderer.
         */
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);
		
		// Some one-time OpenGL initialization can be made here probably based
        // on features of this particular context
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		
	}

}
