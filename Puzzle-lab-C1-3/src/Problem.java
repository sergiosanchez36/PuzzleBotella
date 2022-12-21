public class Problem {
	String initState;
	String id;
	int bottleSize;
	
	public Problem(String id, int bottleSize,String initState) {
		this.id = id;
		this.bottleSize = bottleSize;
		this.initState = initState;
	}

	public String getInitState() {
		return initState;
	}

	public void setInitState(String initState) {
		this.initState = initState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBottleSize() {
		return bottleSize;
	}

	public void setBottleSize(int bottleSize) {
		this.bottleSize = bottleSize;
	}
}