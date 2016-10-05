package monopolyUML;

public class Vine extends OrangeColorGroup{
	public String name="VINE STREET";
	public int cost=200;
	public int rent=16;
	public int noofhouses=0;
	public int mortgageValue=100;
	public int costofhouse=100;
	
	public Vine(){}
	
	
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
			return 16;
		if(noofhouses==1)
			return 80;
		if(noofhouses==2)
			return 220;
		if(noofhouses==3)
			return 600;
		if(noofhouses==4)
			return 800;
		if(noofhouses==5)
			return 1000;
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