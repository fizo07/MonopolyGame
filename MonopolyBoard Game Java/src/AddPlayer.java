
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import monopolyUML.*;
import java.util.*;

class AddPlayer implements ItemListener, ActionListener {

    JFrame f;
    JPanel p1, p2, p3, bg;
    JButton add, remove, start, back;
    JTextField nametext;
    JList Playerlist;
    JComboBox cb;
    JRadioButton human, computer;
    ButtonGroup RadioGroup;
    JLabel namelabel, tokenlabel, tokenimage, createlabel;
    ImageIcon bgicon;
    String tokenimages[] = {"images/Player_icons/barrow.gif", "images/Player_icons/boot.gif", "images/Player_icons/car.gif", "images/Player_icons/dog.gif", "images/Player_icons/iron.gif", "images/Player_icons/ship.gif", "images/Player_icons/thimble.gif"};
    String tokenimages2[] = {"images/Player_icons/barrow.png", "images/Player_icons/boot.png", "images/Player_icons/car.png", "images/Player_icons/dog.png", "images/Player_icons/iron.png", "images/Player_icons/ship.png", "images/Player_icons/thimble.png"};
    String tokennames[] = {"BARROW", "BOOT", "CAR", "DOG", "IRON", "SHIP", "THIMBLE"};
    Player Player[];
    Vector data = new Vector();
    Vector Playername = new Vector();
    Vector tk=new Vector();
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    Boolean type;
    Options options;
    public AddPlayer(Options options) {
        this.options=options;
        f = new JFrame();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                Dimension d = f.getSize();
                g.drawImage(new ImageIcon(getClass().getResource("images/history.png")).getImage(), 0, 0, 600, 350, null);
                super.paintComponent(g);
            }
        };
        add = new JButton("ADD", new ImageIcon(getClass().getResource("images/Edit.gif")));
        remove = new JButton("REMOVE", new ImageIcon(getClass().getResource("images/Delete.gif")));
        start = new JButton(new ImageIcon(getClass().getResource("images/gamestart_icon.gif")));
        back = new JButton(new ImageIcon(getClass().getResource("images/return.png")));
        nametext = new JTextField(7);
        Playerlist = new JList();
        Playerlist.setVisibleRowCount(5);
        Playerlist.setFixedCellWidth(200);
        Playerlist.setFixedCellHeight(25);
        Playerlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        cb = new JComboBox(tokennames);
        human = new JRadioButton("Human Player", true);
        computer = new JRadioButton("Computer Player", false);
        namelabel = new JLabel("Name");
        tokenlabel = new JLabel("Token");
        createlabel = new JLabel("Create Players...");
        tokenimage = new JLabel(new ImageIcon(getClass().getResource("images/Player_icons/barrow.gif")));
        RadioGroup = new ButtonGroup();
        RadioGroup.add(human);
        RadioGroup.add(computer);
        computer.setEnabled(false);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        p1.setLayout(null);
        p1.setBounds(50, 50, 250, 250);
        p1.setOpaque(false);
        p1.add(namelabel);
        p1.add(nametext);
        p1.add(tokenlabel);
        p1.add(cb);
        p1.add(tokenimage);
        p1.add(human);
        p1.add(computer);
        p1.add(add);
        p1.add(remove);
        namelabel.setBounds(10, 10, 100, 30);
        nametext.setBounds(120, 10, 120, 30);
        tokenlabel.setBounds(10, 50, 100, 30);
        cb.setBounds(120, 50, 120, 30);
        tokenimage.setBounds(140, 100, 100, 80);
        human.setBounds(10, 100, 120, 30);
        computer.setBounds(10, 130, 120, 30);
        add.setBounds(10, 190, 110, 30);
        remove.setBounds(130, 190, 110, 30);

        tokenimage.setBorder(raisedetched);

        nametext.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                if (nametext.getText().length() > 7) {
                    e.consume();
                }
            }
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
            }
        });

        Playerlist.setBorder(BorderFactory.createTitledBorder("Please add Players to start game"));
        Playerlist.setBackground(Color.LIGHT_GRAY);
        Playerlist.setForeground(Color.BLACK);
        Playerlist.setFont(new Font("verdana",Font.BOLD,15));
        nametext.setToolTipText("Type player name here");

        p2.setBounds(300, 50, 250, 250);
        p2.setLayout(new FlowLayout());
        p2.setOpaque(false);
        p2.add(new JScrollPane(Playerlist));
        p2.add(start);

        p3.setBorder(raisedetched);
        p3.setLayout(null);
        p3.setBounds(50, 250, 600, 350);
        p3.setOpaque(false);
        p3.add(createlabel);
        createlabel.setBounds(50, 0, 300, 50);
        Font font = new Font("cooper black", Font.BOLD, 20);
        createlabel.setFont(font);
        p3.add(p1);
        p3.add(p2);

        BackgroundImage();
        bg.setLayout(null);
        bg.add(p3);
        bg.add(back);
        back.setBounds(100, 650, 150, 50);

        Dimension d = toolkit.getScreenSize();

        f.setUndecorated(true);
        f.setSize(d);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);

        cb.addItemListener(this);
        start.addActionListener(this);
        add.addActionListener(this);
        remove.addActionListener(this);
        back.addActionListener(this);
    }

    public void BackgroundImage() {
        bgicon = new ImageIcon(getClass().getResource("images/back2.jpg"));
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

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cb) {
            tokenimage.setIcon(new ImageIcon(getClass().getResource(tokenimages[cb.getSelectedIndex()])));
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            if (data.size() > 1) {
                Player = new Player[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    Vector cast = (Vector) data.elementAt(i);
                    boolean playertype;
                    if (cast.elementAt(3).toString().equals("true")) {
                        playertype = true;
                    } else {
                        playertype = false;
                    }
                    Player[i] = new Player(cast.elementAt(0).toString(), 1500, 0, new JLabel(new ImageIcon(getClass().getResource(cast.elementAt(1).toString()))), new JLabel(new ImageIcon(getClass().getResource(cast.elementAt(2).toString()))), false, playertype);
                }
                Thread t = new Thread(new loadingSplash());
                t.start();
                new MonopolyBoard(Player,options);
                f.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please add at least 2 Players to Start game!");
            }
        }
        if (e.getSource() == add) {
            if (nametext.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Enter Player Name");
            } else {
                if (data.size() < 5) {
                    if (computer.isSelected()) {
                        type = true;
                    } else {
                        type = false;
                    }
                    Vector info = new Vector();
                    info.add(nametext.getText());
                    info.add(tokenimages[cb.getSelectedIndex()]);
                    info.add(tokenimages2[cb.getSelectedIndex()]);
                    info.add(type);
                    data.add(info);
                    String form="";
                    if(type){
                        form=" <COMPUTER>";
                    }else{
                        form=" <HUMAN>";
                    }
                    //tk.add(cb.getSelectedIndex());
                    Playername.add(nametext.getText()+ form);
                    Playerlist.setListData(Playername);
                    nametext.setText("");                    
                    computer.setEnabled(true);
                //new loadingSplash();
                }else{
                    JOptionPane.showMessageDialog(null,"You have exceeded maximum number of players!");
                }
            }
        }
        if (e.getSource() == remove) {
            try {
                data.removeElementAt(Playerlist.getSelectedIndex());
                Playername.removeElementAt(Playerlist.getSelectedIndex());
                Playerlist.setListData(Playername);
                if(Playername.size()<1){
                    human.setSelected(true);
                    computer.setSelected(false);
                    computer.setEnabled(false);
                }
            } catch (Exception ae) {
                JOptionPane.showMessageDialog(null, "Please Select a player to remove!");
            }
        }
        if (e.getSource() == back) {
            f.hide();
            MonopolyMenu m = new MonopolyMenu(options);
            f.dispose();
        }
    }

//    public static void main(String args[]) {
//        AddPlayer ap = new AddPlayer();
//    }
}