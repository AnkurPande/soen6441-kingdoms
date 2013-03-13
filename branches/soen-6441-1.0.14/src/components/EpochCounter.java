package components;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * The EpochCounter object is to keep track of the current epoch of the game.
 * The 'epoch' is numeric value that is synonymous to "round" in a game.
 * It can have values between a predefined range (integers between 1 and 3 as of build 1)
 * @author Team B
 */
public class EpochCounter extends GameComponents {
	private int currentEpochNo;
	
	/**
	 * Default constructor. Sets the current epoch to 1.
	 */
	public EpochCounter(){
		this.currentEpochNo = 1;
	}
	
	/**
	 * Creates a EpochCounter with a certain epoch no as defined in the parameter.
	 * @param currentEpoch A numeric value to set the current epoch value to.
	 */
	public EpochCounter(int currentEpoch){
		setCurrentEpochNo(currentEpoch);
	}
	
	@XmlAttribute
	/**
	 * Gets the current epoch no of the EpochCounter.
	 * @return The current epoch no.
	 */
	public int getCurrentEpochNo() {
		return this.currentEpochNo;
	}
	
	/**
	 * Sets the current epoch no to the value specified in the parameter - as long as the parameter is within a 
	 * certain range (integers between 1 and 3 as of build 1).
	 * @param newEpochNo The epoch number to set to.
	 */
	public void setCurrentEpochNo(int newEpochNo) {
		if(newEpochNo > 0 && newEpochNo <=3)//game divided into 3 rounds known as epoch counter
		{ 
			this.currentEpochNo = newEpochNo;
		}
		else{
			System.out.println("Invalid Epoch No.");
			if(this.currentEpochNo > 0){
				System.out.println("Keeping previous epoch no:" + this.currentEpochNo);
			}
			else{
				this.currentEpochNo = 1;	//default value of epoch is 1
				System.out.println("Setting the current epoch no to default value:" + this.currentEpochNo);
			}
		}
	}

}
