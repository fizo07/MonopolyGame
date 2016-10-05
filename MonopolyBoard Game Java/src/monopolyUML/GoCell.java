package monopolyUML;

public class GoCell extends Cell{
	public String name="GO CELL";
	boolean available=false;
	
	public GoCell(){
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name+"\t"+available+"\t"+super.toString(); 
	}
}
