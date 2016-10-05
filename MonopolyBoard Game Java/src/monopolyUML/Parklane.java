package monopolyUML;

public class Parklane extends DarkBlueColorGroup{
	public String name="PARK LANE";
	public int cost=350;
	public int rent=35;
	public int noofhouses=0;
	public int mortgageValue=175;
	public int costofhouse=200;
	
	public Parklane(){}
	
	
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
			return 35;
		if(noofhouses==1)
			return 175;
		if(noofhouses==2)
			return 500;
		if(noofhouses==3)
			return 1100;
		if(noofhouses==4)
			return 1300;
		if(noofhouses==5)
			return 1500;
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