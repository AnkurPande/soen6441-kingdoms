package components;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * The coin class creates coins for the players during gameplay.
 * They are used to keep track of the players score.
 *
 */
public class Coin extends GameComponents {
	@XmlAttribute
	private Material material;
	
	@XmlAttribute
	private int value;
	
	/**
	 * Default constructor.
	 */
	public Coin(){
		
	}
	
	/**
	 * Create a coin with specified material and value.
	 * 
	 * @param m The material of the coin.
	 * @param val The value of the coin. This is used for scoring purposes.
	 */
	public Coin(Material m, int val){
		this.material = m;
		this.value = val;
	}
	
	/**
	 * Returns the value of the coin
	 * @return The numeric value of the coin for scoring purposes.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * An enum type to restrict the materials of the coins.
	 *
	 */
	public enum Material{
		COPPER, SILVER, GOLD;
	}

}
