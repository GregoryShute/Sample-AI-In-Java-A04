import java.util.ArrayList;

/*note that filled spaces are included in the constraint list based 
 * on my implementation.
 */

public class KakuroConstraints {
	
	private ArrayList<KakuroConstraintElement> constraintList = new ArrayList<KakuroConstraintElement>();

	public KakuroConstraints() {
		
	}
	
	public void addConstraint(KakuroConstraintElement constriant){
		constraintList.add(constriant);
	}
	
	public ArrayList<KakuroConstraintElement> getConstraintList(){
		return constraintList;
	}

}
