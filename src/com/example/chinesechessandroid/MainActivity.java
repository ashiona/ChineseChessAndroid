package com.example.chinesechessandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @author Andrew
 *
 */
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
	private boolean newGameUiInited = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		//Set listener for buttons
		View newGameBtn = findViewById(R.id.main_start_button);
		newGameBtn.setOnClickListener(new NewGameOnClickListener());
		
	}

	/** WORKING CLEANME
	 * Initialize chess in position, make retract button enable after step?
	 *
	 * @return true if success
	 */
	private boolean initGame(){
		boolean result = false;
		float baseLeft = 0;
		float baseTop = 0;
		float unitWidth = 0;
		float unitHeight = 0;
		/* TODO:get related coordinate for chess image
		 * 	1. get px/dp for main (background)
		 * 	2. compute related coordinate (unit length)
		 * 	3. compute unit size for chess
		 */
//		View mainLayout = View.inflate(this.getApplicationContext(), R.layout.activity_main, null);
		ImageView bgImg = (ImageView) findViewById(R.id.main_background_imageView);
		baseLeft = bgImg.getPaddingLeft();
		baseTop = bgImg.getPaddingTop();
		unitWidth = bgImg.getMeasuredWidth()/10;
		unitHeight = bgImg.getMeasuredHeight()/11;
		
		//TODO:TEST
		ImageView tImg = (ImageView) this.findViewById(R.id.imageView1);
		tImg.setMaxHeight((int) unitHeight);
		tImg.setX(baseLeft+unitWidth);
		tImg.setTop(12);
		Log.d("initGame", "img "+baseLeft+" "+baseTop+" "+unitWidth+" "+unitHeight+" "+bgImg.getMeasuredWidth());
		Log.d("initGame", ""+bgImg.getTranslationY());
		//TODO:get all chess resources
		
		//TODO:scale chess image to correct size
		
		//TODO:put all chess in position
		
		/*TODO:add on click listener on chess??
		 *  S1: use coordinate computing? need extra thread?
		 *  S2: use image button or click listener? may have large bound
		 */
		return result;
	}
	
	/**
	 * @deprecated
	 */
	public class initGameUIThread implements Runnable{

		@Override
		public void run() {
			while(false==newGameUiInited){
				if(true==initGame()){
					newGameUiInited=true;
					Log.d("initGameUIThread", "Init success");
				}
			}
		}
		
	}
	
	
	public class NewGameOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			initGame();
		}
	}
}
