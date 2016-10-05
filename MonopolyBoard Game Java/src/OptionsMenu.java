
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Abdulsalam M.T Umar
 */
public class OptionsMenu implements ActionListener {

    JFrame f;
    JPanel bg, panel, type, win, time, turn;
    JRadioButton normal, quick, bankrupt, ninety, seventy, fifty, notime, hr1, hr2, hr3, hr4, noturn, min1, min2, min3, min4;
    ButtonGroup gr1, gr2, gr3, gr4;
    JButton save, back;
    ImageIcon bgicon;
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    Options options;

    public OptionsMenu(Options options) {
        this.options = options;
        f = new JFrame();
        panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/history.png")).getImage(), 0, 0, 500, 540, null);
                super.paintComponent(g);
            }
        };
        type = new JPanel();
        win = new JPanel();
        time = new JPanel();
        turn = new JPanel();

        save = new JButton(new ImageIcon(getClass().getResource("images/save.png")));
        back = new JButton(new ImageIcon(getClass().getResource("images/return.png")));

        normal = new JRadioButton("Normal Game", true);
        quick = new JRadioButton("Quick Game");

        bankrupt = new JRadioButton("Bankrupt", true);
        ninety = new JRadioButton("Own 90%");
        seventy = new JRadioButton("Own 70%");
        fifty = new JRadioButton("Own 50%");

        notime = new JRadioButton("No time", true);
        hr1 = new JRadioButton("1 hour");
        hr2 = new JRadioButton("1:30 hours");
        hr3 = new JRadioButton("2 hours");
        hr4 = new JRadioButton("2:30 hours");

        noturn = new JRadioButton("No time", true);
        min1 = new JRadioButton("2 minutes");
        min2 = new JRadioButton("3 minutes");
        min3 = new JRadioButton("4 munites");
        min4 = new JRadioButton("5 minutes");

        gr1 = new ButtonGroup();
        gr1.add(normal);
        gr1.add(quick);

        gr2 = new ButtonGroup();
        gr2.add(bankrupt);
        gr2.add(ninety);
        gr2.add(seventy);
        gr2.add(fifty);

        gr3 = new ButtonGroup();
        gr3.add(notime);
        gr3.add(hr1);
        gr3.add(hr2);
        gr3.add(hr2);
        gr3.add(hr4);

        gr4 = new ButtonGroup();
        gr4.add(noturn);
        gr4.add(min1);
        gr4.add(min2);
        gr4.add(min3);
        gr4.add(min4);

        type.setBorder(BorderFactory.createTitledBorder("Game type"));
        type.setLayout(new GridLayout(2, 1));
        type.setBounds(50, 50, 200, 80);
        type.setOpaque(false);
        type.add(normal);
        type.add(quick);

        win.setBorder(BorderFactory.createTitledBorder("Win type"));
        win.setLayout(new GridLayout(4, 1));
        win.setBounds(50, 130, 200, 160);
        win.setOpaque(false);
        win.add(bankrupt);
        win.add(ninety);
        win.add(seventy);
        win.add(fifty);

        time.setBorder(BorderFactory.createTitledBorder("Game time"));
        time.setLayout(new GridLayout(5, 1));
        time.setBounds(50, 290, 200, 200);
        time.setOpaque(false);
        time.add(notime);
        time.add(hr1);
        time.add(hr2);
        time.add(hr3);
        time.add(hr4);

        turn.setBorder(BorderFactory.createTitledBorder("Player turn time"));
        turn.setLayout(new GridLayout(5, 1));
        turn.setBounds(250, 50, 200, 200);
        turn.setOpaque(false);
        turn.add(noturn);
        turn.add(min1);
        turn.add(min2);
        turn.add(min3);
        turn.add(min4);

        save.setBounds(300, 320, 150, 50);
        back.setBounds(300, 400, 150, 50);

        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(500, 540));
        panel.setBounds(150, 150, 500, 540);
        panel.setBorder(raisedetched);
        panel.setOpaque(false);
        panel.add(type);
        panel.add(win);
        panel.add(time);
        panel.add(turn);
        panel.add(save);
        panel.add(back);

        BackgroundImage();

        bg.setLayout(null);
        bg.add(panel);

        setOptions();

        Dimension d = toolkit.getScreenSize();

        f.setUndecorated(true);
        f.setSize(d);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);

        save.addActionListener(this);
        back.addActionListener(this);

    }

    public void BackgroundImage() {
        bgicon = new ImageIcon(getClass().getResource("images/option.png"));
        bg = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = f.getSize();
                g.drawImage(bgicon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        bg.setOpaque(false);
        Dimension dim = new Dimension();
        dim = f.getSize();
        bg.setPreferredSize(dim);
        f.getContentPane().add(bg);
    }

    public void setOptions() {
        if (!options.isNormalGame()) {
            quick.setSelected(true);
        }
        if (options.getWin().equals("bankrupt")) {
            bankrupt.setSelected(true);
        }
        if (options.getWin().equals("90")) {
            ninety.setSelected(true);
        }
        if (options.getWin().equals("70")) {
            seventy.setSelected(true);
        }
        if (options.getWin().equals("50")) {
            fifty.setSelected(true);
        }
        if (options.getTime().equals("1")) {
            notime.setSelected(true);
        }
        if (options.getTime().equals("1:30")) {
            hr1.setSelected(true);
        }
        if (options.getTime().equals("2")) {
            hr2.setSelected(true);
        }
        if (options.getTime().equals("2:30")) {
            hr3.setSelected(true);
        }
        if (options.getTurn().equals("no")) {
            noturn.setSelected(true);
        }
        if (options.getTurn().equals("2")) {
            min1.setSelected(true);
        }
        if (options.getTurn().equals("3")) {
            min2.setSelected(true);
        }
        if (options.getTurn().equals("4")) {
            min3.setSelected(true);
        }
        if (options.getTurn().equals("5")) {
            min4.setSelected(true);
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            if (normal.isSelected()) {
                options.setNormalGame(true);
            }
            if (quick.isSelected()) {
                options.setNormalGame(false);
            }
            if (bankrupt.isSelected()) {
                options.setWin("bankrupt");
            }
            if (ninety.isSelected()) {
                options.setWin("90");
            }
            if (seventy.isSelected()) {
                options.setWin("70");
            }
            if (fifty.isSelected()) {
                options.setWin("50");
            }
            if (notime.isSelected()) {
                options.setTime("no");
            }
            if (hr1.isSelected()) {
                options.setTime("1");
            }
            if (hr2.isSelected()) {
                options.setTime("1:30");
            }
            if (hr3.isSelected()) {
                options.setTime("2");
            }
            if (hr4.isSelected()) {
                options.setTime("2:30");
            }
            if (noturn.isSelected()) {
                options.setTurn("no");
            }
            if (min1.isSelected()) {
                options.setTurn("2");
            }
            if (min2.isSelected()) {
                options.setTurn("3");
            }
            if (min3.isSelected()) {
                options.setTurn("4");
            }
            if (min4.isSelected()) {
                options.setTurn("5");
            }

            new MonopolyMenu(options);
            f.dispose();
            f = null;
        }
        if (e.getSource() == back) {
            new MonopolyMenu(options);
            f.dispose();
            f = null;
        }
    }
}
