package components;

import javax.xml.bind.annotation.XmlAttribute;


public class Tile extends GameComponents {
	
	private TileType type;
	
	@XmlAttribute
	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public Tile(TileType t){
		this.type = t;
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
	
	public enum TileType{
		RESOURCES,	HAZARD , MOUNTAIN , DRAGON , GOLDMINE, WIZARD;
	}

}
