package monopolyUML;

public class CardCell extends Cell{
	public boolean available=false;
	
	public CardCell(){
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public String toString(){
		return available+"\t"+super.toString();
	}
}