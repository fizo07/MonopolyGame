package monopolyUML;

import javax.swing.*;

public class Cell extends JPanel{

	public Cell(){}

	public boolean isAvailable(){return true;};
	public String getName(){return null;};
	public void setAvailable(boolean available){};
	public void setOwner(Player owner){};
	public void setMortgage(boolean mortgage){};
	public Player getOwner(){return null;};
	public boolean isMortgaged(){return true;};
	public String getColorGroup(){return "";};
	public int getMortgageValue(){return 0;};
	public int getRent(){return 0;};
	public int getRent(int x){return 0;};
	public int getRent(int n,int d){return 0;};
	public int getCost(){return 0;};
	public void setNoOfHouses(int noofhouses){};
	public int getNumberOfHouses(){return 0;};
	public int getCostOfHouse(){return 0;};
	public int getPosition(){return 0;}
	public void setColorGroup(String s){};
	
	public String toString(){
		return ""; 
	}

}