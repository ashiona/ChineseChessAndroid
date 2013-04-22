package com.chess;

import com.chessui.Chess;

public class ChessRule {
	public static void armsRule(ChessBoard cb, Chess c, int x, int y){
		if(c.getRank().equals("bing")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			// only one step is allowed for arms
			if((diff_x + diff_y)== 1){
				// for upper red side
				if(c.getSuit() == Chess.Suits.Red){
					// must move forward
					if( y < index_y){
						c.setChosen(false);
						return;
					}else{
						if( index_y <=4 && diff_x == 1){ //must move forward before cross the river
							c.setChosen(false);
							return;
						}else{
							ChangeStatus(cb, c, x, y);
						}
					}
				}// for below black side
				else{
					// must move forward
					if( y > index_y){
						c.setChosen(false);
						return;
					}else{
						if( index_y >=5 && diff_x == 1){ //must move forward before cross the river
							c.setChosen(false);
							return;
						}else{
							ChangeStatus(cb, c, x, y);
						}
					}
				}
			}else{
				System.out.println("give the wrong destination to arms");
				c.setChosen(false);
			}
			
		}else{
			System.out.println("apply wroing rule on arms!");
		}
	}
	// for ju
	public static void chariotRule(ChessBoard cb, Chess c, int x, int y){
		if(c.getRank().equals("ju")||c.getRank().equals("pao")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			// must walk straight
			if( diff_x ==0 || diff_y == 0){
				// haven't considered the block case
				if(diff_x == 0){ // walk vertically
					int min_y = Math.min(index_y, y);
					int max_y = Math.max(index_y, y);
					boolean flag = false; // the position is not taken by default 
					for(int i= min_y+1; i<max_y; i++){
						if(isBlocked(cb,x,i)){
							flag = true;
						}
					}
					if(flag == true){
						System.out.println("the vertical path is blocked");
						c.setChosen(false);
						return;
					}
					else{
						ChangeStatus(cb, c, x, y);
					}
				}
				else{ // walk horizontally
					
					int min_x = Math.min(index_x, x);
					int max_x = Math.max(index_x, x);
					boolean flag = false; // the position is not taken by default 
					for(int i= min_x+1; i<max_x; i++){
						if(isBlocked(cb,i,y)){
							flag = true;
						}
					}
					if(flag == true){
						System.out.println("the horizontal path is blocked");
						c.setChosen(false);
						return;
					}
					else{
						ChangeStatus(cb, c, x, y);
					}
				}
				
			}else{
				System.out.println("give the wrong destination to cannons");
				c.setChosen(false);
			}
		}else{
			System.out.println("apply wroing rule on cannon!");
			c.setChosen(false);
		}
	}
	
	// for pao
	public static void cannonRule(ChessBoard cb, Chess c, Chess cd){
		if(c.getRank().equals("pao")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int x = cd.getIndX();
			int y = cd.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			// must walk straight
			if( diff_x ==0 || diff_y == 0){
				// haven't considered the block case
				if(diff_x == 0){ // walk vertically
					int min_y = Math.min(index_y, y);
					int max_y = Math.max(index_y, y);
					int numBlock = 0;
					boolean flag = false; // the position is not taken by default 
					for(int i= min_y+1; i<max_y; i++){
						if(isBlocked(cb,x,i)){
							numBlock++;
						}
					}
					System.out.println("apply cannon rule, and the numblock is:" + numBlock);
					if(numBlock != 1 || c.getSuit()==cd.getSuit()){
						flag = true;
					}
					if(flag == true){
						System.out.println("the vertical path is blocked");
						c.setChosen(false);
						cd.setChosen(false);
						return;
					}
					else{
						ChangeStatus(cb, c, x, y);
						c.draw();
						cd.dead();
					}
				}
				else{ // walk horizontally
					
					int min_x = Math.min(index_x, x);
					int max_x = Math.max(index_x, x);
					boolean flag = false; // the position is not taken by default 
					int numBlock = 0;
					for(int i= min_x+1; i<max_x; i++){
						if(isBlocked(cb,i,y)){
							numBlock++;
						}
					}
					System.out.println("apply cannon rule, and the numblock is:" + numBlock);
					if(numBlock!=1 || c.getSuit()==cd.getSuit()){
						flag = true;
					}
					if(flag == true){
						System.out.println("the horizontal path is blocked");
						c.setChosen(false);
						cd.setChosen(false);
						return;
					}
					else{
						ChangeStatus(cb, c, x, y);
						c.draw();
						cd.dead();
					}
				}
				
			}else{
				System.out.println("give the wrong destination to cannons");
				c.setChosen(false);
				cd.setChosen(false);
			}
		}else{
			System.out.println("apply wroing rule on cannon!");
			c.setChosen(false);
			cd.setChosen(false);
		}
	}
	// for xiang
	public static void elephantRule(ChessBoard cb, Chess c, int x, int y){
		if(c.getRank().equals("xiang")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			if(diff_x == 2 && diff_y == 2){
				// for red side
				if(c.getSuit() == Chess.Suits.Red){
					// elephant can not cross the river
					if( y<=4 ){
						int middle_x = (x + index_x)/2;
						int middle_y = (y + index_y)/2;
						// path is blocked
						if(!isBlocked(cb,middle_x,middle_y)){
							ChangeStatus(cb, c, x, y);
						}else{
							System.out.println("the path is blocked");
							c.setChosen(false);
							return;
						}
						
					}else{
						c.setChosen(false);
						return;
					}
				}else{ // for black side
					if( y>=5 ){
						int middle_x = (x + index_x)/2;
						int middle_y = (y + index_y)/2;
						// path is blocked
						if(!isBlocked(cb,middle_x,middle_y)){
							ChangeStatus(cb, c, x, y);
						}else{
							System.out.println("the path is blocked");
							c.setChosen(false);
							return;
						}
					}else{
						c.setChosen(false);
						return;
					}
				}
			}else{
				System.out.println("elephant can not move in that way!");
				c.setChosen(false);
			}
		}else{
			System.out.println("apply wroing rule on elephant!");
			c.setChosen(false);
		}

	}
	
	// for ma
	public static void horseRule(ChessBoard cb, Chess c, int x, int y){
		if(c.getRank().equals("ma")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			if((diff_x + diff_y) == 3){
				if(diff_x == 2){
					int middle_x = (x + index_x)/2;
					// path is blocked
					if(!isBlocked(cb,middle_x,index_y)){
						ChangeStatus(cb, c, x, y);
					}else{
						System.out.println("the horizontal path is blocked");
						c.setChosen(false);
						return;
					}
				}else{
					int middle_y = (y + index_y)/2;
					// path is blocked
					if(!isBlocked(cb,index_x,middle_y)){
						ChangeStatus(cb, c, x, y);
					}else{
						System.out.println("the vertical path is blocked");
						c.setChosen(false);
						return;
					}
				}
				
			}else{
				System.out.println("horse can not move in that way!");
				c.setChosen(false);
			}
		}else{
			System.out.println("apply wroing rule on horse!");
			c.setChosen(false);
		}
	}
	
	// for shi
	public static void chapRule(ChessBoard cb, Chess c, int x, int y){
		if(c.getRank().equals("shi")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			if(diff_x == 1 && diff_y == 1){
				// for red side
				if(c.getSuit() == Chess.Suits.Red){
					// chap's movement is limited
					if(x>=3 && x<=5 && y>=0 && y<=2){
						ChangeStatus(cb, c, x, y);
					}else{
						c.setChosen(false);
						return;
					}
				}else{ // for black side
					if(x>=3 && x<=5 && y>=7 && y<=9){
						ChangeStatus(cb, c, x, y);
					}else{
						c.setChosen(false);
						return;
					}
				}
			}else{
				System.out.println("chap can not move in that way!");
				c.setChosen(false);
			}
		}else{
			System.out.println("apply wroing rule on chap!");
			c.setChosen(false);
		}
	}
	
	// for jiang
	public static void willRule(ChessBoard cb, Chess c, int x, int y){
		if(c.getRank().equals("jiang")){
			int index_x = c.getIndX();
			int index_y = c.getIndY();
			int diff_x = Math.abs(x - index_x);
			int diff_y = Math.abs(y - index_y);
			// only one step is allowed for arms
			if((diff_x + diff_y)== 1){
				// for red side
				if(c.getSuit() == Chess.Suits.Red){
					// chap's movement is limited
					if(x>=3 && x<=5 && y>=0 && y<=2){
						ChangeStatus(cb, c, x, y);
					}else{
						c.setChosen(false);
						return;
					}
				}else{ // for black side
					if(x>=3 && x<=5 && y>=7 && y<=9){
						ChangeStatus(cb, c, x, y);
					}else{
						c.setChosen(false);
						return;
					}
				}
			}else{
				System.out.println("will can not move in that way!");
				c.setChosen(false);
			}
		}else{
			System.out.println("apply wroing rule on will!");
			c.setChosen(false);
		}
	}
	
	public static boolean isBlocked(ChessBoard cb, int x, int y){
		boolean flag = false;
		flag = cb.isTaken(x, y);
		return flag;
	}
	
	
	public static void ChangeStatus(ChessBoard cb, Chess c, int x, int y){
		c.setChosen(false);
		c.setPre_x(c.getIndX());
		c.setPre_y(c.getIndY());
	    c.calPosition(x, y);
	    c.setIndX(x);
	    c.setIndY(y);
	    cb.ChangeTaken(c);
	}
	
	public static void AllRules(ChessBoard cb, Chess c, int x, int y){
		if(c.isChosen()){
			c.setChosen(false);
			if(c.getRank().equals("bing")){
				ChessRule.armsRule(cb, c, x, y);
			}
			if(c.getRank().equals("shi")){
				ChessRule.chapRule(cb, c, x, y);
			}
			if(c.getRank().equals("xiang")){
				ChessRule.elephantRule(cb, c, x, y);
			}
			if(c.getRank().equals("ma")){
				ChessRule.horseRule(cb, c, x, y);
			}
			if(c.getRank().equals("jiang")){
				ChessRule.willRule(cb, c, x, y);
			}
			if(c.getRank().equals("pao")||c.getRank().equals("ju")){
				ChessRule.chariotRule(cb, c, x, y);
			}
			c.draw();
		}
	}
}
