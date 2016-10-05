package monopolyUML;

public class Piccadilly extends YellowColorGroup{
	public String name="PICCADILLY";
	public int cost=280;
	public int rent=24;
	public int noofhouses=0;
	public int mortgageValue=140;
	public int costofhouse=150;
	
	public Piccadilly(){}
	
	
	public void setName(String name){
		this.name=name;
	}
	public void setCost(int cost){
		this.cost=cost;
	}
	public void setRent(int rent){
		this.rent=rent;
	}
	public void setNoOfHouses(int noofhouses){
		this.noofhouses=noofhouses;
	}
	public void setMortgageValue(int mortgageValue){
		this.mortgageValue=mortgageValue;
	}
	public void setCostOfHouse(int costofhouse){
		this.costofhouse=costofhouse;
	}
	
	public String getName(){
		return name;
	}
	public int getCost(){
		return cost;
	}
	public int getRent(){
		if(noofhouses==0)
			return 24;
		if(noofhouses==1)
			return 120;
		if(noofhouses==2)
			return 360;
		if(noofhouses==3)
			return 850;
		if(noofhouses==4)
			return 1025;
		if(noofhouses==5)
			return 1200;
		else
			return 0;
	}
	public int getNumberOfHouses(){
		return noofhouses;
	}
	public int getMortgageValue(){
		return mortgageValue;
	}
	public int getCostOfHouse(){
		return costofhouse;
	}

	public String toString(){
		return name+"\t"+cost+"\t"+rent+"\t"+noofhouses+"\t"+mortgageValue+"\t"+costofhouse+"\t"+super.toString()+"\t";
	}


}