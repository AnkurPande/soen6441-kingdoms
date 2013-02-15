package components;

import javax.xml.bind.annotation.XmlAttribute;

public class EpochCounter extends GameComponents {
	private int currentEpochNo;
	
	public EpochCounter(){
		this.currentEpochNo = 1;
	}
	
	public EpochCounter(int currentEpoch){
		setCurrentEpochNo(currentEpoch);
	}
	
	@XmlAttribute
	public int getCurrentEpochNo() {
		return this.currentEpochNo;
	}

	public void setCurrentEpochNo(int currentEpoch) {
		if(currentEpoch > 0 && currentEpoch <=3) 
			this.currentEpochNo = currentEpoch;
	}

}
