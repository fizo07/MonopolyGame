package monopolyUML;

public class Marlborough extends OrangeColorGroup{
	public String name="MARLBOROUGH STREET";
	public int cost=180;
	public int rent=14;
	public int noofhouses=0;
	public int mortgageValue=90;
	public int costofhouse=100;
	
	public Marlborough(){}
	
	
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
			return 14;
		if(noofhouses==1)
			return 70;
		if(noofhouses==2)
			return 200;
		if(noofhouses==3)
			return 550;
		if(noofhouses==4)
			return 750;
		if(noofhouses==5)
			return 950;
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