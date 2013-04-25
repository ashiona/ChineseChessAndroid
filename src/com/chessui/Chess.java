package com.chessui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.chinesechessandroid.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;


public class Chess extends ImageView{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static final String FOLDER_NAME = "images/";
	public static final int START_X = 25;
	public static final int START_Y = 25;
	public static final int SIZE = 55; //Chess size
	public static final int GAP = 27; // Chess gap
	public static int UNIT = 57; // Chess board unit size
	public enum Suits {Red, Black};
	
	private Context activityContext = null;
	private Handler mHandler=null;
	private AlphaAnimation alpha = null;
	
	private int id = 0;
	private String name = null;
	private String rank = null;
	private Suits suit = null;
	private int index_x = 0;
	private int index_y = 0;
	private Point location = null;
	private int pre_x = 0;
	private int pre_y = 0;
	private String url = null;
	private boolean isChosen = false;
	private boolean isAlive = true;
	//public boolean isMove = false;
	
	public Chess(String str, int x, int y, Context activityContext){
		super(activityContext);
		
		this.activityContext =  activityContext;
		this.mHandler =new Handler();  
		
		this.name = new String(str);
		//System.out.println(str);
		String seprator = new String("-");
		String[] sub = str.split(seprator);
		if(sub.length != 2){
			System.out.println("input strname has problem!");
			System.out.println(str);
			System.out.println(sub[0]);
			System.out.println(sub[1]);
			System.exit(-1);
		}
		//this.figure = Integer.parseInt(sub[1]);
		switch(name.charAt(0)){
		case 'b':
			this.suit = Suits.Black;
			break;
		case 'r':
			this.suit = Suits.Red;
			break;
		}
		this.index_x = x;
		this.index_y = y;
		//Point p = new Point(x,y);
		this.location = new Point();
		//this.preLocation = new Point();
		calPosition(this.index_x, this.index_y);
		this.pre_x = this.index_x;
		this.pre_y = this.index_y;
		this.url = new String("images/" + name);
		int id = name.indexOf('-');
		this.rank = new String(name.substring(id+1,name.length()-4));
//		System.out.println("url is:" + url);
//		System.out.println("name is:" + name);
//		System.out.println("id is:" + id);
//        System.out.println("chess has been initilized");
        
        //Set image resource
        AssetManager assets = activityContext.getResources().getAssets();
        InputStream buf;
		try {
			buf = new BufferedInputStream((assets.open(FOLDER_NAME+str)));
	        Bitmap bm = BitmapFactory.decodeStream(buf);
	        this.setImageBitmap(bm);
		} catch (IOException e) {
			Log.w("Chess", "Chess failed to load image resource!");
			e.printStackTrace();
		}
		
		//set on click listener
		this.setOnClickListener(new OnChessClickListener());
	}
	
	// Convert the matrix index to the real pixels
	public void calPosition(int index_x, int index_y){
		double tmp_x = index_x*UNIT + START_X;
		double tmp_y = index_y*UNIT + START_Y;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Suits getSuit() {
		return suit;
	}
	public void setSuit(Suits suit) {
		this.suit = suit;
	}
	
	public String getRank(){
		return rank;
	}
	public int getIndX(){
		return index_x;
	}
	public int getIndY(){
		return index_y;
	}
	public void setIndX(int index_x){
		this.index_x = index_x;
	}
	public void setIndY(int index_y){
		this.index_y = index_y;
	}
	
	public int getPre_x() {
		return pre_x;
	}
	public void setPre_x(int pre_x) {
		this.pre_x = pre_x;
	}
	public int getPre_y() {
		return pre_y;
	}
	public void setPre_y(int pre_y) {
		this.pre_y = pre_y;
	}
	public void draw(){
		
	}
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}

	public boolean isChosen() {
		return isChosen;
	}
	public void setChosen(boolean isChosen) {
		this.isChosen = isChosen;
		if(isChosen){
			//Blink
			alpha = new AlphaAnimation(1.0f, 0.1f);
		    alpha.setDuration(1000);
		    alpha.setRepeatCount(Animation.INFINITE); 
		    alpha.setRepeatMode(Animation.REVERSE); 
		    this.setAnimation(alpha);
		    alpha.startNow();
		    this.setVisibility(View.INVISIBLE);
		}else{
			if(null!=alpha){
				alpha.reset();
				alpha.cancel();
				this.setAnimation(null);
			    this.setVisibility(View.VISIBLE);
			}
		}
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
/*	public boolean isMove() {
		return isMove;
	}

	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}*/
	
	
	public String toString() {
			
			//write your code here!
			String tmp = null;
			tmp = (suit == Suits.Red)? new String("red")
				: (suit == Suits.Black)? new String("black")
				: null;
			
			double tmp_x = 0;
			double tmp_y = 0;
			
			return Double.valueOf(tmp_x).toString() + ":" + Double.valueOf(tmp_y) + ":" + tmp;
		}
	
	public void dead(){
		this.setChosen(false);
		this.setAlive(false);
		this.setVisibility(View.GONE);
//		this.setVisible(false);
		Log.w("Chess", getName()+" dead!");
	}
	/**
	 * Inner class. Used to respond mouse click
	 * @author Louis
	 *
	 */
	class OnChessClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Chess c = (Chess) v;
			if(isChosen)
				c.setChosen(false);
			else
				c.setChosen(true);
			System.out.println(c.getName());
			
			
		}
		
	}
//	class Moniter extends Mouse {
//		
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			Chess c = (Chess)e.getSource();
//			c.isChosen = true;
//			new Blink(c);
//			System.out.println(c.getId());
//			System.out.println(e.getX());
//			System.out.println(e.getY());
//		}
//
//		
//	}
}
