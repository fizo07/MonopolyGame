package monopolyUML;

public class WaterWorks extends UtilityCell{
	public String name="Water Works";
	public int pos=28;
	
	public WaterWorks(){}

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