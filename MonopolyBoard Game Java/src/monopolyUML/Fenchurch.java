package monopolyUML;

public class Fenchurch extends RailRoadCell{
	public String name="Fenchurch St. Station";
	public int pos=25;
	
	public Fenchurch(){}
	
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