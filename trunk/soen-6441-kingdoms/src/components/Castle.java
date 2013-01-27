package components;

public class Castle extends GameComponents {
	
	private Color color;
	private Rank rank;
		
	public Castle(){
		
	}
	
	public Castle(Color c, Rank r){
		this.color = c;
		this.rank = r;
	}

	public enum Color{
		RED, YELLOW, BLUE, GREEN;
	}

	public enum Rank{
		ONE, TWO, THREE, FOUR;
	}


}
