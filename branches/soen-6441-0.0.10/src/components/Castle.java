package components;

import controller.GameInstance.PlayerColor;

public class Castle extends GameComponents {
	
	private PlayerColor color;
	private CastleRank rank;

	public Castle(PlayerColor c, CastleRank r){
		this.setColor(c);
		this.setRank(r);
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

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