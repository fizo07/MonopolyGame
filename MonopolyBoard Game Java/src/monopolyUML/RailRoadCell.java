package monopolyUML;

public class RailRoadCell extends PropertyCell{
	public int rent;
	public int mortgageValue=100;
	public int cost=200;
	
	public RailRoadCell(){}
	
	public void setRent(int rent){
		this.rent=rent;
	}
	public void setMortgageValue(int mortgageValue){
		this.mortgageValue=mortgageValue;
	}
	public void setCost(int cost){
		this.cost=cost;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getRent(int x){
		if(x==1)
			return 25;
		if(x==2)
			return 50;
		if(x==3)
			return 100;
		if(x==4)
			return 200;
		else 
			return 0;
	}
	
	public int getMortgageValue(){
		return mortgageValue;
	}
	
	public String toString(){
		return rent+"\t"+mortgageValue;
	}


}