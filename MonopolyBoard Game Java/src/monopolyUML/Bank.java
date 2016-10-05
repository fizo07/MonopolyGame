package monopolyUML;

import javax.swing.*;

public class Bank{
	public String name="Bank";
	public int noofHouses=32;
	public int noofHotels=12;
	
	public Bank(){
		
	}
	public void sellHouse(int no){
		if((noofHouses+no)<=32){
			this.noofHouses+=no;
		}
		else{
			JOptionPane.showMessageDialog(null,"error");
		}
	}
	public void sellHotel(int no){
		if((noofHouses+no)<=12){
			this.noofHouses+=no;
		}
		else{
			JOptionPane.showMessageDialog(null,"error");
		}
	}
	public void buyHouse(int no){
		if(noofHouses>=no){
			this.noofHouses-=no;
		}
		else{
			JOptionPane.showMessageDialog(null,"Houses not available");
		}
	}
	public void buyHotel(int no){
		if(noofHouses>=no){
			this.noofHotels-=no;
		}
		else{
			JOptionPane.showMessageDialog(null,"Hotels not available");
		}
	}
	
	public void payPlayer(Player player,int amount){
		player.setMoney(player.money+amount);
	}
	
	public void purchaseHouse(Player player,Cell cell,int no){
		if(cell.getNumberOfHouses()<5){
			player.setMoney(player.money-cell.getCostOfHouse());
			buyHouse(no);
			cell.setNoOfHouses(cell.getNumberOfHouses()+no);
		}
	}
	
	public void removeHouse(Player player,Cell cell,int no){
		if(cell.getNumberOfHouses()>0){
			player.setMoney(player.money+(cell.getCostOfHouse()/2));
			sellHouse(no);
			cell.setNoOfHouses(cell.getNumberOfHouses()-no);
			}
	}

	public int getNoOfHouses(){
		return noofHouses;
	} 
	public int getNoOfHotels(){
		return noofHotels; 
	}
	

}