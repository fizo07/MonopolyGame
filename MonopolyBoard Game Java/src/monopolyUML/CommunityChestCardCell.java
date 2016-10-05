package monopolyUML;

public class CommunityChestCardCell extends CardCell{
	public String name="Community Chest";
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name+"\t"+super.toString(); 
	}

}