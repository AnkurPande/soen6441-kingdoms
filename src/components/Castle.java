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

	enum Color{
		RED, YELLOW, BLUE, GREEN;
	}

	enum Rank{
		ONE, TWO, THREE, FOUR;
	}


}
