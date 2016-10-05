package dice;

import java.util.*;
import javax.swing.*;

public class ThrowDice extends Thread {

    private JLabel dice1;
    private JLabel dice2;
    private JLabel text;
    private Random rg = new Random();
    private int count;
    private int counter;
    private Dice dice;
    public boolean cont;
    public Icon icon1;
    public Icon icon2;
    public int values1[]= new int[36];
    public int values2[]= new int[36];

    public ThrowDice(JLabel dice1, JLabel dice2, JLabel text, Dice dice) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.text = text;
        count = 10;
        counter=0;
        this.dice = dice;
    }

    public synchronized void run() {
        Throw();
    }

    public void Throw(){
        cont = true;
        for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    values1[counter]=i;
                    values2[counter]=j;
                    counter++;
                }
            }
        while (count > 0) {
            count--;
            int random=rg.nextInt(35) + 1;
            int num1 = values1[random];
            int num2 = values2[random];
            icon1 = new ImageIcon(Dice.class.getResource("/images/dice/" + (num1) + ".png"));
            icon2 = new ImageIcon(Dice.class.getResource("/images/dice/" + (num2) + ".png"));
            dice.setDice1(num1);
            dice.setDice2(num2);
            dice1.setIcon(icon1);
            dice2.setIcon(icon2);
            text.setText("Total: " + (num1 + num2));
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        cont = false;
    }
}
