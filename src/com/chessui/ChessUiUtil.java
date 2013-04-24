package com.chessui;

import com.example.chinesechessandroid.R;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ChessUiUtil {
	boolean isInitilized = false;
	
	View mainLayout = null;
	ImageView bgImg = null;

	float baseLeft = 0;
	float baseTop = 0;
	float unitWidth = 0;
	float unitHeight = 0;
	int maxUnitLength = 0;
	int[] location = new int[2];
	
	public void initialize(View mainLayout, ImageView bgImg){
		
		this.mainLayout = mainLayout;
		this.bgImg = bgImg;
		//get parent location
//		bgImg.getLocationOnScreen(location);
		bgImg.getLocationInWindow(location);
//		location[0] = bgImg.getLeft();
//		location[1] = bgImg.getTop();
		
		
//		baseLeft = location[0];
//		baseTop = location[1];
		unitWidth = (float) (bgImg.getWidth()*458/558/8);
//		unitHeight = (float) (bgImg.getHeight()*516/620/9);
		unitHeight = unitWidth;
		
		maxUnitLength = (int) (unitHeight+unitWidth)/2;
		this.isInitilized = true;

		Log.d("ChessUiUtil", "location "+location[0]+" "+location[1]);
		Log.d("ChessUiUtil", "UnitLength "+unitHeight+" "+unitWidth);
	}
	
	public boolean isInitilized() {
		return isInitilized;
	}

	/**
	 * TODO
	 * @param ch
	 * @return
	 */
	public float[] convertChessCoordinate(Chess c) {
		if(false==isInitilized){
			Log.w("ChessUiUtil", "ChessUiUtil NOT initialize!!");
		}
		
		c.setAdjustViewBounds(true);
		c.setMaxHeight( (int) (maxUnitLength) );
		c.setMaxWidth(  (int) (maxUnitLength) );
		c.setMinimumHeight( (int) (maxUnitLength) );
		c.setMinimumWidth(  (int) (maxUnitLength) );
		baseLeft = (float) (location[0]+bgImg.getWidth()*52/558-maxUnitLength/2);
		baseTop = (float) (location[1]+bgImg.getHeight()*53/620-maxUnitLength/2);

		float[] coordinate = new float[2];
		coordinate[0]=baseLeft+unitWidth*c.getIndX();
		coordinate[1]=baseTop+unitHeight*c.getIndY();
		c.setX(coordinate[0]);
		c.setY(coordinate[1]);
		
		return coordinate;
	}

}
