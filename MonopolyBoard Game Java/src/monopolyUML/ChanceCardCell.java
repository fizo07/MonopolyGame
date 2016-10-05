package monopolyUML;

public class ChanceCardCell extends CardCell{
	public String name="Chance";
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name+"\t"+super.toString(); 
	}

}