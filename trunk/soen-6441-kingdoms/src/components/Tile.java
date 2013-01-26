package components;


public class Tile extends GameComponents {
	
	private Type type;
	
	public Tile(){
		
	}
	
	public int getValue(){
		
		switch(this.type)
		{
			case RESOURCES:
				return 1;
			case HAZARD:
				return -1;
			case MOUNTAIN:
				return 0;
			case DRAGON:
				return -6;
			case GOLDMINE:
				return 0;
			case WIZARD:
				return 0;
			default:
				return 0;
		
		}
		
	}
	
	enum Type{
		RESOURCES,	HAZARD , MOUNTAIN , DRAGON , GOLDMINE, WIZARD;
	}

}
