import java.util.ArrayList;


public class KakuroValidator {

	KakuroBoard kakuroBoard;
	
	public KakuroValidator(KakuroBoard kakuroBoard) {
		this.kakuroBoard = kakuroBoard;
	}
	
	public boolean valid(){
		
		ArrayList<KakuroConstraintElement> constraintList = getConstraints();
		
		//Check to determine whether any of the constraints are unsatisfied 
		for(int i = 0; i < constraintList.size(); i++){
//			System.out.println("cL size: " + constraintList.size());
//			System.out.println("constaint clue: " + constraintList.get(i).getKakuroBoardElement().getClue()
//					+ " constraint value: " + constraintList.get(i).getKakuroBoardElement().getValue()
//					+ " constraint clue2: "+ constraintList.get(i).getKakuroBoardElement().getClue2()
//					+ " constraint value2: " +constraintList.get(i).getKakuroBoardElement().getValue2()
//				+ "xcord: " + constraintList.get(i).getKakuroBoardElement().getX_Offset()
//					+ "ycord: " + constraintList.get(i).getKakuroBoardElement().getY_Offset());
			if(!checkConstraint(constraintList.get(i))){
				return false;
			}
		}
		
		return true;
	}
	// returns true if consistent with all constraints so far
	public boolean consistent(){
		ArrayList<KakuroConstraintElement> constraintList = getConstraints();
		
		//Check to determine whether any of the constraints are unsatisfied 
				for(int i = 0; i < constraintList.size(); i++){
					//System.out.println("cL size: " + constraintList.size());
					if(!checkConstraint2(constraintList.get(i))){
						return false;
					}
				}
				return true;
	}
	
	public ArrayList<KakuroConstraintElement> getConstraints(){
		return kakuroBoard.getKakuroConstraints().getConstraintList();
	}
	
	// returns true if consistent with constraint so far
	public boolean checkConstraint2(KakuroConstraintElement kakuroConstraintElement){
		//Element is a filled space
		if(kakuroConstraintElement.getKakuroBoardElement().getValue() == 0){
			return true;
		}
		
		boolean innerCheck = false;
		boolean firstCheck = true;
		boolean secondCheck = true;
		
		int x_Offset = kakuroConstraintElement.getKakuroBoardElement().getX_Offset();
		int y_Offset = kakuroConstraintElement.getKakuroBoardElement().getY_Offset();
		int sumConstraint = kakuroConstraintElement.getKakuroBoardElement().getValue();
		int sum = 0;
		boolean clue2 = false;
		boolean clue2_2 = false;
		
		//Clue has a vertical constraint
		if(kakuroConstraintElement.getKakuroBoardElement().getClue() == 'v'
				|| kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'v'){
			if(kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'v'){
				sumConstraint = kakuroConstraintElement.getKakuroBoardElement().getValue2();
				clue2 = true;
			}
			int verticalOffset = y_Offset + 1;
			KakuroBoardElement[][] kakuroBoardElementArray = kakuroBoard.getKakuroBoardElementArray();
			//System.out.println("height: " + kakuroBoard.getHeight());
			while(  (verticalOffset < kakuroBoard.getHeight()) && // may need to put the - 1 back
					(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() != 0) &&
					(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'h') &&
					(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'v') &&
					(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'x')
					){
				
				if(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() < 0){
					innerCheck = true;
				}
				if(clue2){
					sum = sum + kakuroBoardElementArray[x_Offset][verticalOffset].getValue();
				}else{
					sum = sum + kakuroBoardElementArray[x_Offset][verticalOffset].getValue();
				}
				
				verticalOffset = verticalOffset + 1;
			}
			//System.out.println("sum: " + sum + "X: " + x_Offset + "Y: " + y_Offset);
			if(sum == sumConstraint){
				firstCheck = true;
			}else if(innerCheck == true){ 
				firstCheck = true;
			}else{
				firstCheck = false;
			}
			innerCheck = false;
			
			
		}
		 sum = 0;
		 clue2 = false;
		 clue2_2 = false;
		if(kakuroConstraintElement.getKakuroBoardElement().getClue() == 'h'
				|| kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'h'){ //Clue is horizontal constraint
			
			if(kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'h'){
				sumConstraint = kakuroConstraintElement.getKakuroBoardElement().getValue2();
				clue2_2 = true;
			}
			
			
			
			int horizontalOffset = x_Offset + 1;
			KakuroBoardElement[][] kakuroBoardElementArray = kakuroBoard.getKakuroBoardElementArray();
			while(  (horizontalOffset < kakuroBoard.getWidth()) &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() != 0) &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'h') &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'v') &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'x')
					){
				
				if(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() < 0){
					innerCheck = true;
				}
				if(clue2_2){
					sum = sum + kakuroBoardElementArray[horizontalOffset][y_Offset].getValue();
				}else{
					sum = sum + kakuroBoardElementArray[horizontalOffset][y_Offset].getValue();
				}
				horizontalOffset = horizontalOffset +1;
			}
			//System.out.println("sum: " + sum + "X: " + x_Offset + "Y: " + y_Offset);
			if(sum == sumConstraint){
				secondCheck = true;
			}else if(innerCheck == true){ 
				secondCheck = true;
				}else{
					secondCheck = false;
				}
			
			
		}
		
		return (firstCheck && secondCheck);
		
	}
	
	public boolean checkConstraint(KakuroConstraintElement kakuroConstraintElement){
		//Element is a filled space
		if(kakuroConstraintElement.getKakuroBoardElement().getValue() == 0){
			return true;
		}
		
		boolean firstCheck = true;
		boolean secondCheck = true;
		int x_Offset = kakuroConstraintElement.getKakuroBoardElement().getX_Offset();
		int y_Offset = kakuroConstraintElement.getKakuroBoardElement().getY_Offset();
		int sumConstraint = kakuroConstraintElement.getKakuroBoardElement().getValue();
		int sum = 0;
		boolean clue2 = false;
		boolean clue2_2 = false;
		
		//Clue has a vertical constraint
		if(kakuroConstraintElement.getKakuroBoardElement().getClue() == 'v'
				|| kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'v'){
			if(kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'v'){
				sumConstraint = kakuroConstraintElement.getKakuroBoardElement().getValue2();
				clue2 = true;
			}
			int verticalOffset = y_Offset + 1;
			KakuroBoardElement[][] kakuroBoardElementArray = kakuroBoard.getKakuroBoardElementArray();
			while(  (verticalOffset < kakuroBoard.getHeight()) && // may need -1 but I don't think so
					(kakuroBoardElementArray[x_Offset][verticalOffset].getValue() != 0) &&
					(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'h') &&
					(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'v') &&
					(kakuroBoardElementArray[x_Offset][verticalOffset].getClue() != 'x')
					){
				if(clue2){
					sum = sum + kakuroBoardElementArray[x_Offset][verticalOffset].getValue();
				}else{
					sum = sum + kakuroBoardElementArray[x_Offset][verticalOffset].getValue();
				}
				verticalOffset = verticalOffset + 1;
			}
			//System.out.println("sum: " + sum + "X: " + x_Offset + "Y: " + y_Offset);
			if(sum == sumConstraint){
				firstCheck = true;
			}else{
			firstCheck = false;
			}
		}
		sum = 0;
		clue2 = false;
		clue2_2 = false;
		if(kakuroConstraintElement.getKakuroBoardElement().getClue() == 'h'
				|| kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'h'){ //Clue is horizontal constraint
			
			if(kakuroConstraintElement.getKakuroBoardElement().getClue2() == 'h'){
				sumConstraint = kakuroConstraintElement.getKakuroBoardElement().getValue2();
				clue2_2 = true;
			}
			
			int horizontalOffset = x_Offset + 1;
			KakuroBoardElement[][] kakuroBoardElementArray = kakuroBoard.getKakuroBoardElementArray();
			while(  (horizontalOffset < kakuroBoard.getWidth()) &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getValue() != 0) &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'h') &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'v') &&
					(kakuroBoardElementArray[horizontalOffset][y_Offset].getClue() != 'x')
					){
				if(clue2_2){
					sum = sum + kakuroBoardElementArray[horizontalOffset][y_Offset].getValue();
				}else{
					sum = sum + kakuroBoardElementArray[horizontalOffset][y_Offset].getValue();
				}
				horizontalOffset = horizontalOffset + 1;
			}
			//System.out.println("sum: " + sum + "X: " + x_Offset + "Y: " + y_Offset);
			if(sum == sumConstraint){
				secondCheck = true;
			}else{
				secondCheck = false;
			}
			
		}
		return (firstCheck && secondCheck);
		
	}

}
