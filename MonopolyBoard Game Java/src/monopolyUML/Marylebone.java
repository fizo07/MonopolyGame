package monopolyUML;

public class Marylebone extends RailRoadCell{
	public String name="Marylebone Station";
	public int pos=15;
	
	public Marylebone(){}

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