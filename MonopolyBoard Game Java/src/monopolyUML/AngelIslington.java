package monopolyUML;

public class AngelIslington extends LightBlueColorGroup{
	public String name="THE ANGEL ISLINGTON";
	public int cost=100;
	public int rent=6;
	public int noofhouses=0;
	public int mortgageValue=50;
	public int costofhouse=50;
	
	public AngelIslington(){}
	
	
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
			return 6;
		if(noofhouses==1)
			return 30;
		if(noofhouses==2)
			return 90;
		if(noofhouses==3)
			return 270;
		if(noofhouses==4)
			return 400;
		if(noofhouses==5)
			return 550;
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