package monopolyUML;

public class KingCross extends RailRoadCell{
	public String name="Kings Cross Station";
	public int pos=5;
	
	public KingCross(){}
	
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