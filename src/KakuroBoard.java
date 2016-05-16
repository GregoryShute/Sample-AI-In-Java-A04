

public class KakuroBoard {
	
	private int width;
	private int height;
	private KakuroBoardElement[][] kakuroBoardElementArray;
	private KakuroConstraints constraintList;

	public KakuroBoard(int width, int height, KakuroBoardElement[][] kakuroBoardElementArray,KakuroConstraints constraintList) {
		//super();
		this.width = width;
		this.height = height;
		this.kakuroBoardElementArray = kakuroBoardElementArray;
		this.constraintList = constraintList;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public KakuroBoardElement[][] getKakuroBoardElementArray() {
		return kakuroBoardElementArray;
	}

//	public void setKakuroBoard(KakuroBoardElement[][] kakuroBoardElement) {
//		this.kakuroBoardElementArray = kakuroBoardElementArray;
//	}
	
	public KakuroConstraints getKakuroConstraints(){
		return constraintList;
	}
	
	public void assignValuetoVariable(int value, KakuroBoardElement kakuroBoardElement){
		
		kakuroBoardElement.setValue(value);
		//need to make sure the array associated with the board is changed - not just the element
		kakuroBoardElementArray[kakuroBoardElement.getX_Offset()][kakuroBoardElement.getY_Offset()] = kakuroBoardElement;
		
		removeImpossibleValuesForAdjacentContiguousRowsAndColumns(value,kakuroBoardElement);
	}
	
	public void unassignValuetoVariable(int value, KakuroBoardElement kakuroBoardElement){
		kakuroBoardElement.setValue(-1);
		//need to make sure the array associated with the board is changed - not just the element
		kakuroBoardElementArray[kakuroBoardElement.getX_Offset()][kakuroBoardElement.getY_Offset()] = kakuroBoardElement;
		
		backtrackImpossibleValuesForContiguousRowsAndColumns(value,kakuroBoardElement);
		reRemoveImpossibleValuesForAdjacentContiguousRowsAndColumns();
	}
	
	public void backtrackImpossibleValuesForContiguousRowsAndColumns(int value, KakuroBoardElement kakuroBoardElement){
		//need to check up and down, left and right
		
				int x_Offset = kakuroBoardElement.getX_Offset();
				int y_Offset = kakuroBoardElement.getY_Offset();
				
				int verticalOffset = y_Offset ; // + 1
				
				//update down
				while(  (verticalOffset <= height - 1) && // not sure whether < or <= on these
						(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() != 0) &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'h') &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'v') &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'x')
						){
					//need to make sure this is not going to add back in values that were pruned at the beginning
					if(kakuroBoardElementArray[x_Offset][verticalOffset].getAvailableValues()[value-1]  != -2){ // don't ever make the condition on this less than 0
						
						kakuroBoardElementArray[x_Offset][verticalOffset].addAvailableValue(value);
						//kakuroBoardElement.addAvailableValue(value);
						//kakuroBoardElementArray[x_Offset][verticalOffset] = kakuroBoardElement; // make sure the board is updated
					}
					verticalOffset = verticalOffset + 1;
				}
				
				verticalOffset = y_Offset;//-1
				
				//update up
				while(  (verticalOffset >= 0) &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() != 0) &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'h') &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'v') &&
						(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'x')
						){
					if(kakuroBoardElementArray[x_Offset][verticalOffset].getAvailableValues()[value-1]  != -2){
						kakuroBoardElementArray[x_Offset][verticalOffset].addAvailableValue(value);
						//kakuroBoardElement.addAvailableValue(value);
						//kakuroBoardElementArray[x_Offset][verticalOffset] = kakuroBoardElement; // make sure the board is updated
					}
					verticalOffset = verticalOffset - 1;
				}
				
				
				
				int horizontalOffset = x_Offset; // + 1
				
				//update right
				while(  (horizontalOffset <= width - 1) &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() != 0) &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'h') &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'v') &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'x')
						){
					if(kakuroBoardElementArray[horizontalOffset][y_Offset].getAvailableValues()[value-1]  != -2){
						kakuroBoardElementArray[horizontalOffset][y_Offset].addAvailableValue(value);
						//kakuroBoardElement.addAvailableValue(value);
						//kakuroBoardElementArray[horizontalOffset][y_Offset] = kakuroBoardElement; // make sure the board is updated
					}
					horizontalOffset = horizontalOffset + 1;
				}
				
				horizontalOffset = x_Offset; // -1
				
				//update left
				while(  (horizontalOffset >= 0) &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() != 0) &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'h') &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'v') &&
						(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'x')
						){
					if(kakuroBoardElementArray[horizontalOffset][y_Offset].getAvailableValues()[value-1] != -2){
						kakuroBoardElementArray[horizontalOffset][y_Offset].addAvailableValue(value);
					//kakuroBoardElement.addAvailableValue(value);
					//kakuroBoardElementArray[horizontalOffset][y_Offset] = kakuroBoardElement; // make sure the board is updated
					}
					horizontalOffset = horizontalOffset - 1;
				}
		
		
	}
	
	public void reRemoveImpossibleValuesForAdjacentContiguousRowsAndColumns(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if((kakuroBoardElementArray[i][j].getValue() > 0)
						&& (kakuroBoardElementArray[i][j].getClue() != 'h') 
						&& (kakuroBoardElementArray[i][j].getClue() != 'v') 
						&& (kakuroBoardElementArray[i][j].getClue() != 'x') 
						){
					
					removeImpossibleValuesForAdjacentContiguousRowsAndColumns(kakuroBoardElementArray[i][j].getValue(), kakuroBoardElementArray[i][j]);
					
				}
			}
			
		}
	}
	
	public void removeImpossibleValuesForAdjacentContiguousRowsAndColumns(int removeValue, KakuroBoardElement kakuroBoardElement){
		
		//need to check up and down, left and right
		
		int x_Offset = kakuroBoardElement.getX_Offset();
		int y_Offset = kakuroBoardElement.getY_Offset();
		
		int verticalOffset = y_Offset + 1; //I don't want the assigned to have it taken out
		
		//update down
		while(  (verticalOffset <= height - 1) && // not sure whether < or <= on these
				(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() != 0) &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'h') &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'v') &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'x') 
				){
			
			if(kakuroBoardElementArray[x_Offset][verticalOffset].getAvailableValues()[removeValue-1] != -2){
				kakuroBoardElementArray[x_Offset][verticalOffset].removeAvailableValue(removeValue-1);
			}
			
			//kakuroBoardElement.removeAvailableValue(removeValue-1); // I think I need the - 1 here (on all of them)
			//kakuroBoardElementArray[x_Offset][verticalOffset] = kakuroBoardElement; // make sure the board is updated
			verticalOffset = verticalOffset + 1;
		}
		
		verticalOffset = y_Offset - 1;
		
		//update up
		while(  (verticalOffset >= 0) &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() != 0) &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'h') &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'v') &&
				(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'x')
				){
			if(kakuroBoardElementArray[x_Offset][verticalOffset].getAvailableValues()[removeValue-1] != -2){
				kakuroBoardElementArray[x_Offset][verticalOffset].removeAvailableValue(removeValue-1);
			}
			//kakuroBoardElement.removeAvailableValue(removeValue-1);
			//kakuroBoardElementArray[x_Offset][verticalOffset] = kakuroBoardElement; // make sure the board is updated
			verticalOffset = verticalOffset - 1;
		}
		
		
		
		int horizontalOffset = x_Offset + 1;
		
		//update right
		while(  (horizontalOffset <= width - 1) &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() != 0) &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'h') &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'v') &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'x')
				){
			
			if(kakuroBoardElementArray[horizontalOffset][y_Offset].getAvailableValues()[removeValue-1] != -2){
				kakuroBoardElementArray[horizontalOffset][y_Offset].removeAvailableValue(removeValue-1);
			}
//			kakuroBoardElement.removeAvailableValue(removeValue-1);
//			kakuroBoardElementArray[horizontalOffset][y_Offset] = kakuroBoardElement; // make sure the board is updated
			horizontalOffset = horizontalOffset + 1;
		}
		
		horizontalOffset = x_Offset - 1;
		
		//update left
		while(  (horizontalOffset >= 0) &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() != 0) &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'h') &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'v') &&
				(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'x')
				){
			
			if(kakuroBoardElementArray[horizontalOffset][y_Offset].getAvailableValues()[removeValue-1] != -2){
				kakuroBoardElementArray[horizontalOffset][y_Offset].removeAvailableValue(removeValue-1);
			}
//			kakuroBoardElement.removeAvailableValue(removeValue-1);
//			kakuroBoardElementArray[horizontalOffset][y_Offset] = kakuroBoardElement; // make sure the board is updated
			horizontalOffset = horizontalOffset - 1;
		}
		
	}
	
	//returns false if any fillable space does not have any available moves
	public boolean forwardCheck(){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				
				if(kakuroBoardElementArray[i][j].getClue() == '.' && kakuroBoardElementArray[i][j].getValue() < 0){
					
					int availableMovesCount = 9;
					
					for(int ii = 0; ii <= 8; ii++){
						if(kakuroBoardElementArray[i][j].getAvailableValues()[ii] < 0){
							availableMovesCount--;
						}
					}
					if(availableMovesCount == 0){
						return false;
					}
				}
				
				
			}
			
		}
		return true;
	}
	
	public void printKakuroBoard(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//System.out.println( i + " " + j + " " + kakuroBoardElementArray[i][j].getValue());
			if(kakuroBoardElementArray[i][j].getClue() == '.'){
				for(int ii = 0; ii <=8; ii ++){
				//	System.out.println(i + " " + j + " " + kakuroBoardElementArray[i][j].getAvailableValues()[ii]);
				}
				
			}
			
			
			}
			
		}
	}
	
	public void printSolution(){
		int count = 0;
		int count2 = 0;
		for(int ii = 0; ii < width; ii++){
			for(int jj = 0; jj < height; jj++){
				if(kakuroBoardElementArray[ii][jj].getClue() == '.'){
					count++;
				}
			}
		}
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(kakuroBoardElementArray[i][j].getClue() == '.'){
					if(count2 == count -1 ){
						
						System.out.print(i + " " + j + " " + kakuroBoardElementArray[i][j].getValue());
					}else{
						count2++;
						System.out.println( i + " " + j + " " + kakuroBoardElementArray[i][j].getValue());
					}
				}
			}
		}
	}

}
