package com.chess;

import com.chessui.Chess;

/**
 * @author Andrew
 *
 */
public class ChessEvent {
	public Chess c1 = null;
	public Chess c2 = null;
	public Event event = null;
	public enum Event {MOVE, EAT};
	public int[] origPostion = new int[2];
	
	public ChessEvent(){
		
	}
	
	public void doInitMoveChessEvent(Chess c, int[] origPostion){
		this.c1 = c;
		this.origPostion = origPostion;
		this.event = Event.MOVE;
	}
	
	public void doInitEatChessEvent(Chess chessEat, Chess chessEaten, int[] origPostion){
		this.c1 = chessEat;
		this.c2 = chessEaten;
		this.origPostion = origPostion;
		this.event = Event.EAT;
	}
	
//	public void doUpdateChess()
	
	public void doRecoveryEventToChess(){
		switch (this.event) {
		case EAT:
			this.c1.setIndX(this.origPostion[0]);
			this.c1.setIndY(this.origPostion[1]);
			this.c2.setAlive(true);
			break;
		case MOVE:
			this.c1.setIndX(this.origPostion[0]);
			this.c1.setIndY(this.origPostion[1]);
			
			break;

		default:
			System.err.println("Unexpected Event!!");
			break;
		}
	}
}
