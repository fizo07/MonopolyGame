package monopolyUML;

public class IncomeTax extends Cell{
	public String name="Income Tax";
	public boolean available=false;
	public int rent=200;
	
	public IncomeTax(){
	
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