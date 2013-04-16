package com.example.chinesechessandroid;

import com.example.chinesechessandroid.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		initGame();
	}
	
	private void initGame(){
		/* TODO:get related coordinate for chess image
		 * 	1. get px/dp for main (background)
		 * 	2. compute related coordinate (unit length)
		 * 	3. compute unit size for chess
		 */
		
		//TODO:get all chess resources
		
		//TODO:scale chess image to correct size
		
		//TODO:put all chess in position
		
		/*TODO:add on click listener on chess??
		 *  S1: use coordinate computing? need extra thread?
		 *  S2: use image button or click listener? may have large bound
		 */
	}
}
