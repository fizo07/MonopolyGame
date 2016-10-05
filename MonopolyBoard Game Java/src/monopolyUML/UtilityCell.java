package monopolyUML;

public class UtilityCell extends PropertyCell{
	public int rent=0;
	public int mortgageValue=75;
	public int cost=150;
	
	public int getRent(int n,int d){
		if(n<=1)
			return 4*d;
		if(n>1)
			return 10*d;
		else
			return rent;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getMortgageValue(){
		return mortgageValue;
	}
	
	public String toString(){
		return rent+"\t"+mortgageValue+"\t"+super.toString();
	}

}