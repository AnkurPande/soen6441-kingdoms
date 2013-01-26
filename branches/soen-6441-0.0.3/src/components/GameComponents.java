package components;

public abstract class GameComponents {
	
	private String iconFileName ="";
	
	public String displayIcon(){
		if(this.iconFileName != "")
			return this.iconFileName;
		else
			return this.toString();
			
	}
	
	public void setIconFileName(String path){
		this.iconFileName = path;
	}

}
