package monopolyUML;
import javax.swing.*;
public class Player{
	//public Hastable colorGroup;
	public boolean inJail;
	public int jailcount=0;
	public int money;
	public String name;
	public int position;
	public JLabel token;
	public JLabel token2;    
	public Cell properties[]=new Cell[22];
	public Cell railroads[]=new Cell[4];
	public Cell utilities[]=new Cell[2];
	boolean chancejailcard=false;
	boolean communityjailcard=false;
    boolean isAI;
		
	public Player(){}
	
	public Player(String name,int money,int position,JLabel token,JLabel token2,boolean inJail,boolean isAI){
		this.name=name;
		this.money=money;
		this.position=position;
		this.token=token;
		this.token2=token2;
		this.inJail=inJail;
        this.isAI=isAI;
	}

    public boolean isAI(){
        return isAI;
    }
	
	public void setInJail(boolean inJail){
		this.inJail=inJail;
	}
	
	public void setInJail(int jailcount){
		this.jailcount=jailcount;
	}
	
	public void setMoney(int money){
		this.money=money;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPosition(int position){
		this.position=position;
	}
	
	public void aquiredChanceJailCard(){
		this.chancejailcard=true;
	}
	
	public void useChanceJailCard(){
		this.chancejailcard=false;
	}
	
	public boolean hasChanceJailCard(){
		return chancejailcard; 
	}
	
	public void aquiredCommunityJailCard(){
		this.communityjailcard=true;
	}
	
	public void useCommunityJailCard(){
		this.communityjailcard=false;
	}
	
	public boolean hasCommunityJailCard(){
		return communityjailcard; 
	}
	
	public int getPosition(){
		return position;
	}
	public String getName(){
		return name;
	}
	public int getMoney(){
		return money;
	}
	
	public void setJailCount(int jailcount){
		this.jailcount=jailcount;
	}
	
	public int getJailCount(){
		return jailcount;
	}
	
	public boolean inJail(){
		return inJail;
	}
	
	public void purchaseProperty(Cell cell,int pos,int amount){
		cell.setOwner(this);//Assignin property to player
		this.setMoney(this.getMoney() - amount);//deducting players money
		if(pos==5 || pos==15 || pos==25 || pos==35 ){
			addRailRoad(cell);
		}
		else if(pos==12 || pos==28 ){
			addUtility(cell);
		}
		else{
			addProperty(cell);
		}
	}
	
	public void sellProperty(Cell cell,int pos,int amount){
		cell.setOwner(null);//disowning property to player
		this.setMoney(this.getMoney() + amount);//adding players money
		if(pos==5 || pos==15 || pos==25 || pos==35 ){
			removeRailRoad(cell);
		}
		else if(pos==12 || pos==28 ){
			removeUtility(cell);
		}
		else{
			removeProperty(cell);
		}
	}
		
	public void payRentTo(Player player,int amount){
		player.setMoney(amount+player.money);
		this.money=money-amount;
	}
	
	public void payBank(int amount){
		setMoney(money-amount);
	}
	
	public int getNoOfHouses(){
		int x=0;
		for(int i=0;i<getNoOfProperties();i++){
			if(getProperties()[i].getNumberOfHouses()<5){
				x+=getProperties()[i].getNumberOfHouses();
			}
		}
		return x; 
	}
	
	public int getNoOfHotels(){
		int x=0;
		for(int i=0;i<getNoOfProperties();i++){
			if(getProperties()[i].getNumberOfHouses()>=5){
				x+=1;
			}
		}
		return x; 
	}
	
	public void addProperty(Cell cell){
		for(int i=0;i<properties.length;i++){
				if(properties[i]==null){
					properties[i]= cell;
					break;
				}				
			}
	}
	
	public void removeProperty(Cell cell){
		for(int i=0;i<properties.length;i++){
				if(properties[i]==cell){
					properties[i]= null;
					break;
				}				
			}
	}
	
	public Cell[] getProperties(){
		int count=0;
		for(int i=0;i<properties.length;i++){
			if( properties[i]!=null){
				count++;
			}
		
		}
		int z=0;
		Cell c[]=new Cell[count];
		for(int i=0;i<properties.length;i++){
			if( properties[i]!=null){
				c[z]=properties[i];
				z++;
			}
		
		}
		
		return c; 
	}
	
	public int getNoOfProperties(){
		return getProperties().length;
	}
	
	
	public void addUtility(Cell cell){
		for(int i=0;i<utilities.length;i++){
				if(utilities[i]==null){
					utilities[i]= cell;
					break;
				}				
			}
	}
	
	public void removeUtility(Cell cell){
		for(int i=0;i<utilities.length;i++){
				if(utilities[i]==cell){
					utilities[i]= null;
					break;
				}				
			}
	}
	
	public Cell[] getUtilities(){
		int count=0;
		for(int i=0;i<utilities.length;i++){
			if( utilities[i]!=null){
				count++;
			}
		
		}
		int z=0;
		Cell c[]=new Cell[count];
		for(int i=0;i<utilities.length;i++){
			if( utilities[i]!=null){
				c[z]=utilities[i];
				z++;
			}
		
		}
		
		return c; 
	}
	
	public int getNoOfUtilities(){
		return getUtilities().length;
	}

	public void addRailRoad(Cell cell){
		for(int i=0;i<railroads.length;i++){
				if(railroads[i]!=null){
					continue;
				}
				railroads[i]= cell;
				break;				
			}
	}
	
	public void removeRailRoad(Cell cell){
		for(int i=0;i<railroads.length;i++){
				if(railroads[i]==cell){
					railroads[i]= null;
					break;
				}				
			}
	}
	
	public Cell[] getRailRoads(){
		int count=0;
		for(int i=0;i<railroads.length;i++){
			if( railroads[i]!=null){
				count++;
			}
		
		}
		Cell c[]=new Cell[count];
		int z=0;
		for(int i=0;i<railroads.length;i++){
			if( railroads[i]!=null){
				c[z]=railroads[i];
				z++;
			}		
		}
		
		return c; 
	}

    public int getWorth(){
        Cell a[]=this.getProperties();
        Cell b[]=this.getUtilities();
        Cell c[]=this.getRailRoads();
        int amount=0;
        for(int i=0;i<a.length;i++){
            amount+=a[i].getCost();
            amount+=a[i].getNumberOfHouses()*a[i].getCostOfHouse();
        }
        for(int i=0;i<b.length;i++){
            amount+=b[i].getCost();
        }
        for(int i=0;i<c.length;i++){
            amount+=c[i].getCost();
        }
        amount+=this.getMoney();
        return amount;
    }
	
	public int getNoOfRailRoads(){
		return getRailRoads().length;
	}
	
}