package monopolyUML;

public class FreeParkingCell extends Cell{
	public String name="FREE PARKING";
	boolean available=false;
	
	public FreeParkingCell(){
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