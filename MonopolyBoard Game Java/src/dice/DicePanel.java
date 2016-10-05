package dice;

import javax.swing.*;
import java.awt.*;

public class DicePanel extends JPanel {//implements ActionListener{
	public Dice dice;
	JLabel dice1,dice2,text,text1,text2;
	JButton button;
    public DicePanel(Dice dice) {
        
		  this.dice=dice;
        dice1 = new JLabel(new ImageIcon(Dice.class.getResource("/images/dice/0.png")));
        dice2 = new JLabel(new ImageIcon(Dice.class.getResource("/images/dice/0.png")));
        button = new JButton("Throw");

        text = new JLabel("Total: Nil");
		  text1 = new JLabel("0");
		  text2 = new JLabel("0 - "+dice.Dice1);
		  setBackground(Color.red);
        setLayout(new FlowLayout());
        add(dice1);
        add(dice2);
        add(button);
        add(text);
		  add(text1);
		  add(text2);
       //button.addActionListener(this);
    }
    // public void actionPerformed(ActionEvent e) {
// 	 	
//         Timer timer = new Timer();
// 		  ThrowDice throwing=new ThrowDice(dice1, dice2, text,dice);
//         timer.scheduleAtFixedRate(throwing, 0, 200);
// 		  System.out.println("\t"+dice.getDice1()+"\t"+dice.getDice2());
//     }


}