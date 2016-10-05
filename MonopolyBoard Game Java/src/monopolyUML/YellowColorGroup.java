package monopolyUML;

public class YellowColorGroup extends PropertyCell{
	public String colorGroup="Yellow";
	
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