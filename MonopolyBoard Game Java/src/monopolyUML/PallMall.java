package monopolyUML;

public class PallMall extends PinkColorGroup{
	public String name="PALLMALL";
	public int cost=140;
	public int rent=10;
	public int noofhouses=0;
	public int mortgageValue=70;
	public int costofhouse=100;
	
	public PallMall(){}
	
	
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
			return 10;
		if(noofhouses==1)
			return 50;
		if(noofhouses==2)
			return 150;
		if(noofhouses==3)
			return 450;
		if(noofhouses==4)
			return 625;
		if(noofhouses==5)
			return 750;
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