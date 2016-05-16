
public class KakuroDomain {
	
	int x;
	int y;
	int[] availablevalues;

	public KakuroDomain(int x, int y, int[] availablevalues) {
		super();
		this.x = x;
		this.y = y;
		this.availablevalues = availablevalues;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int[] getAvailablevalues() {
		return availablevalues;
	}

	public void setAvailablevalues(int[] availablevalues) {
		this.availablevalues = availablevalues;
	}

}
