package monopolyUML;

public class GotoJailCell extends Cell{
	public String name="GO TO JAIL";
	boolean available=false;
	
	public GotoJailCell(){
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
