import java.io.File;


public class KakuroEngine {

	public KakuroEngine() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		File f = new File(args[0]);
		KakuroParser kakuroParser = new KakuroParser();
		//Generate the kakuroBoard
		KakuroBoard kakuroBoard = kakuroParser.initializeBoard(f);
//		kakuroBoard.printKakuroBoard();
//		KakuroValidator kakuroValidator = new KakuroValidator(kakuroBoard);
//		boolean valid = kakuroValidator.valid();
//		System.out.println(valid);
		
//		kakuroBoard.assignValuetoVariable(1, kakuroBoard.getKakuroBoardElementArray()[1][1]);
//		kakuroBoard.printKakuroBoard();
		KakuroSolver kakuroSolver = new KakuroSolver();
		kakuroSolver.backtrackingSearch(kakuroBoard);
		
		
	}

}
