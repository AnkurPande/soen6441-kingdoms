package components;

import javax.xml.bind.annotation.XmlAttribute;

public class Coin extends GameComponents {
	@XmlAttribute
	private Material material;
	private int value;
	
	@XmlAttribute
	public int getValue() {
		return this.value;
	}

	public Coin(Material m, int val){
		this.material = m;
		this.value = val;
	}
	
	public enum Material{
		COPPER, SILVER, GOLD;
	}

}
