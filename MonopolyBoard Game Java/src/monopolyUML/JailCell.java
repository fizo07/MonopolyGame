package monopolyUML;

public class JailCell extends Cell{
	public String name="JAIL";
	boolean available;
	
	public JailCell(){
		this.available=false;
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name+"\t"+available; 
	}


}
