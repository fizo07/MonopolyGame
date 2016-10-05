package monopolyUML;

public class Strand extends RedColorGroup{
	public String name="STRAND";
	public int cost=220;
	public int rent=18;
	public int noofhouses=0;
	public int mortgageValue=110;
	public int costofhouse=150;
	
	public Strand(){}
	
	
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
			return 18;
		if(noofhouses==1)
			return 90;
		if(noofhouses==2)
			return 250;
		if(noofhouses==3)
			return 700;
		if(noofhouses==4)
			return 875;
		if(noofhouses==5)
			return 1050;
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