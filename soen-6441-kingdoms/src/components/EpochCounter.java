package components;

public class EpochCounter extends GameComponents {
	private int currentEpoch;
	
	public EpochCounter(){
		this.currentEpoch = 1;
	}
	
	public EpochCounter(int currentEpoch){
		setCurrentEpoch(currentEpoch);
	}

	public int getCurrentEpoch() {
		return currentEpoch;
	}

	public void setCurrentEpoch(int currentEpoch) {
		if(currentEpoch > 0 && currentEpoch <=3) 
			this.currentEpoch = currentEpoch;
	}

}
