package components;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;


@XmlTransient //Prevents the mapping of a JavaBean property/type to XML representation
@XmlSeeAlso({Castle.class, Coin.class, Tile.class, EpochCounter.class, Placeholder.class}) //Instructs JAXB to also bind other classes when binding this class
/**
 * This is an abstract class for all game components.
 * Examples of game components are castles, coins, epoch counter, tiles, etc.
 * This class would contain methods that are common for all game components.
 * @author Team B
 */
public abstract class GameComponents {
	
	private String iconFileName ="";
	
	/**
	 * Gets the display icon file name for the component. 
	 * @return Returns the path of the icon file name. If the file name is not defined(blank) then the class name is returned.
	 */
	public String displayIcon(){
		if(this.iconFileName != ""){
			return this.iconFileName;
		}
		else{
			return this.getClass().getName();
		}			
	}
	
	/**
	 * Sets the icon file name to the path specified.
	 * @param path The path of the icon file. Starts from the application root.
	 */
	public void setIconFileName(String path){
		this.iconFileName = path;
	}

}
