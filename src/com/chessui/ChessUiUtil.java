package com.chessui;

import java.util.Arrays;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ChessUiUtil {
	boolean isInitilized = false;
	
	ViewGroup mainLayout = null;
	ImageView bgImg = null;

	float baseLeft = 0;
	float baseTop = 0;
	float unitWidth = 0;
	float unitHeight = 0;
	int maxUnitLength = 0;
	int[] location = new int[2];
	
	public void initialize(ViewGroup mainLayout, ImageView bgImg){
		
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
		unitHeight = (float) (bgImg.getHeight()*505/620/9);
//		unitHeight = unitWidth;
		
		maxUnitLength = (int) (bgImg.getLeft()==0?unitWidth:unitHeight);
		

		baseLeft = (float) (location[0]+maxUnitLength);
		baseTop = (float) (location[1]+maxUnitLength);
		
		this.isInitilized = true;

		Log.d("ChessUiUtil", "location "+location[0]+" "+location[1]);
		Log.d("ChessUiUtil", "UnitLength "+unitHeight+" "+unitWidth);
	}
	
	public boolean isInitilized() {
		return isInitilized;
	}

	/**
	 * FIXME
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
//		c.UNIT = maxUnitLength;

		float[] coordinate = new float[2];
		coordinate[0]=baseLeft+maxUnitLength*c.getIndX()-maxUnitLength/2;
		coordinate[1]=baseTop+maxUnitLength*c.getIndY();//+maxUnitLength/2;
		c.setX(coordinate[0]);
		c.setY(coordinate[1]);
		
		return coordinate;
	}
	/**
	 * TODO
	 * @param mx
	 * @param my
	 * @return
	 */
	public int[] convertBoardCoordinateFromWindowXY(float mx, float my) {
		if (false == isInitilized) {
			Log.w("ChessUiUtil", "ChessUiUtil NOT initialize!!");
		}

		int[] back = { 0, 0 };
		int range = (int) (maxUnitLength * 0.8);
		int index_x = -1;
		int index_y = -1;
		int start_x = (int) baseLeft;
		int start_y = (int) baseTop;
		int reminder_x = (int) ((mx - location[0] - bgImg.getWidth() * 52 / 558) % maxUnitLength);
		int reminder_y = (int) ((my - location[1] - bgImg.getHeight() * 53 / 620) % maxUnitLength);
		if (mx < (start_x - range)
				|| mx > (start_x + maxUnitLength * 8 + range)
				|| my < (start_y - range)
				|| my > (start_y + maxUnitLength * 9 + range)) {
			back[0] = -1;
			back[1] = -1;
			return back;
		}
		// only response in 10 X 10 local area
		if ((reminder_x > range && reminder_x < (maxUnitLength - range))
				|| (reminder_y > range && reminder_y < (maxUnitLength - range))) {
			// don't response
			;
		} else {
			if (reminder_x <= range) {
				index_x = (int) ((mx - start_x) / maxUnitLength);
			} else if (reminder_x >= (maxUnitLength - range)) {
				index_x = (int) ((mx - start_x + range) / maxUnitLength);
			}
			if (reminder_y <= range) {
				index_y = (int) ((my - start_y) / maxUnitLength);
			} else if (reminder_y >= (maxUnitLength - range)) {
				index_y = (int) ((my - start_y + range) / maxUnitLength);
			}
		}
		back[0] = index_x;
		back[1] = index_y;
//		Log.w("ChessUiUtil", toString());
//		Log.w("ChessUiUtil", bgImg.getBottom() + " " + bgImg.getTop());
//		Log.w("ChessUiUtil", "convertBoardCoordinate " + mx + " " + my + " > "
//				+ back[0] + " " + back[1]);
		return back;
	}

	@Override
	public String toString() {
		return "ChessUiUtil [baseLeft=" + baseLeft + ", baseTop=" + baseTop
				+ ", unitWidth=" + unitWidth + ", unitHeight=" + unitHeight
				+ ", maxUnitLength=" + maxUnitLength + ", location="
				+ Arrays.toString(location) + "]";
	}
	
	public void doUpdateChessPosition(Chess chess){
		float[] coordinate = convertChessCoordinate(chess);
		if(2!=coordinate.length){
			Log.e("addImageViewOnLayout", "Coordinate dimension not match!!");
		}
		chess.setX(coordinate[0]);
		chess.setY(coordinate[1]);
//		((ViewGroup) mainLayout).addView(chess);
	}
	

	public void doDeleteChessPosition(Chess chess){
		((ViewGroup) mainLayout).removeView(chess);
	}

}
