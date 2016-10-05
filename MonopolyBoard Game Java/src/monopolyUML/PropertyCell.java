package monopolyUML;

public class PropertyCell extends Cell{
	public boolean available=true;
	public Player owner;
	public boolean mortgaged=false; 
	
	public PropertyCell(){
	}
	
	public void setAvailable(boolean available){
		this.available=available;
	}
	public void setOwner(Player owner){
		this.owner=owner;
	}
	public void setMortgage(boolean mortgaged){
		this.mortgaged=mortgaged;
	}
	public boolean isAvalaible(){
		return available;
	}
	public Player getOwner(){
		return owner;
	}
	public boolean isMortgaged(){
		return mortgaged;
	}
	public String toString(){
		return available+"\t"+mortgaged+"\t"+owner;
	}
}