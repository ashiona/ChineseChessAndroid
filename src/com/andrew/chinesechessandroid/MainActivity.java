package com.andrew.chinesechessandroid;

import com.example.chinesechessandroid.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
		View mainLayout = View.inflate(this.getApplicationContext(), R.layout.activity_main, null);
		ImageView bgImg = (ImageView) findViewById(R.id.main_background_imageView);
		int[] location = new int[2];
		bgImg.getLocationInWindow(location);
//		baseLeft = location[0];
//		baseTop = location[1];
		unitWidth = (float) (bgImg.getWidth()*458/558/8);
//		unitHeight = (float) (bgImg.getHeight()*516/620/9);
		unitHeight = unitWidth;
		
		//TODO:TEST
		int maxUnitLength = (int) (unitHeight+unitWidth)/2;
		ImageView tImg = (ImageView) this.findViewById(R.id.imageView1);
		tImg.setAdjustViewBounds(true);
		tImg.setMaxHeight( (int) (maxUnitLength) );
		tImg.setMaxWidth(  (int) (maxUnitLength) );
		baseLeft = (float) (location[0]+bgImg.getWidth()*52/558-maxUnitLength/2);
		baseTop = (float) (location[1]+bgImg.getHeight()*53/620+maxUnitLength/2);
		tImg.setX(baseLeft+unitWidth*0);
		tImg.setY(baseTop+unitHeight*9);

//		Log.d("initGame", "tImg.getWidth() "+tImg.getWidth());
//		Log.d("initGame", "tImg.getHeight() "+tImg.getHeight());
//		Log.d("initGame", "bgImg.getHeight() "+bgImg.getHeight());
//		Log.d("initGame", "unitWidth "+unitWidth);
//		Log.d("initGame", "unitHeight "+unitHeight);
//		Log.d("initGame", "baseLeft "+baseLeft+" parentX "+location[0]);
//		Log.d("initGame", "baseTop "+baseTop+" parentY "+location[1]);
		
		loadChessToBoard();

		//TODO:get all chess resources
		
		//TODO:scale chess image to correct size
		
		//TODO:put all chess in position
		
		/*TODO:add on click listener on chess??
		 *  S1: use coordinate computing? need extra thread?
		 *  S2: use image button or click listener? may have large bound
		 */
		return result;
	}

	private void loadChessToBoard() {
		//TODO:loadChessToBoard
		ViewGroup chessLayout = (ViewGroup)this.findViewById(R.id.main_chessbg_LinearLayout);
//		chessLayout.setBackgroundColor( Color.BLUE);
		ImageView tImg2 = new ImageView(getApplicationContext());
		addImageViewToLayout(0f,0f,tImg2, chessLayout);
		addImageViewToLayout(100f,100f,new ImageView(getApplicationContext()), chessLayout)
			.setImageResource(R.drawable.black_ju);
	}

	/**
	 * @param x
	 * @param y
	 * @param childImageView
	 * @param parentLayout
	 * @return
	 */
	private ImageView addImageViewToLayout(float x, float y, ImageView childImageView,
			ViewGroup parentLayout) {
		childImageView.setImageResource(R.drawable.black_pao);
		childImageView.setX(x);
		childImageView.setY(y);
		childImageView.setScaleX(0.8f);
		childImageView.setScaleY(0.8f);
		parentLayout.addView(childImageView);
		return childImageView;
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
