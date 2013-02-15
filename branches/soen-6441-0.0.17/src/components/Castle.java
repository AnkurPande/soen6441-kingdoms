package components;

import javax.xml.bind.annotation.XmlAttribute;

import controller.GameInstance.PlayerColor;

public class Castle extends GameComponents {
	
	private PlayerColor color;
	private CastleRank rank;
	
	public Castle(){
		
	}

	public Castle(PlayerColor c, CastleRank r){
		this.setColor(c);
		this.setRank(r);
	}
	
	@XmlAttribute
	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}
	
	@XmlAttribute
	public CastleRank getRank() {
		return rank;
	}

	public void setRank(CastleRank rank) {
		this.rank = rank;
	}

	public enum CastleRank{
		ONE, TWO, THREE, FOUR;
	}


}
