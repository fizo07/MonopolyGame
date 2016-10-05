package dice;

public class Dice {
	public int DiceTotal;
	public int Dice1;
	public int Dice2;
	
	
	 public Dice(){}
	
    public Dice(int Dice1,int Dice2){
	 	this.Dice1=Dice1;
		this.Dice2=Dice2;
    }
	 
	 public void setDiceTotal(int DiceTotal){
	 	this.DiceTotal=DiceTotal;
	 }
	 public void setDice1(int Dice1){
	 	this.Dice1=Dice1;
	 }
	 public void setDice2(int Dice2){
	 	this.Dice2=Dice2;
	 }
	 
	public int getDice1(){
		return Dice1;
		}
	public int getDice2(){
		return Dice2;
		}
	public int getDiceTotal(){
		return Dice1+Dice2;
		}
}
