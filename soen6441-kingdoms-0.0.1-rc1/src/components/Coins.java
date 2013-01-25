package components;

public class Coins extends GameComponents {
	
	private Material material;
	private int value;
	
	public Coins(){
		
	}
	
	public Coins(Material m, int val){
		this.material = m;
		this.value = val;
	}
	
	enum Material{
		COPPER, SILVER, GOLD;
	}

}
