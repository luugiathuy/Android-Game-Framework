package com.luugiathuy.games.android.view;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.luugiathuy.games.android.framework.GameObject;
import com.luugiathuy.games.android.framework.gl.GameRenderer;
import com.luugiathuy.games.android.framework.manager.GameObjectManager;
import com.luugiathuy.games.android.framework.manager.GameViewManager;
import com.luugiathuy.games.android.framework.manager.InputManager;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameView extends GLSurfaceView implements SurfaceHolder.Callback{

	/** The logic thread handles updating */
	protected IngameThread mThread;
	
	/** List of game objects in the view */
	protected ArrayList<GameObject> mGameObjectList;
	
	private static final int NUM_BUBBLES = 30;
	
	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		// create renderer
		GameRenderer gameRenderer = new GameRenderer();
		setRenderer(gameRenderer);

		// create thread that handle updating
		mThread = new IngameThread(holder, context, null);
		
		// set focus to this view
		setFocusable(true);
	}
	
	/**
	 * Initialize objects for the view
	 */
	protected void initObject() {
		mGameObjectList = new ArrayList<GameObject>();
		
		for (int i=0; i<NUM_BUBBLES; ++i) {
			mGameObjectList.add(GameObjectManager.instance.createGameObject(GameObjectManager.BUBBLE));
		}
	}
	
	
	/**
	 * Draw this view to the screen
	 * @param gl
	 */
	public void draw(GL10 gl){
		for (GameObject gameObj : mGameObjectList) {
			gameObj.draw(gl);
		}
	}
	
	/**
     * Fetches the animation thread corresponding to this view.
     * @return the animation thread
     */
    public IngameThread getThread() {
        return mThread;
    }
    
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
    	InputManager.instance.onTrackballEvent(event);
    	return super.onTrackballEvent(event);
    }
    
    
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
    	if (!hasWindowFocus)
    		mThread.pause();
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	InputManager.instance.OnKeyUp(keyCode, event);
    	return super.onKeyUp(keyCode, event);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	InputManager.instance.OnKeyDown(keyCode, event);
    	return super.onKeyDown(keyCode, event);
    }
    
    
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		// create game object
        initObject();
        
		mThread.setIsRunning(true);
		mThread.start();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		super.surfaceChanged(holder, format, width, height);
		mThread.setSurfaceSize(width, height);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
		
		// we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        mThread.setIsRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}
	
	//----------------------------------------------------------
	
	class IngameThread extends Thread {
		
	    private long mLastTime;
	    
	    private boolean mIsRunning = false;
	    private boolean mIsPaused;
	    
	    
	    /** Handle to the surface manager object we interact with */
	    private SurfaceHolder mSurfaceHolder;
	    
	        
	    public IngameThread(SurfaceHolder surfaceHolder, Context context,
	            Handler handler){
	    	
	    	mSurfaceHolder = surfaceHolder;
	    }
	    
	    
	    /**
	     * Set whether this thread is running
	     * @param value
	     */
	    public void setIsRunning(boolean value){
	    	mIsRunning = value;
	    }   

		/**
	     * Pause the update
	     */
	    public void pause() {
	    	synchronized (mSurfaceHolder) {
	            if (mIsRunning) 
	            	mIsPaused = true;
	        }
	    }
	    
	    /**
	     * Resume the update
	     */
	    public void unpause() {
	    	synchronized (mSurfaceHolder) {
	            if (mIsRunning)
	            {
	            	// Move the real time clock up to now
	            	mLastTime = System.currentTimeMillis() + 100;
	            	mIsPaused = false;
	            }
	        }
	    }
	    
	    @Override
	    public void run() {
			while (mIsRunning) {
				while (mIsPaused && mIsRunning) {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						
					}
				}
				update();
				
				try {
					sleep((long)((1.f / 60.f) * 1000.f));
				} catch (InterruptedException e) {
					
				}
			}
	    }
	    
	    private void update() {
	        // Perform a single simulation step.
            final long time = SystemClock.uptimeMillis();
            final long elapsedTimeMili = time - mLastTime;
            final float elapsedGameTime = 
                mLastTime > 0.0f ? elapsedTimeMili / 1000.0f : 0.0f;
            mLastTime = time;
            updateState();
            updateAI();
            //PhysicsManager.instance.update(elapsedGameTime);
            
            for (GameObject obj : mGameObjectList) {
            	obj.update(elapsedGameTime);
            }
            
	        InputManager.instance.update();
	    }
	    
	    private void updateState(){}
	    private void updateAI() {}
	    
	    /**
	     * Callback invoke when the surface change size
	     * @param width
	     * @param height
	     */
	    public void setSurfaceSize(int width, int height) {
	        GameViewManager.sScreenWidth = width;
	        GameViewManager.sScreenHeight = height;
	    }
	}
	//----------------------------------------------------------

}
