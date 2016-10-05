package monopolyUML;

public class Whitechapel extends BrownColorGroup{
	public String name="WHIIE CHAPEL ROAD";
	public int cost=60;
	public int rent=4;
	public int noofhouses=0;
	public int mortgageValue=30;
	public int costofhouse=50;
	
	public Whitechapel(){}
	
	
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
			return 4;
		if(noofhouses==1)
			return 20;
		if(noofhouses==2)
			return 60;
		if(noofhouses==3)
			return 180;
		if(noofhouses==4)
			return 320;
		if(noofhouses==5)
			return 450;
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