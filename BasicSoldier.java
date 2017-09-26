package fighters;

import framework.BattleField;
import framework.Random131;
//Class for details pertaining to the different characteristics for each soldier
public class BasicSoldier {

	//State of the soldiers
	public final static int INITIAL_HEALTH = 10;  
	public final static int ARMOR = 20;   
	public final static int STRENGTH = 30;  
	public final static int SKILL = 40;
	//Moving variables
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int UP_AND_RIGHT = 4;
	public final static int DOWN_AND_RIGHT = 5;
	public final static int DOWN_AND_LEFT = 6;
	public final static int UP_AND_LEFT = 7;
	public final static int NEUTRAL = -1;

	public final BattleField battleField;  
	public int row, col; 
	public int health; 
	public final int team; 
	
	//Constructor that gives the values for each individual soldier
	public BasicSoldier(BattleField battleFieldIn, int 
			teamIn, int rowIn, int colIn){
		battleField = battleFieldIn;
		row = rowIn;
		col = colIn;
		health = INITIAL_HEALTH;
		team = teamIn;	

	}
	/*This method returns true if it is possible for this soldier to move, 
	false otherwise. */
	public boolean canMove() {
		boolean move = false;
		if (battleField.get(row+1,col) == BattleField.EMPTY  
				&& battleField.get(row+1,col) == BattleField.OUT_OF_BOUNDS 
				&& battleField.get(row+1,col) == BattleField.OBSTACLE
				&& battleField.get(row+1,col) != team
				
				|| battleField.get(row-1, col) == BattleField.EMPTY 
						&& battleField.get(row-1,col) == BattleField.OUT_OF_BOUNDS 
						&& battleField.get(row-1,col) == BattleField.OBSTACLE
						&& battleField.get(row-1,col) != team
						
				|| battleField.get(row, col+1) == BattleField.EMPTY 
						&& battleField.get(row,col+1) == BattleField.OUT_OF_BOUNDS 
						&& battleField.get(row,col+1) == BattleField.OBSTACLE
						&& battleField.get(row,col+1) != team
						
				|| battleField.get(row, col-1) == BattleField.EMPTY
				&& battleField.get(row,col-1) == BattleField.OUT_OF_BOUNDS 
				&& battleField.get(row,col-1) == BattleField.OBSTACLE
				&& battleField.get(row,col-1) != team
				){
			
			move = true;
		}
		return move;
	}
	/*This method returns the number of enemies who are on the battlefield.*/
	public int numberOfEnemiesRemaining()  {
		int counter = 0;
		//Checks the gird for soldiers not on users team and adds them to counter
		for(int i = 0; i < battleField.getRows(); i++){
			for(int j = 0; j < battleField.getCols(); j++){
				if(battleField.get(i, j) != team){
					counter++;
				}
			}
		} 
		//Returns total number of enemies to user
		return counter;
	}
	/*This method calculates the number of moves it would take this 
	 * soldier to reach the destination specified by the parameters.*/
	public int getDistance(int destinationRow, int destinationCol){
		int totalDistance;	
		//Takes absolute value of distance between destination and row/col
		totalDistance = Math.abs((row-destinationRow))+
				Math.abs((col-destinationCol));
		//Returns total distance to user
		return totalDistance;



	} 
	/*This method determines which way this soldier would have to go in 
	 * order to arrive at the destination specified by the parameters. */
	public int getDirection(int destinationRow, int destinationCol) {
		int direction = NEUTRAL;
		//Up
		if (row > destinationRow && col == destinationCol){
			direction = UP;
			//down
		} else if (row < destinationRow && col == destinationCol){
			direction = DOWN;
			//left
		} else if (col > destinationCol && row == destinationRow){
			direction = LEFT;
			//right
		} else if (col < destinationRow && row == destinationRow){
			direction = RIGHT;
			//Up and Right
		} else if (row > destinationRow && col < destinationCol){
			direction = UP_AND_RIGHT;
			//Down and Right
		} else if (row < destinationRow && col < destinationCol){
			direction = DOWN_AND_LEFT;
			//Down and Left
		} else if (row  < destinationRow && col < destinationCol){
			direction = DOWN_AND_LEFT;
			//Up and Right
		} else if (row > destinationRow && col > destinationCol){
			direction = UP_AND_RIGHT;
			//neutral
		} else {
			direction = NEUTRAL;
		}
		//Returns direction to user
		return direction;
	}
	//This method will return the direction of the nearest team mate. 
	public int getDirectionOfNearestFriend(){

	    int distance = (battleField.getRows() + battleField.getCols()) + 1;
	    int direction = NEUTRAL;
	    
	    for (int rowIndex = 0; rowIndex < battleField.getRows(); rowIndex++) {
	      for (int colIndex = 0; colIndex < battleField.getCols(); colIndex++) {
	        if (battleField.get(rowIndex, colIndex) == team && !(colIndex == col && rowIndex == row)) {
	          if (getDistance(rowIndex, colIndex) < distance) {
	            distance = getDistance(rowIndex, colIndex);
	            direction = getDirection(rowIndex, colIndex);
	          }
	        }
	      }
	    }
	    
	    return direction;
	}
	/*This method will count the number of team mates whose distance
	 *  (measured in "moves") from the current soldier is less than 
	 *  or equal to the specified radius.*/
	public int countNearbyFriends(int radius) {
		 int x = 0;
		    
		    for (int i = 0; i < battleField.getRows(); i++) {
		      for (int j = 0; j < battleField.getCols(); j++) {
		        if (battleField.get(i, j) == team && !(j == col && i == row) && getDistance(i, j) <= radius) {
		          x++;
		        }
		      }
		    }
		    
		    return x;
	}
	/*This method will return the direction of the nearest enemy whose distance
	 *  from the current soldier (measured in "moves") is less than or equal to
	 *   the given radius. */
	public int getDirectionOfNearestEnemy(int radius) {
		  int enemyTeam;
		    
		    if (team == BattleField.BLUE_TEAM) {
		      enemyTeam = BattleField.RED_TEAM;
		    }
		    else {
		      enemyTeam = BattleField.BLUE_TEAM;
		    }
		    
		    int distance = (battleField.getRows() + battleField.getCols()) + 1;
		    int direction = NEUTRAL;
		    
		    for (int i = 0; i < battleField.getRows(); i++) {
		      for (int j = 0; j < battleField.getCols(); j++) {
		        if ((battleField.get(i, j) == enemyTeam && getDistance(i, j) <= radius)) {
		          if (getDistance(i, j) < distance) {
		            distance = getDistance(i, j);
		            direction = getDirection(i, j);
		          }
		        }
		      }
		    }
		    
		    return direction;
	} 
	/*This method will serve as the "AI" for the soldier. It will either attack
	 * , move, or do nothing depending on where it is located and what 
	 * is around it*/
	public void performMyTurn()  {
		
		while(health <= 2){
			if(col+1 != team){
				col = col-1;
			}
			if(col-1 != team){
				col = col+1;
			}
			if(row+1 != team){
				row = row-1;
			}
			if(row-1 != team){
				row = row+1;
			}
		}
		
		if(col+1 == team){
			col = col+1;
		}else if(col-1 == team){
			col = col-1;
		}else if (row +1 == team){
			row = row+1;
		}else if(row-1 == team){
			row = row-1;
		}
		//Attacks opponents if the they are above, below, to the left, or right
				if(col+1 != team){
					//battleField.attack(row, col+1);
				}else if(col-1 != team){
					battleField.attack(row, col-1);
				}else if(row+1 != team){
					battleField.attack(row+1, col);
				}else if(row-1 != team){
					battleField.attack(row-1, col+1);
				}
				//Moves if there is an empty space
				if(col+1 == BattleField.EMPTY){
					col = col+1;
				}else if(col-1 == BattleField.EMPTY){
					col = col-1;
				}else if(row+1 == BattleField.EMPTY){
					row = row+1;
				}else if(row-1 == BattleField.EMPTY ){
					row = row-1;
				}
				
	//******************************Here is Where you Implement AI*********************************************//	
		// #1-----------If Surrounded on All Sides fight
				//If surrounded on all sides, fight
	/*	while(col+1 != team && col-1 != team && row+1 != team && row-1 != team){
			battleField.attack(col+1, row+1);
			battleField.attack(col-1, row-1);
			
			
		}
		 */
	//2-------------Runs away if surrounded on two or all three sides
				if( row+1 != team && col+1 !=  team){
					row = row-1;
					col = col-1;
					
				}else if( row-1 != team && col+1 !=  team){
					row = row+1;
					col = col-1;
					
				}else if( row-1 != team && col-1 !=  team){
					row = row+1;
					col = col+1;
					
				}else if( row+1 != team && col-1 !=  team){
					row = row-1;
					col = col+1;
				}else if(row+1 != team && col-1 != team && row-1 != team){
					col = col+1;
				}else if(row+1 != team && col+1 != team && row-1 != team){
					col = col-1;
				}else if(col+1 != team && col-1 != team && row-1 != team){
					row = row+1;
				}else if(col+1 != team && col-1 != team && row+1 != team){
					row = row-1;
				} 		
		//3-----------------If your with 2 or more ally, fight
			/*	if(row +1 == team && row -1 == team && col + 1 != team ){
					battleField.attack(col+1, row);
				}else if (row +1 == team && row -1 == team &&  col -1 != team){
					battleField.attack(col-1, row);
				}else if(col +1 == team && col -1 == team && row -1 != team){
					battleField.attack(col, row-1);
				}else if(col +1 == team && col -1 == team && row +1 != team){
					battleField.attack(col, row+1);
				}
			*/	
		
	}
}
