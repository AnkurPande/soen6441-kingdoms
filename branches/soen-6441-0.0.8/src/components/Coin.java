package components;

public class Coin extends GameComponents {
	
	private Material material;
	private int value;
	
	public int getValue() {
		return value;
	}

	public Coin(Material m, int val){
		this.material = m;
		this.value = val;
	}
	
	public enum Material{
		COPPER, SILVER, GOLD;
	}

}
