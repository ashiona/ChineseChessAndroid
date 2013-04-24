package com.andrew.chinesechessandroid;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chessui.Chess;
import com.chessui.ChessUiUtil;
import com.chess.ChessBoard;
import com.chess.ChessGroup;
import com.chess.ChessRule;
import com.example.chinesechessandroid.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
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
import android.widget.Toast;

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
	private Context mActivityContext = null;
	private AssetManager mAssetManager = null;
	private ChessUiUtil chessUiUtil = null;
	
	private ViewGroup chessLayout = null;
	
	ChessGroup red = null;  //  @jve:decl-index=0:
	ChessGroup black = null;  //  @jve:decl-index=0:
	ChessBoard cb = null;  //  @jve:decl-index=0:
	private boolean RedPlay = false;
	private boolean isOver = false;
	public boolean isClick = false;
	
	private boolean newGameUiInited = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivityContext = this.getApplicationContext();
		mAssetManager = this.getAssets();
		

		setContentView(R.layout.activity_main);
		
		chessLayout = (ViewGroup)this.findViewById(R.id.main_chessbg_LinearLayout);
		
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

		//Initialize util class
		if(null==chessUiUtil){
			chessUiUtil = new ChessUiUtil();
		}
		//ChessUiUtil settings
		if(false==chessUiUtil.isInitilized()){
			View mainLayout = View.inflate(this.getApplicationContext(), R.layout.activity_main, null);
			ImageView bgImg = (ImageView) findViewById(R.id.main_background_imageView);
			chessUiUtil.initialize(mainLayout, bgImg);
		}
		
		/*  DEMO CODE: get related coordinate for chess image
		 * 	1. get px/dp for main (background)
		 * 	2. compute related coordinate (unit length)
		 * 	3. compute unit size for chess
		 */
//		float baseLeft = 0;
//		float baseTop = 0;
//		float unitWidth = 0;
//		float unitHeight = 0;
//		View mainLayout = View.inflate(this.getApplicationContext(), R.layout.activity_main, null);
//		ImageView bgImg = (ImageView) findViewById(R.id.main_background_imageView);
//		int[] location = new int[2];
//		bgImg.getLocationInWindow(location);
////		baseLeft = location[0];
////		baseTop = location[1];
//		unitWidth = (float) (bgImg.getWidth()*458/558/8);
////		unitHeight = (float) (bgImg.getHeight()*516/620/9);
//		unitHeight = unitWidth;
//		
//		//unit size for chess
//		int maxUnitLength = (int) (unitHeight+unitWidth)/2;
//		ImageView tImg = (ImageView) this.findViewById(R.id.imageView1);
//		tImg.setAdjustViewBounds(true);
//		tImg.setMaxHeight( (int) (maxUnitLength) );
//		tImg.setMaxWidth(  (int) (maxUnitLength) );
//		baseLeft = (float) (location[0]+bgImg.getWidth()*52/558-maxUnitLength/2);
//		baseTop = (float) (location[1]+bgImg.getHeight()*53/620+maxUnitLength/2);
//		tImg.setX(baseLeft+unitWidth*0);
//		tImg.setY(baseTop+unitHeight*9);

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
//		chessLayout.setBackgroundColor( Color.BLUE);
//		ImageView tImg2 = new ImageView(getApplicationContext());
//		addImageViewOnLayout(0f,0f,tImg2, chessLayout);
//		addImageViewOnLayout(100f,100f,new ImageView(getApplicationContext()), chessLayout)
//			.setImageResource(R.drawable.black_ju);

		if(red != null){
			ListIterator<Chess> lit = red.getChess().listIterator();
			while(lit.hasNext()){
				Chess c = lit.next();
				deleteImageViewOnLayout(c, chessLayout);
			}
			red = null;
		}
		if(black != null){
			ListIterator<Chess> lit = black.getChess().listIterator();
			while(lit.hasNext()){
				Chess c = lit.next();
				deleteImageViewOnLayout(c, chessLayout);
			}
			black = null;
		}
		if(cb != null){
			cb = null;
		}
		String[] str = readImageFolder("images");
//		for(int i=0; i<str.length; i++){
//			System.out.println(str[i]);
//		}
		red = new ChessGroup(mActivityContext, str, "red");
		drawChess(red);
		black = new ChessGroup(mActivityContext, str, "black");
		drawChess(black);
		cb = new ChessBoard(9,10);
		cb.SetPositionTaken(red);
		cb.SetPositionTaken(black);
		new ChessDestroy(red,black);
	}

	/**
	 * @deprecated
	 * @param x
	 * @param y
	 * @param childImageView
	 * @param parentLayout
	 * @return
	 */
	@SuppressWarnings("unused")
	private ImageView addImageViewOnLayout(float x, float y, ImageView childImageView,
			ViewGroup parentLayout) {
		childImageView.setImageResource(R.drawable.black_pao);
		childImageView.setX(x);
		childImageView.setY(y);
//		childImageView.setScaleX(0.8f);
//		childImageView.setScaleY(0.8f);
		parentLayout.addView(childImageView);
		return childImageView;
	}

	/**
	 * @deprecated
	 * @param coordinate Chess location x, y
	 * @param childImageView
	 * @param parentLayout
	 * @return
	 */
	@SuppressWarnings("unused")
	private ImageView addImageViewOnLayout(float[] coordinate, ImageView childImageView,
			ViewGroup parentLayout) {
		if(2!=coordinate.length){
			Log.e("addImageViewOnLayout", "Coordinate dimension not match!!");
			return null;
		}
//		childImageView.setImageResource(R.drawable.black_pao);
		childImageView.setX(coordinate[0]);
		childImageView.setY(coordinate[1]);
//		childImageView.setScaleX(0.8f);
//		childImageView.setScaleY(0.8f);
		parentLayout.addView(childImageView);
		return childImageView;
	}
	/**
	 * TODO: scale
	 * @param chess
	 * @param parentLayout
	 * @return
	 */
	private ImageView addImageViewOnLayout(Chess chess,
			ViewGroup parentLayout) {
		float[] coordinate = chessUiUtil.convertChessCoordinate(chess);
		if(2!=coordinate.length){
			Log.e("addImageViewOnLayout", "Coordinate dimension not match!!");
			return null;
		}
//		childImageView.setImageResource(R.drawable.black_pao);
		chess.setX(coordinate[0]);
		chess.setY(coordinate[1]);
//		childImageView.setScaleX(0.8f);
//		childImageView.setScaleY(0.8f);
		parentLayout.addView(chess);
		return chess;
	}
	
	private void deleteChessOnBoard(Chess c) {
		deleteImageViewOnLayout(c, chessLayout);
	}
	
	private void deleteImageViewOnLayout(ImageView childImageView,
			ViewGroup parentLayout) {
		parentLayout.removeView(childImageView);
	}
	
	/**
	 * TODO CLEANME
	 * @param url
	 * @return
	 */
	public String[] readImageFolder(String url){
		String tmp = new String();
		String[] str = null;
		String[] fileNames = null;
		//System.out.println("enter to here!");
		//Pattern p = Pattern.compile("[a-z]{3,5}-\\d{1,2}\\.gif");
		Pattern p = Pattern.compile("[a-z]{3,5}-[a-z]{2,5}.gif");
		HashSet<String> hs = new HashSet<String>();
		try {
			fileNames = mAssetManager.list(url);
//			for(String s:fileNames){
//				Log.d("readImageFolder", "fileNames "+s);
//			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		for(int i=0; i<fileNames.length; i++){
			tmp = fileNames[i];
			// System.out.println(tmp);
			Matcher m = p.matcher(tmp);
			if (m.matches()) {
				// hs.add(tmp.substring(0, tmp.length()-4));
				hs.add(tmp);
			}
		}
		if(str == null){
			str = new String[hs.size()];
			Iterator<String> it = hs.iterator();
			int j = 0;
			while(it.hasNext()){
				tmp = it.next();
				str[j] = new String(tmp);
				j++;
				//System.out.println(j);
			}
		}
		return str;
	}
	
	public void drawChess(ChessGroup cg){
		
		Iterator<Chess> it = cg.getChess().iterator();
		while(it.hasNext()){
			Chess c = it.next();
			addImageViewOnLayout(c, chessLayout);
		}
	}
	
	/**
	 * Inner class to handle chess destroy
	 */
	class ChessDestroy implements Runnable{
		private ChessGroup cg1 = null;
		private ChessGroup cg2 = null;
		ChessDestroy(ChessGroup cg1,ChessGroup cg2 ){
			this.cg1 = cg1;
			this.cg2 = cg2;
			(new Thread(this)).start();
		}
		@Override
		public void run() {
			while(!isOver){
			//if(!isOver){
				System.out.println("subthread running!");
				Chess c;
				if((c = red.findChess("red-ju.gif"))!=null){
					Log.d("initGame", "c.getName() "+c.getName());
					Log.d("initGame", "c.getX() "+c.getX()+" c.getY() "+c.getY());
					Log.d("initGame", "c.getWidth() "+c.getWidth()+" c.getHeight() "+c.getHeight());
				}
				
			// red chess been clicked first, and another chess been clicked
				cg1.CalIndex();
				cg2.CalIndex();
				if(cg1.getNumTaken()>1){
					cg1.disableChosen();
				}
				if(cg2.getNumTaken()>1){
					cg2.disableChosen();
				}
				if(cg1.getNumTaken()==1 && cg2.getNumTaken()==1){
					//Can not stop click black chess first. it will cause problem. need to be fixed later
					if(RedPlay == true){ // red chess eat black
						Chess r = cg1.getChess().get(cg1.getIndex());
						Chess b = cg2.getChess().get(cg2.getIndex());
						int x = b.getIndX();
						int y = b.getIndY();
						int old_x = r.getIndX();
						int old_y = r.getIndY();
						if(r.getRank().equals("pao")){
							ChessRule.cannonRule(cb, r, b);
						}else{
							ChessRule.AllRules(cb, r, x, y);
						}
						if(old_x != r.getIndX() || old_y != r.getIndY()){
							RedPlay = false;
							doMakeToast("Now is Black side's turn!");
							b.dead();
							deleteImageViewOnLayout(b, chessLayout);
						}
						
					}else{ // black chess eat red
						Chess r = cg1.getChess().get(cg1.getIndex());
						Chess b = cg2.getChess().get(cg2.getIndex());
						int x = r.getIndX();
						int y = r.getIndY();
						int old_x = b.getIndX();
						int old_y = b.getIndY();
						if(b.getRank().equals("pao")){
							ChessRule.cannonRule(cb, b, r);
						}else{
							ChessRule.AllRules(cb, b, x, y);
						}
						if(old_x != b.getIndX() || old_y != b.getIndY()){
							RedPlay = true;
							doMakeToast("Now is Red side's turn!");
							r.dead();
							deleteImageViewOnLayout(r, chessLayout);
						}
						
					}
				}
				if(!cg1.isAlive() || !cg2.isAlive()){
					isOver = true;
					System.out.println("Game over!");
					if(!cg1.isAlive()){
						doMakeToast("Red side LOSE, Game Over!");
						//TODO: Make game finish?
					}else{
						doMakeToast("Black side LOSE, Game Over!");
					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
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
	
	public void doMakeToast(String str){
		Toast.makeText(mActivityContext, str,
        	     Toast.LENGTH_SHORT).show();
	}
	
	public class NewGameOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			
			doMakeToast("Black side walks first.");
			initGame();
		}
	}
}
