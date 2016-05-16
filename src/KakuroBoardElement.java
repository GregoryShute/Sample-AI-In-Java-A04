
public class KakuroBoardElement {
	
	private char clue;
	private char clue2;
	private int value;
	private int value2;
	private int x_Offset;
	private int y_Offset;
	private int[] availableValues;

	public KakuroBoardElement(int x_Offset, int y_Offset, char clue,int value) {
		this.clue = clue;
		this.value = value;
		clue2 = '.';
		this.x_Offset = x_Offset;
		this.y_Offset = y_Offset;
		
		if(clue == '.'){
			availableValues = new int[9];
			for(int i = 0; i < 9; i++){
				availableValues[i] = i + 1;
			}
			
		}
		
	}
	

	public KakuroBoardElement(int x_Offset, int y_Offset, char clue,int value,char clue2,int value2) {
		this.clue = clue;
		this.value = value;
		this.clue2 = clue2;
		this.value2 = value2;
		this.x_Offset = x_Offset;
		this.y_Offset = y_Offset;
		
		if(clue == '.'){
			availableValues = new int[9];
			for(int i = 0; i < 9; i++){
				availableValues[i] = i + 1;
			}
			
		}
		
	}
	
	public char getClue2() {
		return clue2;
	}

	public void setClue2(char clue2) {
		this.clue2 = clue2;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}
	
	public int[] getAvailableValues() {
		return availableValues;
	}

	public void removeAvailableValue(int removeValue) {
		availableValues[removeValue] = -1; 
	}
	
	public void removeAvailableValue2(int removeValue) {
		availableValues[removeValue] = -2; 
	}
	
	public void addAvailableValue(int value){
		availableValues[value-1] = value; // notice the it is value-1 ( little inconsistent but I think this works)
	}

	public char getClue() {
		return clue;
	}

	public int getX_Offset() {
		return x_Offset;
	}

	public void setX_Offset(int x_Offset) {
		this.x_Offset = x_Offset;
	}

	public int getY_Offset() {
		return y_Offset;
	}

	public void setY_Offset(int y_Offset) {
		this.y_Offset = y_Offset;
	}

	public void setClue(char clue) {
		this.clue = clue;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


}
