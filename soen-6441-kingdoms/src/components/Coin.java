package components;

import javax.xml.bind.annotation.XmlAttribute;

public class Coin extends GameComponents {
	@XmlAttribute
	private Material material;
	
	@XmlAttribute
	private int value;
	
	public Coin(){
		
	}

	public Coin(Material m, int val){
		this.material = m;
		this.value = val;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public enum Material{
		COPPER, SILVER, GOLD;
	}

}
