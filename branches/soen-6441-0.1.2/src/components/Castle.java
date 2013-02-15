package components;

import javax.xml.bind.annotation.XmlAttribute;

import model.GameInstance.PlayerColor;

/**
 * This class defines the castle components for the game.
 *
 */
public class Castle extends GameComponents {
	/**
	 * Note that the private member 'color' of this class is the same as the players color.
	 */
	private PlayerColor color;
	private CastleRank rank;
	
	/**
	 * Default constructor.
	 */
	public Castle(){
		
	}
	
	/**
	 * Create a castle with defined color and rank.
	 * @param c The color of the castle - the type of this parameter is the same type as enum PlayerColor in the model.GameInstance class.
	 * @param r The rank of the castle.
	 */
	public Castle(PlayerColor c, CastleRank r){
		this.setColor(c);
		this.setRank(r);
	}
	
	@XmlAttribute
	/**
	 * Gets the color of the castle.
	 * @return Returns the color of the castle.
	 */
	public PlayerColor getColor() {
		return color;
	}
	
	/**
	 * Sets the color of the castle. Note that the method is private.
	 * Once created castles can't change color - as this is not required as of build 1.
	 * 
	 * @param color The color to set the castle to.
	 */
	private void setColor(PlayerColor color) {
		this.color = color;
	}
	
	@XmlAttribute
	/**
	 * Gets the rank of the castle.
	 * @return Returns the rank of the castle.
	 */
	public CastleRank getRank() {
		return rank;
	}
	
	/**
	 * Sets the rank of the castle to the value in the parameter.
	 * Note that the method is private - castle ranks can't be reset 
	 * once they are created as this is not a requirement as of build 1.
	 * 
	 * @param rank The rank to set the castle to.
	 */
	private void setRank(CastleRank rank) {
		this.rank = rank;
	}
	
	/**
	 * An enum to restrict the ranks of castles.
	 *
	 */
	public enum CastleRank{
		ONE, TWO, THREE, FOUR;
	}


}
