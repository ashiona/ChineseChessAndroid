package com.chess;

import java.util.ArrayList;
import java.util.ListIterator;

import com.chessui.Chess;

public class ChessBoard {
	private int width;
	private int height;
	private ArrayList<ChessPoint> chessPoints = null;
	//private int index; 
	
	public ChessBoard(int width, int height){
		this.width = width;
		this.height = height;
		//this.index = -1;
		this.chessPoints = new ArrayList<ChessPoint>();
		for(int i=0; i<this.width; i++){
			for(int j=0; j<this.height; j++){
			ChessPoint cp = new ChessPoint(i,j);
			this.chessPoints.add(cp);
			}
		}
	}
	
	public ArrayList<ChessPoint> getChessPoints(){
		return this.chessPoints;
	}
	
	public void SetPositionTaken(ChessGroup cg){
		ListIterator<ChessPoint> lit = this.chessPoints.listIterator();
		while(lit.hasNext()){
			ChessPoint cp = lit.next();
			ListIterator<Chess> ic = cg.getChess().listIterator();
			while(ic.hasNext()){
				Chess c = ic.next();
				if(cp.getX() == c.getIndX() && cp.getY() == c.getIndY()){
					cp.setTaken(true);
					break;
				}
			}
		}
	}
	
	// used to return the index in the ChessBoard
	public int FindByIndex(int index_x, int index_y){
		ListIterator<ChessPoint> lit = this.chessPoints.listIterator();
		int index = -1;
		while(lit.hasNext()){
			ChessPoint cp = lit.next();
			if(cp.getX() == index_x && cp.getY() == index_y){
				index = this.chessPoints.indexOf(cp);
				break;
			}
		}
		return index;
	}
	// used to return if the position is taken or not
	public boolean isTaken(int index_x, int index_y){
		boolean flag = false;
		int index = FindByIndex(index_x, index_y);
		if(index == -1){
			System.out.println("index can not be -1");
			System.exit(-1);
		}else{ // find the specific position
			ChessPoint cp = (ChessPoint)this.chessPoints.get(index);
			flag = cp.isTaken();
		}
		return flag;
	}
	// used to change the position status
	public void ChangeTaken(Chess c){
		int old_indx = FindByIndex(c.getPre_x(),c.getPre_y());
		int new_indx = FindByIndex(c.getIndX(),c.getIndY());
		if(old_indx!=-1 && new_indx!=-1){
			ChessPoint cpold = this.chessPoints.get(old_indx);
			cpold.setTaken(false);
			ChessPoint cpnew = this.chessPoints.get(new_indx);
			cpnew.setTaken(true);	
		}else{
			System.out.println("Wrong here!");
			System.exit(-1);
		}
	}
	
	class ChessPoint{
		private int x = 0;
		private int y = 0;
		private boolean isTaken = false;
		
		ChessPoint(){
			super();
		}
		
		ChessPoint(int x, int y){
			this.x = x;
			this.y = y;
			this.isTaken = false;
		}
		
		ChessPoint(int x, int y, boolean isTaken){
			this(x,y);
			this.isTaken = isTaken;
		}
		
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}




		public boolean isTaken() {
			return isTaken;
		}

		public void setTaken(boolean isTaken) {
			this.isTaken = isTaken;
		}
		
		
	}
}
