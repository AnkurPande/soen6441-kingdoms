package components;

/**
 * A placeholder is a blank type of GameComponent. 
 * The purpose of this is type of GameComponent is to occupy a place on game board to show that 
 * some GameComponents (castles/tiles) can be placed on that particular spot. 
 * 
 * @author Team B
 */
public class Placeholder extends GameComponents {
	
	/**
	 * Default constructor. Creates a PlaceHolder object without any members and a image file.
	 */
	public Placeholder(){
		this.setIconFileName("images/placeholder.png");
	}

}
