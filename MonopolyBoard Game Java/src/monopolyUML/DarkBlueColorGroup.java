package monopolyUML;

public class DarkBlueColorGroup extends PropertyCell{
	public String colorGroup="Dark Blue";
	
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