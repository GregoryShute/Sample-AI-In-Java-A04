import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class KakuroParser {

	public KakuroParser() {
		
	}
	
	/*Takes in a Kakuro txt file and returns a KakuroBoard object*/

	public KakuroBoard initializeBoard(File f){
		
		KakuroBoardElement[][] kakuroBoard = null;
		KakuroConstraints constraintList = null;
		int width = 0;
        int height = 0;
        
		try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String[] tokens = null;
            
            if((line = br.readLine()) != null){
            	tokens = line.split(" ");
            	width = Integer.parseInt(tokens[0]);
            	height = Integer.parseInt(tokens[1]);
            }
            
            kakuroBoard = new KakuroBoardElement[width][height];
			constraintList = new KakuroConstraints();
            
            int x_Offset;
            int y_Offset; 
            char clue;
            int value;
            
            while ((line = br.readLine()) != null) {
            	tokens = line.split(" ");   
            	x_Offset = Integer.parseInt(tokens[0]);
            	y_Offset = Integer.parseInt(tokens[1]);
            	clue = tokens[2].charAt(0);
            	value = Integer.parseInt(tokens[3]);
            	
            	//handle case where there are multiple clues
            	if(kakuroBoard[x_Offset][y_Offset] != null){
            		char clue2 = clue;
            		int value2 = value;
            		clue = kakuroBoard[x_Offset][y_Offset].getClue();
            		value = kakuroBoard[x_Offset][y_Offset].getValue();
            		if(clue == 'v'){
            		kakuroBoard[x_Offset][y_Offset] = new KakuroBoardElement(x_Offset, y_Offset,clue,value,clue2,value2);
            		KakuroConstraintElement kakuroConstraintElement  
    				= new KakuroConstraintElement(new KakuroBoardElement(x_Offset, y_Offset, clue,value,clue2, value2));
    	constraintList.addConstraint(kakuroConstraintElement);
            		}else{
            			kakuroBoard[x_Offset][y_Offset] = new KakuroBoardElement(x_Offset, y_Offset,clue,value,clue2,value2);
                		KakuroConstraintElement kakuroConstraintElement  
        				= new KakuroConstraintElement(new KakuroBoardElement(x_Offset, y_Offset, clue2,value2,clue, value));
        	constraintList.addConstraint(kakuroConstraintElement);
            		}
    	//System.out.println("I am a double");
            	}else{
            	kakuroBoard[x_Offset][y_Offset] = new KakuroBoardElement(x_Offset, y_Offset,clue,value);
            	
            	KakuroConstraintElement kakuroConstraintElement  
            				= new KakuroConstraintElement(new KakuroBoardElement(x_Offset, y_Offset, clue,value));
            	constraintList.addConstraint(kakuroConstraintElement);
            	}
            	
            }
            
            
            
            br.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		/*Last step is to fill in places of the kakuroBoard that are null with indication of an 
		 * unfilled space (char = '.'; value = -1;)
		 */
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(kakuroBoard[i][j] == null){
					kakuroBoard[i][j] = new KakuroBoardElement(i,j,'.',-1);
				}
			}
			
		}
		KakuroBoard kakuroBoardExtreme = new KakuroBoard(width,height,kakuroBoard,constraintList);	
		
		//For each kakuroBoardElement in the kakuroBoard, remove values that can never be
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//if it is a clue space
				if(kakuroBoardExtreme.getKakuroBoardElementArray()[i][j].getClue() == '.'){
					int horizontalOffset = i;
					int verticalOffset = j;
					int constraintValue;
					int rowElementCount = 0;
					int columnElementCount = 0;
				
					// check the constraint to the left
					horizontalOffset = horizontalOffset -1 ;
					outter: while( (horizontalOffset >= 0)){
						if(i == 4 && j == 1){
						//	System.out.println("horOffyyyy " + horizontalOffset + "cl " + kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getClue());
						}
						if(kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getClue() == 'h' 
								|| kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getClue2() == 'h'){
							if(kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getClue() == 'h' ){
								constraintValue = kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getValue();
							}else{
								constraintValue = kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getValue2();
							}
							if(i == 4 && j == 1){
							//	System.out.println("horOff0 " + horizontalOffset);
							}
							horizontalOffset = horizontalOffset + 1; // move to the next fillable space
							inner: while( (horizontalOffset <= width - 1)){
								
								if(kakuroBoardExtreme.getKakuroBoardElementArray()[horizontalOffset][j].getClue() != '.'){
									break inner;
								}
								
								rowElementCount = rowElementCount + 1;
								horizontalOffset = horizontalOffset + 1;
							}
							if(i == 4 && j == 1){
							//	System.out.println("horOff01 " + horizontalOffset);
							}
							
							rowElementCount = rowElementCount - 1; // I want to exclude the space I'm looking at
							
							int min = 0;
							int max = 0;
							
							for(int z = 1; z <= rowElementCount; z++){
								min = min + z;
							}
							
							int maxValuesToRemove = constraintValue - min; //remove values above this (that are positive)
							
							if(i == 4 && j == 1){
							//	System.out.println("hor " + maxValuesToRemove + " rowElCo " + rowElementCount + "horOff " + horizontalOffset);
							}
							
							int start = 9;
							for(int z = 1; z <= rowElementCount; z++){
								max = max + start;
								start = start - 1;
							}
							
							int minValuesToRemove = constraintValue - max; // remove values below this( that are positive)
							
							for( int z = maxValuesToRemove; z <= 8; z++){ // don't do maxValuesToRemove + 1 because of the way
								//removeAvailableValue method scales into the array which starts at 0
								
								if(z >= 0){
									kakuroBoardExtreme.getKakuroBoardElementArray()[i][j].removeAvailableValue2(z);
								}
								
							}
							
							for( int z = minValuesToRemove - 2; z >= 0; z--){
								
								if(z <= 8){
									kakuroBoardExtreme.getKakuroBoardElementArray()[i][j].removeAvailableValue2(z);
								}
							}
							break outter;
						}
						horizontalOffset = horizontalOffset - 1;
					}
					
					
					// check the constraint above
					verticalOffset = verticalOffset - 1;
					outter: while((verticalOffset >= 0)){
						
						if(kakuroBoardExtreme.getKakuroBoardElementArray()[i][verticalOffset].getClue() == 'v'
								|| kakuroBoardExtreme.getKakuroBoardElementArray()[i][verticalOffset].getClue2() == 'v'){
							if(kakuroBoardExtreme.getKakuroBoardElementArray()[i][verticalOffset].getClue() == 'v'){
								constraintValue = kakuroBoardExtreme.getKakuroBoardElementArray()[i][verticalOffset].getValue();
							}else{
								constraintValue = kakuroBoardExtreme.getKakuroBoardElementArray()[i][verticalOffset].getValue2();
							}
						
							verticalOffset = verticalOffset + 1; // move to the next fillable space
							inner: while( (verticalOffset <= height - 1)){
								
								if(kakuroBoardExtreme.getKakuroBoardElementArray()[i][verticalOffset].getClue() != '.'){
									break inner;
								}
								
								columnElementCount = columnElementCount + 1;
								verticalOffset = verticalOffset + 1;
							}
							
							columnElementCount = columnElementCount - 1; // I want to exclude the space I'm looking at
							
							int min = 0;
							int max = 0;
							
							for(int z = 1; z <= columnElementCount; z++){
								min = min + z;
							}
							
							int maxValuesToRemove = constraintValue - min; //remove values above this (that are positive)
							if(i == 4 && j == 1){
							//	System.out.println("ver " + maxValuesToRemove);
							}
							int start = 9;
							for(int z = 1; z <= columnElementCount; z++){
								max = max + start;
								start = start - 1;
							}
							
							int minValuesToRemove = constraintValue - max; // remove values below this( that are positive)
							
							for( int z = maxValuesToRemove; z <= 8; z++){ // don't do maxValuesToRemove + 1 because of the way
								//removeAvailableValue method scales into the array which starts at 0
								
								if(z >= 0){
									kakuroBoardExtreme.getKakuroBoardElementArray()[i][j].removeAvailableValue2(z);
								}
								
							}
							
							for( int z = minValuesToRemove - 2; z >= 0; z--){
								
								if(z <= 8){
									kakuroBoardExtreme.getKakuroBoardElementArray()[i][j].removeAvailableValue2(z);
								}
							}
							break outter;
						}
						verticalOffset = verticalOffset - 1;
					}
					
					
				}
			}
			
		}
		
		//Values that can never be have been elimnated
		
		
		
		//kakuroBoardExtreme = new KakuroBoard(width,height,kakuroBoard,constraintList);	
		
		return kakuroBoardExtreme;
	}

}
