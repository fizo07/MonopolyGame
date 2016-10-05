package monopolyUML;

public class Liverpool extends RailRoadCell{

	public String name="Liverpool St. Station";
	
	public Liverpool(){}
	public int pos=35;
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	public int getPosition(){
		return pos;
	}
	
	public String toString(){
		return name+"\t"+super.toString();
	}
	
}