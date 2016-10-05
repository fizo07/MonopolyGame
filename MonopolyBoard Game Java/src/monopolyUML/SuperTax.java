package monopolyUML;

public class SuperTax extends Cell{
	public String name="Super Tax";
	public boolean available=false;
	public int rent=100;
	
	public SuperTax(){
	
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public String getName(){
		return name;
	}
	
	public int getRent(){
		return rent;
	}
	
	public String toString(){
		return name+"\t"+rent+"\t"+available+"\t"+super.toString();
	}

}