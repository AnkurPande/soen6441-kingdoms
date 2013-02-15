package components;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient //Prevents the mapping of a JavaBean property/type to XML representation
@XmlSeeAlso({Castle.class, Coin.class, Tile.class, EpochCounter.class, Placeholder.class}) //Instructs JAXB to also bind other classes when binding this class
public abstract class GameComponents {
	
	private String iconFileName ="";
	
	public String displayIcon(){
		if(this.iconFileName != "")
			return this.iconFileName;
		else
			return this.getClass().getName();
			
	}
	
	public void setIconFileName(String path){
		this.iconFileName = path;
	}

}
