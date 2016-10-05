package monopolyUML;

public class Electric extends UtilityCell{
	public String name="Electric Company";
	public int pos=12;

	public Electric(){}
	
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