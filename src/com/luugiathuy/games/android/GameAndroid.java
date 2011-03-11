package com.luugiathuy.games.android;


import com.luugiathuy.games.android.framework.manager.GameViewManager;
import com.luugiathuy.games.android.view.GameView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class GameAndroid extends Activity {
    
	// GLSurfaceView
	private GameView mIngameView;
	
	public static DisplayMetrics dm = new DisplayMetrics();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        mIngameView = new GameView(this, null);
        GameViewManager.instance.setGameView(mIngameView);
        setContentView(mIngameView);
    }
}