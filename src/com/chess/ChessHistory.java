package com.chess;

import java.util.Stack;

import android.util.Log;

import com.chessui.Chess;

public class ChessHistory {
	public Stack<ChessEvent> chessEvents = null;
	public Stack<Chess> changedChess = null;
	public boolean isBlackTurn = true;

	public ChessHistory() {
		super();
		chessEvents = new Stack<ChessEvent>();
		changedChess = new Stack<Chess>();
	}
	
	public void doRecordChessMove(Chess c, int[] origPostion){
		ChessEvent ce = new ChessEvent();
		ce.doInitMoveChessEvent(c, origPostion);
		this.chessEvents.push(ce);
	}
	
	public void doRecordChessEat(Chess opChess, Chess eatenChess, int[] origPostion){
		ChessEvent ce = new ChessEvent();
		ce.doInitEatChessEvent(opChess, eatenChess, origPostion);
		this.chessEvents.push(ce);
	}
	
	public int doRecoveryRecordOneTurn(){
		Chess.Suits walkSide = null;
		int recoveryCount = 0;
		
		if(null==this.chessEvents||this.chessEvents.size()==0){
			Log.w("ChessHistory", "Record not found!!");
			return 0;
		}
		
		ChessEvent ce = this.chessEvents.peek();
		walkSide = ce.c1.getSuit();
		do{
			if(ce==null){
				break;
			}
			ce.doRecoveryEventToChess();
			if(ce.c1!=null)this.changedChess.add(ce.c1);
			if(ce.c2!=null)this.changedChess.add(ce.c2);
			this.chessEvents.pop();
			
			if(walkSide == Chess.Suits.Red){
				isBlackTurn = false;
			}else{
				isBlackTurn = true;
			}
			
			if(this.chessEvents.size()<=0){
				break;
			}
			ce = this.chessEvents.peek();
			recoveryCount++;
		}while(ce.c1.getSuit().equals(walkSide));
		
		return recoveryCount;
	}

}
