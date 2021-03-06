package components;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * This class is for creating the tiles objects in the game.
 * Tiles are similar to cards that can be placed on the game board.
 * @author Team B
 */
public class Tile extends GameComponents {
	
	@XmlAttribute
	private TileType type;
	
	@XmlAttribute
	private int value;
	
	/**
	 * Default constructor.
	 */
	public Tile(){
		
	}
	
	/**
	 * Constructor specifying the type of tile. A tile of with a valid type can be created with this constructor.
	 * 
	 * @param t An enum type of type 'TileType'. It can have the values RESOURCES, HAZARD, etc as per the definition of the enum TileType.
	 */
	public Tile(TileType t, int val){
		this.type = t;
		this.value = val;
	}
	
	/**
	 * Gets the type of the tile.
	 * 
	 * @return The type of the tile (as a enum 'TileType' object).
	 */
	public TileType getType() {
		return this.type;
	}
	
	/**
	 * Gets the value of the tile for score computation purposes.
	 * @return Returns the numeric value of the tile to be considered in score computation.
	 */
	public int getValue(){
		return this.value;		
	}
	
	/**
	 * An enum for restricting the types of tiles that can be created
	 *
	 */
	public enum TileType{
		RESOURCES,	HAZARD , MOUNTAIN , DRAGON , GOLDMINE, WIZARD;
	}

}
