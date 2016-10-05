package monopolyUML;

public class BrownColorGroup extends PropertyCell{
	public String colorGroup="Brown";
	
	public void DarkBlueColorGroup(){}
	
	public void setColorGroup(String colorGroup){
		this.colorGroup=colorGroup;
	}
	
	public String getColorGroup(){
		return colorGroup;
	}
	
	public String toString(){
		return colorGroup+"\t"+super.toString();
	}

}