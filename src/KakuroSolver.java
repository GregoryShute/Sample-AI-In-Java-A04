import java.util.ArrayList;

//one more place to forward check / consistency check( prob easier) is to see if there are enough available values left in a column or row
public class KakuroSolver {

	public KakuroSolver() {
		
	}
	
	public boolean backtrackingSearch(KakuroBoard kakuroBoard){
		
		//Check to see if the kakuroBoard is valid
		KakuroValidator kakuroValidator = new KakuroValidator(kakuroBoard);
		if(kakuroValidator.valid()){
			kakuroBoard.printSolution();
			return true;
		}
		
		//should forward check to see if we get a failure right here to make sure that the board is
		//not impossible to solve
		
		return backtrack(getDomains(kakuroBoard), kakuroBoard);	
	}
	
	public boolean backtrack(ArrayList<KakuroDomain> domains, KakuroBoard kakuroBoard){
		
//		for(int i = 0; i < domains.size(); i++){
//			KakuroDomain d = domains.get(i);
//		//	System.out.println("Domain " + d.getX() +" " + d.getY());
//			for(int j = 0; j <= 8; j ++){
//		//	System.out.println(d.getAvailablevalues()[j]);
//			}
//		}
//		KakuroValidator kakuroValidator2 = new KakuroValidator(kakuroBoard);
//		if(kakuroValidator2.valid()){
//			kakuroBoard.printSolution();
//			return true;
//		}
		
	//	System.out.println(domains.size());
		//kakuroBoard.printSolution();
		//System.out.println(domains.size());
		//kakuroBoard.printSolution();
		if(domains.size() > 0){
			
			KakuroDomain domain = domains.get(0);
			
			int value = 0;
		
			for(int j = 0; j <= 8; j++){
				
				if((value = domain.getAvailablevalues()[j]) > 0){ 
					kakuroBoard.assignValuetoVariable(value, kakuroBoard.getKakuroBoardElementArray()[domain.getX()][domain.getY()]);
			//		System.out.println("value: " + value);
					KakuroValidator kakuroValidator = new KakuroValidator(kakuroBoard);
					if(kakuroValidator.consistent()){
					//	System.out.println("hello");
						if(!kakuroBoard.forwardCheck()){ //forward check fails 
							kakuroBoard.unassignValuetoVariable(value, kakuroBoard.getKakuroBoardElementArray()[domain.getX()][domain.getY()]);
						}else{
							//domains.remove(0); // do not think I need to do this because every time I get the domains it figures it out
							//from the fact that I assigned a value
							backtrack(getDomains(kakuroBoard),kakuroBoard); //return result
							kakuroBoard.unassignValuetoVariable(value, kakuroBoard.getKakuroBoardElementArray()[domain.getX()][domain.getY()]);
						}
					}else{ //it is not consistent
						kakuroBoard.unassignValuetoVariable(value, kakuroBoard.getKakuroBoardElementArray()[domain.getX()][domain.getY()]);
					}
				}
				
			}
			
			//domains.add(domain); probably do not need to add one either
			return false;
		
		}else{
			KakuroValidator kakuroValidator = new KakuroValidator(kakuroBoard);
		//	kakuroBoard.printSolution();
			if(kakuroValidator.valid()){
				//System.out.println("hello");
				kakuroBoard.printSolution();
				return true;
			}else{
				return false;
			}
		}
		
		//return false;
		
	}
	
	
	
	//returns information about unassigned domains (position and available values)
	public ArrayList<KakuroDomain> getDomains(KakuroBoard kakuroBoard){
		ArrayList<KakuroDomain> domains = new ArrayList<KakuroDomain>();
		
			for(int i = 0; i < kakuroBoard.getWidth(); i++){
				for(int j = 0; j < kakuroBoard.getHeight(); j++){
					if(kakuroBoard.getKakuroBoardElementArray()[i][j].getClue() == '.'){
						if(kakuroBoard.getKakuroBoardElementArray()[i][j].getValue() == -1){
							KakuroDomain domain = new KakuroDomain(i,j,kakuroBoard.getKakuroBoardElementArray()[i][j].getAvailableValues());
						//	System.out.println("domain listing: " + i + " " + j );
							domains.add(domain);
						}
					}		
				}
			}
			
			return domains;
	}

}
