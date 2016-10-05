//*********
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.image.*;
import java.text.*;
import dice.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.JSpinner.*;
import javax.swing.filechooser.*;
import mediaPlayer.Media;
import monopolyUML.*;

class MonopolyBoard implements ActionListener {

    JFrame f;
    JPanel Board, Center, Street[], Deed, bg, Deedstatus, Actionpanel, PlayerInfo, CurPlayers;
    //JButton Move;
    JLabel timelabel;
    JLabel dice1, dice2, Dicetext, turntext;
    JButton Dicebutton, Dicemove;
    JLabel Detail, ownerlabel, statuslabel, rentlabel, houselabel, costlabel, owner, status, rent, house, cost;//status labels
    JButton build, sell, mortgage, unmortgage, trade;
    ImageIcon bgicon;
    JTextArea history;
    JTabbedPane infotab, playertab;
    Cell Cell[], com[];
    JPanel houseimage[];
    Media media;
    Player Player[];
    Bank bank;
    Sound sound;
    GameAI GameAI;
    Options options;
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    Dice DiceControl;
    boolean throwable = true;
    int Delay;
    boolean isTurn;
    //Timer Runtimer;
    int turn = 0;
    int goMoney = 200;
    int help = 1;
    private boolean aiDecision;

    public MonopolyBoard(Player Player[], Options options) {
        this.options = options;
        this.Player = Player;
        f = new JFrame();
        Board = new JPanel();
        Center = new JPanel();
        Street = new JPanel[40];
        houseimage = new JPanel[40];
        Cell = new Cell[40];
        DiceControl = new Dice();
        bank = new Bank();
        Deed = new JPanel();
        sound = new Sound();
        GameAI = new GameAI();

        BackgroundImage();
        setMonopolyBoard();
        setFrame();
        showTitleDeed atd = new showTitleDeed();
        atd.createListener();
        addListeners();

        runTimer();
        startGame();
        printCells();

    }

    private void addListeners() {
        trade.addActionListener(this);
        build.addActionListener(this);
        mortgage.addActionListener(this);
        sell.addActionListener(this);
        unmortgage.addActionListener(this);
    }

    //arranginging street panels
    public void arrangePanels() {
        Dimension d = Board.getSize();
        int x = 9;
        int y = 0;
        int a = 9;
        int b = 0;
        for (int i = 0; i < 40; i++) {
            Street[i] = new JPanel();
            houseimage[i] = new JPanel();
            houseimage[i].setOpaque(false);
            if (i == 0) {
                Board.add(Street[i]);
                Street[i].setBounds((int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height));
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i == 10) {
                Board.add(Street[i]);
                Street[i].setBounds(0, (int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height));
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i == 20) {
                Board.add(Street[i]);
                Street[i].setBounds(0, 0, (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height));
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i == 30) {
                Board.add(Street[i]);
                Street[i].setBounds((int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height), 0, (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height));
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i > 0 && i < 10) {
                Board.add(Street[i]);
                Board.add(houseimage[i]);
                Street[i].setBounds((int) BoardDimension.getWidth(d.height) + (int) BoardDimension.getHeight(d.height) * (9 - i), (int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getHeight(d.height), (int) BoardDimension.getWidth(d.height));
                houseimage[i].setBounds((int) BoardDimension.getWidth(d.height) + (int) BoardDimension.getHeight(d.height) * (9 - i), (int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height) - 15, (int) BoardDimension.getHeight(d.height), 15);
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i > 10 && i < 20) {
                Board.add(Street[i]);
                Board.add(houseimage[i]);
                Street[i].setBounds(0, (int) BoardDimension.getWidth(d.height) + (int) (BoardDimension.getHeight(d.height) * --x), (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getHeight(d.height));
                houseimage[i].setBounds((int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height) + (int) (BoardDimension.getHeight(d.height) * --a), 15, (int) BoardDimension.getHeight(d.height));
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i > 20 && i < 30) {
                Board.add(Street[i]);
                Board.add(houseimage[i]);
                Street[i].setBounds((int) BoardDimension.getWidth(d.height) + (int) BoardDimension.getHeight(d.height) * ((9 + i) - 30), 0, (int) BoardDimension.getHeight(d.height), (int) BoardDimension.getWidth(d.height));
                houseimage[i].setBounds((int) BoardDimension.getWidth(d.height) + (int) BoardDimension.getHeight(d.height) * ((9 + i) - 30), (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getHeight(d.height), 15);
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
            if (i > 30 && i < 40) {
                Board.add(Street[i]);
                Board.add(houseimage[i]);
                Street[i].setBounds((int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height) + (int) (BoardDimension.getHeight(d.height) * y++), (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getHeight(d.height));
                houseimage[i].setBounds((int) BoardDimension.getCenterSize(d.height) + (int) BoardDimension.getWidth(d.height) - 15, (int) BoardDimension.getWidth(d.height) + (int) (BoardDimension.getHeight(d.height) * b++), 15, (int) BoardDimension.getHeight(d.height));
                Street[i].setLayout(new GridLayout(1, 1));
                Street[i].add(Cell[i]);
            }
        }
    }//end panel arragement

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == trade) {
            makeTrade();
        }
        if (e.getSource() == build) {
            buildHouse(turn);
        }
        if (e.getSource() == mortgage) {
            playMortgage();
        }
        if (e.getSource() == sell) {
            menu();
        }
        if (e.getSource() == unmortgage) {
            GameDetails();
        }

    }

    public void BackgroundImage() {
        bgicon = new ImageIcon(getClass().getResource("images/back1.png"));
        bg = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                //Scale image to size of component
                Dimension d = f.getSize();
                g.drawImage(bgicon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        bg.setOpaque(false);
        Dimension dim = new Dimension();
        dim = toolkit.getScreenSize();
        bg.setPreferredSize(dim);
        f.getContentPane().add(bg);
    }

    //int x=820;
    //int y=820;
// 	public void movePlayer(){
// 
// 	// 	ActionListener ac=new ActionListener(){
// // 			public void actionPerformed(ActionEvent e){
// 				if(x>10 && y==820){
// 					player.setBounds(x,y,100,100);
// 					--x;
// 					System.out.println(x);
// 				}
    // if(x==10 && y>10){
// 					player.setBounds(x,y,100,100);
// 					--y;
// 					System.out.println("\t"+y);
// 				}
// 				if(y==10 && x<820){
// 					player.setBounds(x,y,100,100);
// 					++x;
// 					System.out.println("\t\t"+x);
// 				}
// 				if(x==820 && y<820){
// 					player.setBounds(x,y,100,100);
// 					y++;
// 				}
// 				
// 			}
// 		};
// 		Runtimer = new Timer(Delay,ac);
// 		Runtimer.start();
// 	}
    private void setFrame() {
        setTitle();
        setDeed();
        bg.setLayout(null);
        //bg.add(player);
        bg.add(DicePanel());
        bg.add(Deed);
        bg.add(Board);
        bg.add(FunctionPanel());
        bg.add(InfoTab());
        makePlayersTab(Player);
        Dimension d = toolkit.getScreenSize();
        f.setResizable(false);
        f.setUndecorated(true);
        //f.setModal(true);
        f.setSize(d);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_VERT | f.MAXIMIZED_HORIZ);
    }

    private void setTitle() {
        Detail = new JLabel(new ImageIcon(getClass().getResource("images/details.png")));
        Detail.setBounds(Board.getWidth() + 10, 0, 400, 40);
        bg.add(Detail);
    }

    private void setDeed() {
        BoardDimension bd = new BoardDimension();
        Dimension dim = toolkit.getScreenSize();
        bd.setDimension(dim);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Deed.setBorder(raisedetched);
        Deed.setBounds(bd.DeedPosition().width, bd.DeedPosition().height, bd.DeedSize().width * 2, bd.DeedSize().height);
        Deed.setLayout(new GridLayout(1, 2, 5, 10));
        Deed.setVisible(false);
        Deed.setBackground(Color.black);
    }

    private void setPropertyStatus(int i) {
        Deedstatus = new JPanel();
        ownerlabel = new JLabel(" Owner");
        statuslabel = new JLabel(" Status");
        rentlabel = new JLabel(" Rent");
        houselabel = new JLabel(" House(s)");
        costlabel = new JLabel(" Cost");
        owner = new JLabel();
        status = new JLabel();
        rent = new JLabel();
        house = new JLabel();
        cost = new JLabel();

        Font font = new Font("papyrus", Font.BOLD + Font.ITALIC, 12);

        ownerlabel.setFont(font);
        statuslabel.setFont(font);
        rentlabel.setFont(font);
        houselabel.setFont(font);
        costlabel.setFont(font);

        ownerlabel.setForeground(Color.RED);
        statuslabel.setForeground(Color.RED);
        rentlabel.setForeground(Color.RED);
        houselabel.setForeground(Color.RED);
        costlabel.setForeground(Color.RED);

        updatePropertyStatus(i);

        Deedstatus.add(ownerlabel);
        Deedstatus.add(owner);
        Deedstatus.add(statuslabel);
        Deedstatus.add(status);
        Deedstatus.add(rentlabel);
        Deedstatus.add(rent);
        Deedstatus.add(houselabel);
        Deedstatus.add(house);
        Deedstatus.add(costlabel);
        Deedstatus.add(cost);
        Deedstatus.setLayout(new GridLayout(5, 2, 0, 0));

        Deed.add(Deedstatus);
    }

    private JPanel FunctionPanel() {
        Actionpanel = new JPanel();
        build = new JButton(new ImageIcon(getClass().getResource("images/buttons/build.png")));
        sell = new JButton(new ImageIcon(getClass().getResource("images/buttons/menu.png")));
        trade = new JButton(new ImageIcon(getClass().getResource("images/buttons/trade.png")));
        mortgage = new JButton(new ImageIcon(getClass().getResource("images/buttons/mortgage.png")));
        unmortgage = new JButton(new ImageIcon(getClass().getResource("images/buttons/details.png")));
        Actionpanel.add(sell);
        Actionpanel.add(build);
        Actionpanel.add(trade);
        Actionpanel.add(mortgage);
        Actionpanel.add(unmortgage);
        Actionpanel.setLayout(new GridLayout(1, 5, 10, 10));
        Actionpanel.setOpaque(false);
        Actionpanel.setBounds(5, Board.getHeight() + 10, Board.getHeight(), 50);
        return Actionpanel;
    }

    private JPanel HistoryPanel() {
        final JPanel panel = new JPanel();
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        final JPanel p = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(393, (int) (Board.getHeight() * 0.5) - 30);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        JPanel p1 = new JPanel();
        history = new JTextArea();
        JLabel title = new JLabel("HISTORY");
        timelabel = new JLabel();
        JScrollPane js = new JScrollPane(history);
        history.setBackground(Color.LIGHT_GRAY);
        history.setForeground(new Color(177, 0, 2));
        history.setLineWrap(true);
        history.setEditable(false);
        Font font = new Font("cooper black", Font.BOLD + Font.ITALIC, 25);
        Font font1 = new Font("system", Font.BOLD, 20);
        title.setFont(font);
        timelabel.setFont(font1);
        panel.add(p);
        panel.setLayout(new GridLayout(1, 1));
        p.setLayout(null);
        p.add(title);
        p.add(timelabel);
        p.add(js);

        title.setBounds(30, 10, 200, 30);
        timelabel.setBounds(290, 10, 100, 30);
        js.setBounds(20, 40, 350, (int) (Board.getHeight() * 0.5) - 100);

        p.setOpaque(false);
        panel.setSize(340, (int) (Board.getHeight() * 0.5) - 30);
        return panel;
    }

    private JPanel ChatPanel() {
        final JPanel panel = new JPanel();
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        final JPanel p = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(393, (int) (Board.getHeight() * 0.5) - 30);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        panel.add(p);
        panel.setLayout(new GridLayout(1, 1));
        panel.setSize(393, (int) (Board.getHeight() * 0.5) - 30);
        p.setOpaque(false);

        //more code here
        return panel;
    }

    private JPanel MusicPanel() {
        final JPanel panel = new JPanel();
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        final JPanel p = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(393, (int) (Board.getHeight() * 0.5) - 30);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        final JCheckBox bgmusic = new JCheckBox("Mute background music");
        final JPanel p1 = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back.jpg")).getImage(), 0, 0, 300, 250, null);
                super.paintComponent(g);
            }
        };
        final JList playlist = new JList();
        final Vector list = new Vector();
        final Vector path = new Vector();
        JButton add = new JButton("Add", new ImageIcon(getClass().getResource("images/Edit.gif")));
        JButton rem = new JButton("Remove", new ImageIcon(getClass().getResource("images/Delete.gif")));
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        playlist.setBorder(BorderFactory.createTitledBorder("Add music files to playlist"));
        playlist.setPreferredSize(new Dimension(250, 150));
        playlist.setBackground(Color.lightGray);
        p1.setPreferredSize(new Dimension(250, 250));
        p1.setBorder(raisedetched);
        p1.setOpaque(false);
        p1.add(playlist);
        p1.add(add);
        p1.add(rem);

        //sound.musicPlayer();
        //Component visual=sound.music.getVisualComponent();
        //Component control=sound.music.getControlPanelComponent();
        media = new Media();

        //Component c=sound.music.getControlPanelComponent();
        //control.add(c);
        p1.setBounds(50, 20, 300, 200);
        bgmusic.setBounds(50, 230, 200, 30);
        media.setBounds(50, 270, 300, 150);
        media.setOpaque(false);

        p.setLayout(null);
        p.add(p1);
        p.add(bgmusic);
        p.add(media);

        //p.add(control);
        //p.add(sound.music.getControlPanelComponent());

        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    FileFilter filter = new FileFilter() {

                        @Override
                        public boolean accept(File f) {
                            String name = f.getName();
                            if (f.isDirectory()) {
                                return true;
                            }
                            if (name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".wma")) {
                                return true;
                            }
                            return false;
                        }

                        @Override
                        public String getDescription() {
                            return "Music files";
                        }
                    };
                    chooser.setAcceptAllFileFilterUsed(false);
                    chooser.setFileFilter(filter);

                    int returnVal = chooser.showOpenDialog(p1);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        path.add(chooser.getSelectedFile().getPath());
                        list.add(chooser.getSelectedFile().getName());
                        playlist.setListData(list);
                        media.setPlaylist(path);
                    }
                } catch (Exception Exception) {
                }
            }
        });

        rem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    list.removeElementAt(playlist.getSelectedIndex());
                    path.removeElementAt(playlist.getSelectedIndex());
                    playlist.setListData(list);
                    media.setPlaylist(path);
                } catch (Exception ae) {
                }
            }
        });

        bgmusic.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (bgmusic.isSelected()) {
                    sound.clip.stop();
                } else {
                    sound.clip.start();
                }
            }
        });
        panel.add(p);
        panel.setLayout(new GridLayout(1, 1));
        panel.setSize(393, (int) (Board.getHeight() * 0.5) - 30);
        p.setOpaque(false);

        //more code here
        return panel;
    }

    private JTabbedPane InfoTab() {
        infotab = new JTabbedPane();
        infotab.addTab("History", new JScrollPane(HistoryPanel()));
        //infotab.addTab("Chat", new JScrollPane(ChatPanel()));
        infotab.addTab("Music", new JScrollPane(MusicPanel()));
        infotab.setBounds(Board.getWidth() + 10, (Board.getHeight() + 120) - ((int) (Board.getHeight() * 0.5)), 400, (int) (Board.getHeight() * 0.5));
        infotab.setOpaque(false);
        return infotab;
    }

    private JPanel PlayerDetails(int q) {
        final String dir = "images/tittle_deeds/";
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        PlayerInfo = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(393, (int) (Board.getHeight() * 0.5) + 20);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        PlayerInfo.setOpaque(false);
        JPanel p1 = new JPanel();
        Cell com[];
        Font font = new Font("cooper black", Font.BOLD, 35);
        Font font1 = new Font("cooper black", Font.BOLD, 30);
        JLabel moneytext = new JLabel(String.valueOf(Player[q].getMoney()));
        moneytext.setFont(font);
        moneytext.setForeground(new Color(177, 0, 2));
        final JLabel housetext = new JLabel();
        housetext.setFont(font1);
        housetext.setForeground(Color.DARK_GRAY);
        final JLabel hoteltext = new JLabel();
        hoteltext.setFont(font1);
        hoteltext.setForeground(Color.DARK_GRAY);
        final JLabel houseimage = new JLabel(new ImageIcon(getClass().getResource("images/house.png")));
        final JLabel hotelimage = new JLabel(new ImageIcon(getClass().getResource("images/hotel.png")));
        final JLabel moneyimage = new JLabel(new ImageIcon(getClass().getResource("images/cash.png")));
        JPanel j1 = new JPanel();
        JPanel j2 = new JPanel();
        JPanel jailcards = new JPanel();

        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        com = new Cell[40];
        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < com.length; i++) {
            com[i] = new PropertyCell();
            com[i].setLayout(new GridLayout(1, 1));
            com[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(com[i]);
            }
            com[i].setBorder(raisedetched);
            final int z = i;
            com[i].addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent e) {
                    Deed.setVisible(true);
                    Deed.removeAll();
                    Deed.add(TitleDeed(z, true));
                    setPropertyStatus(z);
                    Deed.revalidate();
                }

                public void mouseExited(MouseEvent m) {
                    Deed.removeAll();
                    Deed.setVisible(false);
                    Deed.revalidate();
                }
            });
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);
        for (int i = 0; i < com.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
                final int z = i;
                com[i].addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        Deed.setVisible(true);
                        Deed.removeAll();
                        Deed.add(TitleDeed(z, true));
                        setPropertyStatus(z);
                        Deed.revalidate();
                    }

                    public void mouseExited(MouseEvent m) {
                        Deed.removeAll();
                        Deed.setVisible(false);
                        Deed.revalidate();
                    }
                });
            }

        }
        for (int i = 0; i < com.length; i++) {
            if (i == 12 || i == 28) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
                final int z = i;
                com[i].addMouseListener(new MouseAdapter() {

                    JDialog panel = new JDialog();

                    public void mouseEntered(MouseEvent e) {
                        Deed.setVisible(true);
                        Deed.removeAll();
                        Deed.add(TitleDeed(z, true));
                        setPropertyStatus(z);
                        Deed.revalidate();
                    }

                    public void mouseExited(MouseEvent m) {
                        Deed.removeAll();
                        Deed.setVisible(false);
                        Deed.revalidate();
                    }
                });
            }

        }
        j1.setBorder(raisedetched);
        j1.setOpaque(false);
        j2.setBorder(raisedetched);
        j2.setOpaque(false);
        jailcards.setLayout(new GridLayout(1, 1, 10, 10));
        jailcards.setOpaque(false);
        jailcards.add(j1);
        jailcards.add(j2);
        p1.setBounds(20, 20, 100, 350);
        moneyimage.setBounds(130, 20, 100, 100);
        houseimage.setBounds(140, 130, 55, 40);
        hotelimage.setBounds(140, 170, 55, 40);
        moneytext.setBounds(220, 40, 100, 50);
        housetext.setBounds(220, 140, 100, 30);
        hoteltext.setBounds(220, 180, 100, 30);
        jailcards.setBounds(140, 230, 150, 50);
        PlayerInfo.setLayout(null);
        PlayerInfo.add(p1);
        PlayerInfo.add(moneyimage);
        PlayerInfo.add(moneytext);
        PlayerInfo.add(houseimage);
        PlayerInfo.add(housetext);
        PlayerInfo.add(hotelimage);
        PlayerInfo.add(hoteltext);
        PlayerInfo.add(jailcards);

        for (int k = 0; k < com.length; k++) {
            for (int j = 0; j < Player.length; j++) {
                if (Cell[k].getOwner() == Player[j]) {
                    com[k].setOwner(Player[j]);
                }
            }
            com[k].setMortgage(Cell[k].isMortgaged());
            com[k].setNoOfHouses(Cell[k].getNumberOfHouses());
        }

        for (int i = 0; i < com.length; i++) {

            if (com[i].getOwner() == Player[q]) {
                if (com[i].isMortgaged()) {
                    addImage(com[i], dir + "mortgage.png", 25, 25);
                    com[i].revalidate();
                    com[i].updateUI();
                } else {
                    addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                    com[i].revalidate();
                    com[i].updateUI();
                    PlayerInfo.revalidate();
                    PlayerInfo.updateUI();
                }
            } else if (com[i].getOwner() != Player[q] && com[i].getOwner() != null) {
                com[i].setBackground(Color.gray);
                com[i].setOpaque(true);
            }

        }
        if (Player[q].hasChanceJailCard()) {
            addImage(j1, "images/chance/3.png", 70, 50);
            j1.revalidate();
            j1.updateUI();
        } else if (Player[q].hasCommunityJailCard()) {
            addImage(j2, "images/Community_chest/15.png", 70, 50);
            j2.revalidate();
            j2.updateUI();
        }

        moneytext.setText(String.valueOf(Player[q].getMoney()));
        housetext.setText(String.valueOf(Player[q].getNoOfHouses()));
        hoteltext.setText(String.valueOf(Player[q].getNoOfHotels()));
        return PlayerInfo;
    }

    private JPanel PlayDetails(int q) {
        final String dir = "images/tittle_deeds/";
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        PlayerInfo = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(330, 500);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        PlayerInfo.setOpaque(false);
        JPanel p1 = new JPanel();
        Cell cod[];
        Font font = new Font("cooper black", Font.BOLD, 35);
        Font font1 = new Font("cooper black", Font.BOLD, 30);
        JLabel moneytext = new JLabel(String.valueOf(Player[q].getMoney()));
        JLabel perc = new JLabel();
        JLabel worth = new JLabel();
        moneytext.setFont(font);
        moneytext.setForeground(new Color(177, 0, 2));
        final JLabel housetext = new JLabel();
        housetext.setFont(font1);
        housetext.setForeground(Color.DARK_GRAY);
        final JLabel hoteltext = new JLabel();
        hoteltext.setFont(font1);
        hoteltext.setForeground(Color.DARK_GRAY);
        final JLabel houseimg = new JLabel(new ImageIcon(getClass().getResource("images/house.png")));
        final JLabel hotelimage = new JLabel(new ImageIcon(getClass().getResource("images/hotel.png")));
        final JLabel moneyimage = new JLabel(new ImageIcon(getClass().getResource("images/cash.png")));
        JPanel j1 = new JPanel();
        JPanel j2 = new JPanel();
        JPanel jailcards = new JPanel();

        double asd = GameAI.probOfProperties(Cell, Player[q]);

        perc.setFont(new Font("cooper black", Font.BOLD, 15));
        worth.setFont(new Font("cooper black", Font.BOLD, 15));
        perc.setText("Board Ownership : " + String.valueOf(asd) + "%");
        worth.setText("Total worth : $" + Player[q].getWorth());


        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        cod = new Cell[40];
        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < cod.length; i++) {
            cod[i] = new PropertyCell();
            cod[i].setLayout(new GridLayout(1, 1));
            cod[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(cod[i]);
            }
            cod[i].setBorder(raisedetched);
            final int z = i;
            cod[i].addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent m) {
                }
            });
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);
        for (int i = 0; i < cod.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p1.add(cod[i]);
                cod[i].setBorder(raisedetched);
                final int z = i;
                cod[i].addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent m) {
                    }
                });
            }
        }
        for (int i = 0; i < cod.length; i++) {
            if (i == 12 || i == 28) {
                p1.add(cod[i]);
                cod[i].setBorder(raisedetched);
                final int z = i;
                cod[i].addMouseListener(new MouseAdapter() {

                    JDialog panel = new JDialog();

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent m) {
                    }
                });
            }

        }
        j1.setBorder(raisedetched);
        j1.setOpaque(false);
        j2.setBorder(raisedetched);
        j2.setOpaque(false);
        jailcards.setLayout(new GridLayout(1, 1, 10, 10));
        jailcards.setOpaque(false);
        jailcards.add(j1);
        jailcards.add(j2);
        p1.setBounds(20, 20, 100, 350);
        moneyimage.setBounds(130, 20, 100, 100);
        houseimg.setBounds(140, 130, 55, 40);
        hotelimage.setBounds(140, 170, 55, 40);
        moneytext.setBounds(220, 40, 100, 50);
        housetext.setBounds(220, 140, 100, 30);
        hoteltext.setBounds(220, 180, 100, 30);
        jailcards.setBounds(140, 230, 150, 50);
        perc.setBounds(60, 370, 200, 50);
        worth.setBounds(60, 390, 200, 50);
        PlayerInfo.setLayout(null);
        PlayerInfo.add(p1);
        PlayerInfo.add(moneyimage);
        PlayerInfo.add(moneytext);
        PlayerInfo.add(houseimg);
        PlayerInfo.add(housetext);
        PlayerInfo.add(hotelimage);
        PlayerInfo.add(hoteltext);
        PlayerInfo.add(jailcards);
        PlayerInfo.add(perc);
        PlayerInfo.add(worth);

        for (int k = 0; k < cod.length; k++) {
            for (int j = 0; j < Player.length; j++) {
                if (Cell[k].getOwner() == Player[j]) {
                    cod[k].setOwner(Player[j]);
                }
            }
            cod[k].setMortgage(Cell[k].isMortgaged());
            cod[k].setNoOfHouses(Cell[k].getNumberOfHouses());
        }

        for (int i = 0; i < cod.length; i++) {

            if (cod[i].getOwner() == Player[q]) {
                if (cod[i].isMortgaged()) {
                    addImage(cod[i], dir + "mortgage.png", 25, 25);
                    cod[i].revalidate();
                    cod[i].updateUI();
                } else {
                    addImage(cod[i], dir + BoardComponent.Deeds[i], 25, 25);
                    cod[i].revalidate();
                    cod[i].updateUI();
                    PlayerInfo.revalidate();
                    PlayerInfo.updateUI();
                }
            } else if (cod[i].getOwner() != Player[q] && cod[i].getOwner() != null) {
                cod[i].setBackground(Color.gray);
                cod[i].setOpaque(true);
            }

        }
        if (Player[q].hasChanceJailCard()) {
            addImage(j1, "images/chance/3.png", 70, 50);
            j1.revalidate();
            j1.updateUI();
        } else if (Player[q].hasCommunityJailCard()) {
            addImage(j2, "images/Community_chest/15.png", 70, 50);
            j2.revalidate();
            j2.updateUI();
        }


        moneytext.setText(String.valueOf(Player[q].getMoney()));
        housetext.setText(String.valueOf(Player[q].getNoOfHouses()));
        hoteltext.setText(String.valueOf(Player[q].getNoOfHotels()));
        return PlayerInfo;
    }

    private JPanel GamerDetails() {
        final String dir = "images/tittle_deeds/";
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        PlayerInfo = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(350, (int) (Board.getHeight() * 0.5) + 20);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        PlayerInfo.setOpaque(false);

        JPanel p1 = new JPanel();
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        com = new Cell[40];
        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < com.length; i++) {
            com[i] = new PropertyCell();
            com[i].setLayout(new GridLayout(1, 1));
            com[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(com[i]);
            }
            com[i].setBorder(raisedetched);
            final int z = i;
            com[i].addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent m) {
                }
            });
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);
        for (int i = 0; i < com.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
                final int z = i;
                com[i].addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent m) {
                    }
                });
            }

        }
        for (int i = 0; i < com.length; i++) {
            if (i == 12 || i == 28) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
                final int z = i;
                com[i].addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent m) {
                    }
                });
            }
        }

        return null;
    }

    private JPanel BankDetails() {
        final String dir = "images/tittle_deeds/";
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/history.png"));
        PlayerInfo = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = new Dimension(393, (int) (Board.getHeight() * 0.5) + 20);
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                super.paintComponent(g);
            }
        };
        PlayerInfo.setOpaque(false);
        JPanel p1 = new JPanel();
        Cell com[];
        Font font = new Font("cooper black", Font.BOLD, 35);
        Font font1 = new Font("cooper black", Font.BOLD, 30);
        JLabel moneytext = new JLabel("0");
        moneytext.setFont(font);
        moneytext.setForeground(new Color(177, 0, 2));
        final JLabel housetext = new JLabel("0");
        housetext.setFont(font1);
        housetext.setForeground(Color.DARK_GRAY);
        final JLabel hoteltext = new JLabel("0");
        hoteltext.setFont(font1);
        hoteltext.setForeground(Color.DARK_GRAY);
        final JLabel houseimage = new JLabel(new ImageIcon(getClass().getResource("images/house.png")));
        final JLabel hotelimage = new JLabel(new ImageIcon(getClass().getResource("images/hotel.png")));
        final JLabel moneyimage = new JLabel(new ImageIcon(getClass().getResource("images/safe.png")));

        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        com = new Cell[40];
        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < com.length; i++) {
            com[i] = new PropertyCell();
            com[i].setLayout(new GridLayout(1, 1));
            com[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(com[i]);
            }
            com[i].setBorder(raisedetched);
            final int z = i;
            com[i].addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent e) {
                    Deed.setVisible(true);
                    Deed.removeAll();
                    Deed.add(TitleDeed(z, true));
                    setPropertyStatus(z);
                    Deed.revalidate();
                }

                public void mouseExited(MouseEvent m) {
                    Deed.removeAll();
                    Deed.setVisible(false);
                    Deed.revalidate();
                }
            });
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);
        for (int i = 0; i < com.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
                final int z = i;
                com[i].addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        Deed.setVisible(true);
                        Deed.removeAll();
                        Deed.add(TitleDeed(z, true));
                        setPropertyStatus(z);
                        Deed.revalidate();
                    }

                    public void mouseExited(MouseEvent m) {
                        Deed.removeAll();
                        Deed.setVisible(false);
                        Deed.revalidate();
                    }
                });
            }

        }
        for (int i = 0; i < com.length; i++) {
            if (i == 12 || i == 28) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
                final int z = i;
                com[i].addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        Deed.setVisible(true);
                        Deed.removeAll();
                        Deed.add(TitleDeed(z, true));
                        setPropertyStatus(z);
                        Deed.revalidate();
                    }

                    public void mouseExited(MouseEvent m) {
                        Deed.removeAll();
                        Deed.setVisible(false);
                        Deed.revalidate();
                    }
                });
            }

        }
        p1.setBounds(20, 20, 100, 350);
        moneyimage.setBounds(130, 20, 150, 150);
        houseimage.setBounds(140, 180, 55, 40);
        hotelimage.setBounds(140, 220, 55, 40);
        //moneytext.setBounds(220,40,150,150);
        housetext.setBounds(220, 190, 100, 30);
        hoteltext.setBounds(220, 230, 100, 30);
        PlayerInfo.setLayout(null);
        PlayerInfo.add(p1);
        PlayerInfo.add(moneyimage);
        PlayerInfo.add(moneytext);
        PlayerInfo.add(houseimage);
        PlayerInfo.add(housetext);
        PlayerInfo.add(hotelimage);
        PlayerInfo.add(hoteltext);

        for (int k = 0; k < com.length; k++) {
            for (int j = 0; j < Player.length; j++) {
                if (Cell[k].getOwner() == Player[j]) {
                    com[k].setOwner(Player[j]);
                }
            }
        }

        for (int i = 0; i < com.length; i++) {

            if (com[i].getOwner() == null) {
                //adding image
                addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                com[i].revalidate();
                com[i].updateUI();
                PlayerInfo.revalidate();
                PlayerInfo.updateUI();
            }

        }
        housetext.setText(String.valueOf(bank.getNoOfHouses()));
        hoteltext.setText(String.valueOf(bank.getNoOfHotels()));
        return PlayerInfo;
    }

    private void playTrade(final Player trader) {
        final Vector v1 = new Vector();
        final Vector v2 = new Vector();
        final String dir = "images/tittle_deeds/";
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        final JDialog trade = new JDialog();
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 400, 400, null);
                super.paintComponent(g);
            }
        };
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back4.png")).getImage(), 0, 0, 180, 400, null);
                super.paintComponent(g);
            }
        };
        JPanel p3 = new JPanel();
        JPanel t1 = new JPanel();
        JPanel t2 = new JPanel();
        JPanel all = new JPanel();
        final JPanel image = new JPanel();
        JLabel token1 = new JLabel("$" + Player[turn].getMoney(), Player[turn].token.getIcon(), JLabel.CENTER);
        JLabel token2 = new JLabel("$" + trader.getMoney(), trader.token.getIcon(), JLabel.CENTER);
        //JPanel image=new JPanel();
        JLabel amount = new JLabel();
        final JLabel price = new JLabel();
        JLabel cash = new JLabel("<--Cash Transfer-->");
        JLabel title = new JLabel(new ImageIcon(getClass().getResource("images/trade.png")));
        JButton offer = new JButton("Offer");
        JButton cancel = new JButton("Cancel");
        final Cell com[];
        final Cell com1[];
        final SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Player[turn].getMoney(), 5);
        final SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, trader.getMoney(), 5);
        final JSpinner sp1 = new JSpinner(model);
        final JSpinner sp2 = new JSpinner(model1);


        com = new Cell[40];
        com1 = new Cell[40];

        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < com.length; i++) {
            com[i] = new PropertyCell();
            com[i].setLayout(new GridLayout(1, 1));
            com[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(com[i]);
            }
            com[i].setBorder(raisedetched);
            final int z = i;
            com[i].addMouseListener(new MouseAdapter() {

                boolean b = true;

                public void mouseEntered(MouseEvent e) {
                    image.removeAll();
                    image.add(tradeImage(z, 150, 180));
                    image.revalidate();
                    price.setText("$" + String.valueOf(Cell[z].getCost()));
                }

                public void mouseExited(MouseEvent m) {
                    image.removeAll();
                    image.setBackground(Color.GRAY);
                    image.revalidate();
                }

                public void mouseClicked(MouseEvent me) {
                    if (b) {
                        if (com[z].getOwner() == Player[turn]) {
                            if (trader.isAI()) {
                                if (v1.size() < 1) {
                                    v1.add(Cell[z]);
                                    com[z].removeAll();
                                    addImage(com[z], "images/trade1.png", 25, 25);
                                    com[z].revalidate();
                                    b = false;
                                } else {
                                    JOptionPane.showMessageDialog(null, "You cannot trade more than one property with computer!\nRemove other selected properties and try again");
                                }
                            } else {
                                v1.add(Cell[z]);
                                com[z].removeAll();
                                addImage(com[z], "images/trade1.png", 25, 25);
                                com[z].revalidate();
                                b = false;
                            }
                        }
                    } else {
                        if (com[z].getOwner() == Player[turn]) {
                            v1.remove(Cell[z]);
                            com[z].removeAll();
                            addImage(com[z], dir + BoardComponent.Deeds[z], 25, 25);
                            com[z].revalidate();
                            b = true;
                        }
                    }

                }
            });
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);
        for (int i = 0; i < com.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
            }

        }
        for (int i = 0; i < com.length; i++) {
            if (i == 12 || i == 28) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
            }

        }
        //second cells
        p3.setLayout(new GridLayout(10, 3, 5, 5));
        p3.setOpaque(false);
        for (int i = 0; i < com1.length; i++) {
            com1[i] = new PropertyCell();
            com1[i].setLayout(new GridLayout(1, 1));
            com1[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel n = new JPanel();
                n.setOpaque(false);
                p3.add(n);
            } else {
                p3.add(com1[i]);
            }
            com1[i].setBorder(raisedetched);
            final int z = i;
            com1[i].addMouseListener(new MouseAdapter() {

                boolean b = true;

                public void mouseEntered(MouseEvent e) {
                    image.removeAll();
                    image.add(tradeImage(z, 150, 180));
                    image.revalidate();
                    price.setText("$" + String.valueOf(Cell[z].getCost()));
                }

                public void mouseExited(MouseEvent m) {
                    image.removeAll();
                    image.revalidate();
                    image.setBackground(Color.GRAY);
                    image.revalidate();
                }

                public void mouseClicked(MouseEvent me) {
                    if (b) {
                        if (com1[z].getOwner() == trader) {
                            if (trader.isAI()) {
                                if (v2.size() < 1) {
                                    v2.add(Cell[z]);
                                    com1[z].removeAll();
                                    addImage(com1[z], "images/trade2.png", 20, 20);
                                    com1[z].revalidate();
                                    b = false;
                                } else {
                                    JOptionPane.showMessageDialog(null, "You cannot trade more than one property with computer!\nRemove other selected properties and try again");
                                }
                            } else {
                                v2.add(Cell[z]);
                                com1[z].removeAll();
                                addImage(com1[z], "images/trade2.png", 20, 20);
                                com1[z].revalidate();
                                b = false;
                            }
                        }
                    } else {
                        if (com1[z].getOwner() == trader) {
                            v2.remove(Cell[z]);
                            com1[z].removeAll();
                            addImage(com1[z], dir + BoardComponent.Deeds[z], 20, 20);
                            com1[z].revalidate();
                            b = true;
                        }
                    }

                }
            });
        }
        JPanel n = new JPanel();
        n.setOpaque(false);
        p3.add(n);
        for (int i = 0; i < com1.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p3.add(com1[i]);
                com1[i].setBorder(raisedetched);

            }

        }
        for (int i = 0; i < com1.length; i++) {
            if (i == 12 || i == 28) {
                p3.add(com1[i]);
                com1[i].setBorder(raisedetched);
                final int z = i;

            }

        }

        offer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (v1.size() != 0 || v2.size() != 0) {
                    Cell a[] = new Cell[v1.size()];
                    for (int i = 0; i < a.length; i++) {
                        a[i] = (Cell) v1.elementAt(i);
                    }
                    Cell b[] = new Cell[v2.size()];
                    for (int i = 0; i < b.length; i++) {
                        b[i] = (Cell) v2.elementAt(i);
                    }
                    tradeProperty(Player[turn], model.getNumber().intValue(), a, trader, model1.getNumber().intValue(), b);
                    trade.setVisible(false);

                    //updating player details
                    updatePlayerDetails();
                } else {
                    JOptionPane.showMessageDialog(null, "Select Items to trade!");
                }
            }
        });
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
            }
        });
        t1.setOpaque(false);
        t2.setOpaque(false);
        p2.setOpaque(false);
        all.setOpaque(false);

        token1.setBorder(raisedetched);
        token1.setBounds(3, 5, 97, 40);
        token1.setBackground(Color.WHITE);
        token1.setOpaque(true);
        token2.setBorder(raisedetched);
        token2.setBounds(0, 5, 100, 40);
        token2.setBackground(Color.WHITE);
        token2.setOpaque(true);

        price.setFont(new Font("cooper black", Font.BOLD, 18));
        cash.setFont(new Font("cooper black", Font.BOLD, 15));
        sp1.setFont(new Font("cooper black", 0, 20));
        sp2.setFont(new Font("cooper black", 0, 20));
        offer.setFont(new Font("cooper black", 0, 18));
        cancel.setFont(new Font("cooper black", 0, 18));

        offer.setBackground(Color.WHITE);
        cancel.setBackground(Color.WHITE);
        offer.setForeground(Color.GRAY);
        cancel.setForeground(Color.GRAY);

        image.setBorder(raisedetched);
        image.setBackground(Color.GRAY);
        image.setBounds(15, 50, 150, 180);
        title.setBounds(10, 10, 160, 40);
        price.setBounds(65, 230, 50, 20);
        cash.setBounds(20, 260, 140, 20);
        sp1.setBounds(0, 360, 75, 30);
        sp2.setBounds(5, 360, 75, 30);
        offer.setBounds(40, 320, 100, 30);
        cancel.setBounds(40, 360, 100, 30);

        p1.setBounds(10, 50, 90, 300);
        p3.setBounds(0, 50, 90, 300);
        t1.setBounds(10, 0, 100, 400);
        p2.setBounds(110, 0, 180, 400);
        t2.setBounds(295, 0, 100, 400);
        all.setBounds(0, 0, 400, 400);
        image.setLayout(new GridLayout(1, 1));
        t1.setLayout(null);
        t2.setLayout(null);
        p2.setLayout(null);
        all.setLayout(null);
        all.add(t1);
        all.add(p2);
        all.add(t2);
        t1.add(p1);
        t1.add(token1);
        t1.add(sp1);
        t2.add(sp2);
        t2.add(p3);
        t2.add(token2);
        p2.add(title);
        p2.add(cash);
        p2.add(image);
        p2.add(price);
        p2.add(offer);
        p2.add(cancel);

        Dimension d = Board.getSize();

        Cback.setBounds(0, 0, 400, 400);
        Cback.add(all);
        Cback.setOpaque(false);
        Cback.setLayout(null);
        trade.setLayout(null);
        trade.add(Cback);
        trade.setBounds((int) (d.height / 2) - 200, (int) (d.height / 2) - 200, 400, 400);

        for (int k = 0; k < com.length; k++) {
            for (int j = 0; j < Player.length; j++) {
                if (Cell[k].getOwner() == Player[j]) {
                    com[k].setOwner(Player[j]);
                    com1[k].setOwner(Player[j]);
                }
            }
        }

        for (int i = 0; i < com.length; i++) {

            if (com[i].getOwner() == Player[turn]) {
                addImage(com[i], dir + BoardComponent.Deeds[i], 20, 20);
                com[i].revalidate();
                com[i].updateUI();
            }
            if (com1[i].getOwner() == trader) {
                addImage(com1[i], dir + BoardComponent.Deeds[i], 20, 20);
                com1[i].revalidate();
                com1[i].updateUI();
            }

        }
        trade.setUndecorated(true);
        trade.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        trade.setResizable(false);
        trade.setModal(true);
        trade.setVisible(true);

    }

    public void makeTrade() {
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        final JDialog trade = new JDialog();
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 400, 400, null);
                super.paintComponent(g);
            }
        };
        String a[] = new String[Player.length];
        for (int i = 0; i < Player.length; i++) {
            if (Player[i] != Player[turn]) {
                a[i] = Player[i].getName();
            }
        }
        final JComboBox cb = new JComboBox(a);
        JPanel all = new JPanel();
        JLabel title = new JLabel("Select player to trade!");
        JButton ok = new JButton("Ok");
        JButton can = new JButton("Cancel");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < Player.length; i++) {
                    if (Player[i].getName().equals(cb.getSelectedItem())) {
                        trade.setVisible(false);
                        playTrade(Player[i]);
                        break;
                    }
                }
            }
        });
        can.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
            }
        });
        cb.setFont(new Font("cooper black", 0, 20));
        title.setFont(new Font("cooper black", 0, 18));
        ok.setFont(new Font("cooper black", 0, 25));
        can.setFont(new Font("cooper black", 0, 25));

        ok.setBackground(Color.WHITE);
        can.setBackground(Color.WHITE);
        //ok.setOpaque(true);

        all.setLayout(new GridLayout(4, 1, 20, 20));
        all.add(title);
        all.add(cb);
        all.add(ok);
        all.add(can);
        all.setBounds(125, 100, 150, 200);
        all.setOpaque(false);

        Dimension d = Board.getSize();

        Cback.setBounds(0, 0, 400, 400);
        Cback.add(all, BorderLayout.CENTER);
        Cback.setOpaque(false);
        Cback.setLayout(null);
        trade.setLayout(null);
        trade.add(Cback);
        trade.setBounds((int) (d.height / 2) - 200, (int) (d.height / 2) - 200, 400, 400);

        trade.setUndecorated(true);
        trade.setDefaultCloseOperation(trade.DO_NOTHING_ON_CLOSE);
        trade.setResizable(false);
        trade.setModal(true);
        trade.setVisible(true);

    }

    private void playerMoney() {
        for (int i = 0; i < bg.getComponents().length; i++) {
            if (bg.getComponents()[i] == CurPlayers) {
                bg.remove(CurPlayers);
            }
        }
        CurPlayers = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back.jpg")).getImage(), 0, 0, Board.getWidth(), 50, null);
                super.paintComponent(g);
            }
        };
        JLabel lab[] = new JLabel[Player.length];
        JLabel title = new JLabel("  Players :--->");
        Border redline = BorderFactory.createLineBorder(Color.black);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border compound = BorderFactory.createCompoundBorder(redline, raisedetched);
        title.setFont(new Font("cooper black", Font.BOLD, 15));
        title.setHorizontalAlignment(JLabel.RIGHT);
        CurPlayers.add(title);
        for (int i = 0; i < Player.length; i++) {
            lab[i] = new JLabel(Player[i].getName() + " - $" + Player[i].getMoney(), Player[i].token2.getIcon(), JLabel.CENTER);
            lab[i].setVerticalTextPosition(JLabel.CENTER);
            lab[i].setHorizontalTextPosition(JLabel.RIGHT);
            lab[i].setIconTextGap(1);
            lab[i].setFont(new Font("cooper black", 0, 15));
            lab[i].setBorder(raisedetched);
            lab[i].setOpaque(true);
            if (i == turn) {
                lab[i].setBackground(Color.GRAY);
            } else {
                lab[i].setBackground(Color.LIGHT_GRAY);
            }

            CurPlayers.add(lab[i]);
        }

        CurPlayers.setOpaque(false);
        CurPlayers.setBorder(compound);
        CurPlayers.setBackground(Color.white);
        CurPlayers.setLayout(new GridLayout(1, Player.length + 1, 0, 0));
        CurPlayers.setBounds(5, Board.getHeight() + 70, Board.getWidth(), 50);

        bg.add(CurPlayers);
        bg.revalidate();
    }

    private void makePlayersTab(Player Player[]) {
        playertab = new JTabbedPane();
        playertab.addTab("", new ImageIcon(getClass().getResource("images/bank2.png")), new JScrollPane(BankDetails()));
        for (int i = 0; i < Player.length; i++) {
            playertab.addTab(Player[i].getName(), Player[i].token2.getIcon(), new JScrollPane(PlayerDetails(i)));
        }
        playertab.setTabPlacement(JTabbedPane.TOP);
        playertab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        playertab.setBounds((Board.getWidth() + 10), 45, 400, (int) (Board.getHeight() * 0.5) + 70);
        playertab.revalidate();
        //playertab.updateUI();

        bg.add(playertab);
        playerMoney();
        bg.revalidate();
        bg.updateUI();
    }

    private void buildHouse(final int current) {
        final Vector v = new Vector();
        final String dir = "images/tittle_deeds/";
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        final JDialog trade = new JDialog();
        final JButton add = new JButton(new ImageIcon(getClass().getResource("images/buildhouse.png")));
        final JButton sellhouse = new JButton(new ImageIcon(getClass().getResource("images/sellhouse.png")));
        final JButton done = new JButton("Done");
        final JButton cancel = new JButton("Cancel");
        final JLabel title = new JLabel(Player[current].getName() + " - $" + Player[current].getMoney(), Player[current].token.getIcon(), JLabel.CENTER);
        final JLabel image = new JLabel();
        final JLabel name = new JLabel();
        final JLabel housecost = new JLabel();
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 400, 400, null);
                super.paintComponent(g);
            }
        };
        JPanel p1 = new JPanel();
        JPanel all = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/construction.png")).getImage(), 0, 0, 350, 300, null);
                super.paintComponent(g);
            }
        };
        final Cell com[] = new Cell[40];
        p1.setLayout(new GridLayout(8, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < com.length; i++) {
            com[i] = new PropertyCell();
            com[i].setLayout(new GridLayout(1, 1));
            com[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(com[i]);
            }
            com[i].setBorder(raisedetched);
            final int z = i;
            com[i].addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent m) {
                }

                public void mouseClicked(MouseEvent me) {
                    v.clear();
                    v.add(z);
                    image.removeAll();
                    name.setText(Cell[z].getName());
                    housecost.setText("Cost of House - $" + String.valueOf(Cell[z].getCostOfHouse()));
                    if (Cell[z].getNumberOfHouses() == 0) {
                        image.setIcon(new ImageIcon(getClass().getResource("images/house/no_house.png")));
                    } else if (Cell[z].getNumberOfHouses() == 1) {
                        image.setIcon(new ImageIcon(getClass().getResource("images/house/1_1.png")));
                    } else if (Cell[z].getNumberOfHouses() == 2) {
                        image.setIcon(new ImageIcon(getClass().getResource("images/house/2_1.png")));
                    } else if (Cell[z].getNumberOfHouses() == 3) {
                        image.setIcon(new ImageIcon(getClass().getResource("images/house/3_1.png")));
                    } else if (Cell[z].getNumberOfHouses() == 4) {
                        image.setIcon(new ImageIcon(getClass().getResource("images/house/4_1.png")));
                    } else if (Cell[z].getNumberOfHouses() == 5) {
                        image.setIcon(new ImageIcon(getClass().getResource("images/house/5_1.png")));
                    }
                    image.revalidate();
                }
            });
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);

        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (v.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No property is selected");
                } else {
                    int no = Integer.parseInt(String.valueOf(v.elementAt(0)));
                    if (com[no].getComponentCount() < 1) {
                        JOptionPane.showMessageDialog(null, "You cannot build on this property");
                    } else {
                        if (Cell[no].getNumberOfHouses() <= 5) {
                            if (Player[current].getMoney() >= Cell[no].getCostOfHouse()) {
                                bank.purchaseHouse(Player[current], Cell[no], 1);
                                houseimage[no].removeAll();
                                if (no > 0 && no < 10) {
                                    if (Cell[no].getNumberOfHouses() == 1) {
                                        addImage(houseimage[no], "images/house/1_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 2) {
                                        addImage(houseimage[no], "images/house/2_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 3) {
                                        addImage(houseimage[no], "images/house/3_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 4) {
                                        addImage(houseimage[no], "images/house/4_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 5) {
                                        addImage(houseimage[no], "images/house/5_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                }
                                if (no > 10 && no < 20) {
                                    if (Cell[no].getNumberOfHouses() == 1) {
                                        addImage(houseimage[no], "images/house/1_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 2) {
                                        addImage(houseimage[no], "images/house/2_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 3) {
                                        addImage(houseimage[no], "images/house/3_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 4) {
                                        addImage(houseimage[no], "images/house/4_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 5) {
                                        addImage(houseimage[no], "images/house/5_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                }
                                if (no > 20 && no < 30) {
                                    if (Cell[no].getNumberOfHouses() == 1) {
                                        addImage(houseimage[no], "images/house/1_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 2) {
                                        addImage(houseimage[no], "images/house/2_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 3) {
                                        addImage(houseimage[no], "images/house/3_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 4) {
                                        addImage(houseimage[no], "images/house/4_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 5) {
                                        addImage(houseimage[no], "images/house/5_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                }
                                if (no > 30 && no < 40) {
                                    if (Cell[no].getNumberOfHouses() == 1) {
                                        addImage(houseimage[no], "images/house/1_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 2) {
                                        addImage(houseimage[no], "images/house/2_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 3) {
                                        addImage(houseimage[no], "images/house/3_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 4) {
                                        addImage(houseimage[no], "images/house/4_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                    if (Cell[no].getNumberOfHouses() == 5) {
                                        addImage(houseimage[no], "images/house/5_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                                    }
                                }
                                sound.playBuild();
                                trade.setVisible(false);
                                buildHouse(current);
                            } else {
                                JOptionPane.showMessageDialog(null, "You have insufficient funds !");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Maximum number of houses reached");
                        }
                    }
                }
            }
        });
        sellhouse.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (v.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No property is selected");
                } else {
                    int no = Integer.parseInt(String.valueOf(v.elementAt(0)));
                    bank.removeHouse(Player[current], Cell[no], 1);
                    houseimage[no].removeAll();
                    if (no > 0 && no < 10) {
                        if (Cell[no].getNumberOfHouses() == 1) {
                            addImage(houseimage[no], "images/house/1_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 2) {
                            addImage(houseimage[no], "images/house/2_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 3) {
                            addImage(houseimage[no], "images/house/3_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 4) {
                            addImage(houseimage[no], "images/house/4_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 5) {
                            addImage(houseimage[no], "images/house/5_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }

                    }
                    if (no > 10 && no < 20) {
                        if (Cell[no].getNumberOfHouses() == 1) {
                            addImage(houseimage[no], "images/house/1_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 2) {
                            addImage(houseimage[no], "images/house/2_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 3) {
                            addImage(houseimage[no], "images/house/3_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 4) {
                            addImage(houseimage[no], "images/house/4_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 5) {
                            addImage(houseimage[no], "images/house/5_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                    }
                    if (no > 20 && no < 30) {
                        if (Cell[no].getNumberOfHouses() == 1) {
                            addImage(houseimage[no], "images/house/1_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 2) {
                            addImage(houseimage[no], "images/house/2_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 3) {
                            addImage(houseimage[no], "images/house/3_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 4) {
                            addImage(houseimage[no], "images/house/4_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 5) {
                            addImage(houseimage[no], "images/house/5_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                    }
                    if (no > 30 && no < 40) {
                        if (Cell[no].getNumberOfHouses() == 1) {
                            addImage(houseimage[no], "images/house/1_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 2) {
                            addImage(houseimage[no], "images/house/2_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 3) {
                            addImage(houseimage[no], "images/house/3_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 4) {
                            addImage(houseimage[no], "images/house/4_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                        if (Cell[no].getNumberOfHouses() == 5) {
                            addImage(houseimage[no], "images/house/5_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                        }
                    }
                    sound.playBuild();
                    trade.setVisible(false);
                    buildHouse(current);
                }

            }
        });
        done.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
                //updating player details
                updatePlayerDetails();
            }
        });
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
            }
        });

        p1.setBounds(120, 50, 90, 250);
        done.setBounds(90, 360, 100, 30);
        cancel.setBounds(210, 360, 100, 30);
        title.setBounds(25, 10, 350, 35);
        title.setBorder(raisedetched);
        title.setOpaque(true);
        image.setBounds(220, 100, 120, 50);
        image.setBorder(raisedetched);
        add.setBounds(230, 170, 100, 50);
        sellhouse.setBounds(230, 230, 100, 50);
        name.setBounds(220, 50, 120, 20);
        housecost.setBounds(220, 70, 120, 20);

        all.setLayout(null);
        all.add(p1);
        all.add(image);
        all.add(name);
        all.add(housecost);
        all.add(add);
        all.add(sellhouse);
        all.setBounds(25, 50, 350, 300);
        all.setOpaque(false);

        int brown = 0;
        int lightblue = 0;
        int pink = 0;
        int orange = 0;
        int red = 0;
        int yellow = 0;
        int green = 0;
        int darkblue = 0;

        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i].getOwner() == Player[current]) {
                if (Cell[i].isMortgaged()) {
                    //nothing happens
                } else {
                    if (Cell[i].getColorGroup().equals("Brown")) {
                        brown++;
                    } else if (Cell[i].getColorGroup().equals("Light Blue")) {
                        lightblue++;
                    } else if (Cell[i].getColorGroup().equals("Pink")) {
                        pink++;
                    } else if (Cell[i].getColorGroup().equals("Orange")) {
                        orange++;
                    } else if (Cell[i].getColorGroup().equals("Red")) {
                        red++;
                    } else if (Cell[i].getColorGroup().equals("Yellow")) {
                        yellow++;
                    } else if (Cell[i].getColorGroup().equals("Green")) {
                        green++;
                    } else if (Cell[i].getColorGroup().equals("Dark Blue")) {
                        darkblue++;
                    }
                }
            }
        }

        for (int k = 0; k < com.length; k++) {
            for (int j = 0; j < Player.length; j++) {
                if (Cell[k].getOwner() == Player[j]) {
                    com[k].setOwner(Player[j]);
                }
            }
            com[k].setMortgage(Cell[k].isMortgaged());
            com[k].setNoOfHouses(Cell[k].getNumberOfHouses());
        //com[k].setColorGroup(Cell[k].getColorGroup());
        }

        for (int i = 0; i < com.length; i++) {
            if (com[i].getOwner() == Player[current]) {
                if (com[i].isMortgaged()) {
                    com[i].setBackground(Color.LIGHT_GRAY);
                    com[i].setOpaque(true);
                } else {

                    if (Cell[i].getColorGroup().equals("Brown") && brown == 2) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Light Blue") && lightblue == 3) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Pink") && pink == 3) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Orange") && orange == 3) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Red") && red == 3) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Yellow") && yellow == 3) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Green") && green == 3) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else if (Cell[i].getColorGroup().equals("Dark Blue") && darkblue == 2) {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 25, 25);
                        com[i].revalidate();
                    } else {
                        com[i].setBackground(Color.LIGHT_GRAY);
                        com[i].setOpaque(true);
                    }
                }
            }
        }

        Dimension d = Board.getSize();

        Cback.setBounds(0, 0, 400, 400);
        Cback.add(all, BorderLayout.CENTER);
        Cback.add(title);
        Cback.add(done);
        Cback.add(cancel);
        Cback.setOpaque(false);
        Cback.setLayout(null);
        trade.setLayout(null);
        trade.add(Cback);
        trade.setBounds((int) (d.height / 2) - 200, (int) (d.height / 2) - 200, 400, 400);

        trade.setUndecorated(true);
        trade.setDefaultCloseOperation(trade.DO_NOTHING_ON_CLOSE);
        trade.setResizable(false);
        trade.setModal(true);
        trade.setVisible(true);

    }

    private void playMortgage() {
        final Vector v = new Vector();
        final int no = 0;
        final String dir = "images/tittle_deeds/";
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        final JDialog trade = new JDialog();
        final JButton add = new JButton(new ImageIcon(getClass().getResource("images/buttons/mortgage.png")));
        final JButton sell = new JButton(new ImageIcon(getClass().getResource("images/buttons/unmortgage.png")));
        final JButton done = new JButton("Done");
        final JButton cancel = new JButton("Cancel");
        final JLabel title = new JLabel(Player[turn].getName() + " - $" + Player[turn].getMoney(), Player[turn].token.getIcon(), JLabel.CENTER);
        final JPanel image = new JPanel();
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 400, 400, null);
                super.paintComponent(g);
            }
        };
        JPanel p1 = new JPanel();
        JPanel all = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/mortgage.png")).getImage(), 0, 0, 350, 300, null);
                super.paintComponent(g);
            }
        };
        Cell com[] = new Cell[40];
        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setOpaque(false);
        for (int i = 0; i < com.length; i++) {
            com[i] = new PropertyCell();
            com[i].setLayout(new GridLayout(1, 1));
            com[i].setOpaque(false);
            if (i == 0 || i == 2 || i == 7 || i == 10 || i == 17 || i == 20 || i == 22 || i == 30 || i == 33 || i == 36 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28) {
                //nothing happens
            } else if (i == 4) {
                JPanel p = new JPanel();
                p.setOpaque(false);
                p1.add(p);
            } else {
                p1.add(com[i]);
            }
            com[i].setBorder(raisedetched);
        }
        JPanel p = new JPanel();
        p.setOpaque(false);
        p1.add(p);
        for (int i = 0; i < com.length; i++) {
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
            }
        }
        for (int i = 0; i < com.length; i++) {
            if (i == 12 || i == 28) {
                p1.add(com[i]);
                com[i].setBorder(raisedetched);
            }

        }

        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (v.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Select property!");
                } else {
                    int no = Integer.parseInt(String.valueOf(v.elementAt(0)));
                    if (Cell[no].isMortgaged()) {
                        JOptionPane.showMessageDialog(null, "Property is already mortgaged !");
                    } else {
                        if (Cell[no].getNumberOfHouses() > 0) {
                            JOptionPane.showMessageDialog(null, "Improved property cannot be mortgagaed!");
                        } else {
                            Cell[no].setMortgage(true);
                            bank.payPlayer(Player[turn], Cell[no].getCost() / 2);
                            sound.playMortgage();
                        }
                    }
                    trade.setVisible(false);
                    playMortgage();
                }
            }
        });
        sell.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (v.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Select property!");
                } else {
                    int no = Integer.parseInt(String.valueOf(v.elementAt(0)));
                    if (!Cell[no].isMortgaged()) {
                        JOptionPane.showMessageDialog(null, "You cannot unmortgage this property ! !");
                    } else {
                        int value = (int) ((Cell[no].getCost() / 2) + ((Cell[no].getCost() / 2) * 0.1));
                        if (value <= Player[turn].getMoney()) {
                            Cell[no].setMortgage(false);
                            Player[turn].payBank(value);
                            sound.playUmMortgage();
                        } else {
                            JOptionPane.showMessageDialog(null, "You have insufficient funds to unmortgage this property!");
                        }
                    }
                    trade.setVisible(false);
                    playMortgage();
                }
            }
        });
        done.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
                //update player details
                bg.remove(playertab);
                bg.revalidate();
                makePlayersTab(Player);
            }
        });
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
                //update player details
                bg.remove(playertab);
                bg.revalidate();
                makePlayersTab(Player);
            }
        });

        p1.setBounds(120, 31, 80, 270);
        done.setBounds(90, 360, 100, 30);
        cancel.setBounds(210, 360, 100, 30);
        title.setBounds(25, 10, 350, 35);
        title.setBorder(raisedetched);
        title.setOpaque(true);
        image.setLayout(new GridLayout(1, 1));
        image.setBounds(230, 50, 100, 130);
        image.setBorder(raisedetched);
        add.setBounds(230, 200, 100, 40);
        sell.setBounds(230, 250, 100, 40);

        all.setLayout(null);
        all.add(p1);
        all.add(image);
        all.add(add);
        all.add(sell);
        all.setBounds(25, 50, 350, 300);
        all.setOpaque(false);

        for (int k = 0; k < com.length; k++) {
            for (int j = 0; j < Player.length; j++) {
                if (Cell[k].getOwner() == Player[j]) {
                    com[k].setOwner(Player[j]);
                }
            }
            com[k].setMortgage(Cell[k].isMortgaged());
            com[k].setNoOfHouses(Cell[k].getNumberOfHouses());
        }

        for (int i = 0; i < com.length; i++) {
            if (com[i].getOwner() == Player[turn]) {
                if (com[i].getNumberOfHouses() == 0) {
                    if (com[i].isMortgaged()) {
                        addImage(com[i], dir + "mortgage.png", 20, 20);
                        com[i].revalidate();
                        com[i].updateUI();
                    } else {
                        addImage(com[i], dir + BoardComponent.Deeds[i], 20, 20);
                        com[i].revalidate();
                        com[i].updateUI();
                    }
                    final int z = i;
                    com[i].addMouseListener(new MouseAdapter() {

                        public void mouseEntered(MouseEvent e) {
                        }

                        public void mouseExited(MouseEvent m) {
                        }

                        public void mouseClicked(MouseEvent me) {
                            v.clear();
                            v.add(z);
                            image.removeAll();
                            image.add(tradeImage(z, 100, 130));
                            image.revalidate();
                        }
                    });
                } else {
                    com[i].setBackground(Color.DARK_GRAY);
                    com[i].setOpaque(false);
                    com[i].revalidate();
                }
            }
        }

        Dimension d = Board.getSize();

        Cback.setBounds(0, 0, 400, 400);
        Cback.add(all, BorderLayout.CENTER);
        Cback.add(title);
        Cback.add(done);
        Cback.add(cancel);
        Cback.setOpaque(false);
        Cback.setLayout(null);
        trade.setLayout(null);
        trade.add(Cback);
        trade.setBounds((int) (d.height / 2) - 200, (int) (d.height / 2) - 200, 400, 400);

        trade.setUndecorated(true);
        trade.setDefaultCloseOperation(trade.DO_NOTHING_ON_CLOSE);
        trade.setResizable(false);
        trade.setModal(true);
        trade.setVisible(true);
    }

    private void setMonopolyBoard() {
        BoardDimension bd = new BoardDimension();
        Dimension dim = toolkit.getScreenSize();
        bd.setDimension(dim);
        Board.setBackground(Color.black);
        Board.setSize(bd.boardsize(), bd.boardsize());
        Dimension d = Board.getSize();
        Center.setBounds((int) BoardDimension.getWidth(d.height), (int) BoardDimension.getWidth(d.height), (int) BoardDimension.getCenterSize(d.height), (int) BoardDimension.getCenterSize(d.height));
        addImage(Center, "images/street_images/center.png", (int) BoardDimension.getCenterSize(d.height), (int) BoardDimension.getCenterSize(d.height));
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        //Board.setBorder(raisedetched);
        Board.setLayout(null);
        CreateCell();
        arrangePanels();
        Board.add(Center);
    }

    public void addImage(JPanel cp, String url, int w, int h) {
        try {
            ImageIcon img;
            img = createImageIcon(url);
            cp.setLayout(new GridLayout(1, 1));
            cp.add(new JLabel(new ImageIcon(getScaledImage(img.getImage(), w, h))));
        } catch (Exception e) {
        }
    }

    public Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public ImageIcon createImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        return new ImageIcon(imgURL);
    }

    public JLabel PlayerIcon(String s) {
        ImageIcon img = createImageIcon(s);
        BoardDimension bd = new BoardDimension();
        Dimension d = Board.getSize();
        bd.setDimension(d);
        JLabel lb = new JLabel(new ImageIcon(getScaledImage(img.getImage(), (int) (bd.getWidth(d.height) * 0.3), (int) (bd.getWidth(d.height) * 0.25))));
        lb.setOpaque(false);
        return lb;
    }

    public JPanel TitleDeed(int i, boolean b) {
        String dir = "images/tittle_deeds/";
        final JPanel p = new JPanel();
        BoardDimension bd = new BoardDimension();
        Dimension dim = toolkit.getScreenSize();
        bd.setDimension(dim);
        if (b == true) {
            addImage(p, dir + BoardComponent.Deeds[i], bd.DeedSize().width, bd.DeedSize().height);
        } else {
            addImage(p, dir + BoardComponent.own[i], bd.DeedSize().width, bd.DeedSize().height);
        }
////////////////////
        p.setBounds(0, 0, bd.DeedSize().width, bd.DeedSize().width);
        //p.setBackground(Color.black);
        return p;
    }

    public JPanel tradeImage(int i, int w, int h) {
        String dir = "images/tittle_deeds/";
        final JPanel p = new JPanel();
        Dimension bd = new Dimension(w, h);
        addImage(p, dir + BoardComponent.Deeds[i], (int) bd.getWidth(), (int) bd.getHeight());
        return p;
    }

    public JPanel DicePanel() {
        final ImageIcon icon = new ImageIcon(getClass().getResource("images/back.jpg"));
        final JPanel p = new JPanel();
        final JPanel p1 = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = f.getSize();
                g.drawImage(icon.getImage(), 0, 0, 150, 90, null);
                super.paintComponent(g);
            }
        };
        final JPanel p3 = new JPanel() {

            protected void paintComponent(Graphics g) {
                Dimension d = f.getSize();
                g.drawImage(icon.getImage(), 0, 0, 150, 90, null);
                super.paintComponent(g);
            }
        };
        final JPanel p2 = new JPanel();
        dice1 = new JLabel(new ImageIcon(Dice.class.getResource("/images/dice/0.png")));
        dice2 = new JLabel(new ImageIcon(Dice.class.getResource("/images/dice/0.png")));
        Dicebutton = new JButton(new ImageIcon(getClass().getResource("images/Spinner.gif")));
        Dicemove = new JButton("Move");
        final JButton endturn = new JButton(new ImageIcon(getClass().getResource("images/endturn.png")));
        Dicetext = new JLabel("Total: Nil");
        turntext = new JLabel(Player[turn].getName() + " to roll", Player[turn].token.getIcon(), JLabel.CENTER);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Font font = new Font("cooper black", Font.BOLD, 13);
        turntext.setFont(font);
        turntext.setForeground(Color.BLACK);
        Dicemove.setEnabled(false);
        endturn.setFont(new Font("cooper black", Font.BOLD, 25));
        endturn.setBackground(Color.WHITE);
        endturn.setBounds(25, 15, 100, 60);
        p.setBackground(Color.red);
        p.setBounds((int) (Board.getWidth() * 0.5) - 75, (int) (Board.getWidth() * 0.65), 150, 135);
        p.setLayout(null);
        p1.setLayout(new FlowLayout());
        p1.setOpaque(false);
        p1.add(dice1);
        p1.add(dice2);
        p1.add(Dicebutton);
        p1.add(Dicemove);
        p1.setBounds(0, 45, 150, 100);
        p2.setBounds(0, 0, 150, 45);
        p3.setOpaque(false);
        p3.setLayout(null);
        p3.setBounds(0, 45, 150, 100);
        p3.add(endturn);
        p2.add(turntext);
        p2.setBorder(raisedetched);
        p.setBorder(raisedetched);
        p.setOpaque(false);
        p.add(p2);
        p.add(p1);
        Dicebutton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (throwable) {
                    try {
                        rollDice();
                        Dicebutton.setEnabled(false);
                        Dicemove.setEnabled(true);
                    } catch (Exception ex) {
                    }
                }
            }
        });
        Dicemove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                movePlayer(DiceControl.getDiceTotal(), Player[turn].getPosition());
                if (DiceControl.getDice1() != DiceControl.getDice2()) {
                    p.remove(p1);
                    p.revalidate();
                    p.add(p3);
                    p.revalidate();
                    p.updateUI();
                }
                Dicemove.setEnabled(false);
                Dicebutton.setEnabled(true);

            }
        });
        endturn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                p.remove(p3);
                p.revalidate();
                p.add(p1);
                p.revalidate();
                p.updateUI();
                endTurn();
            }
        });
        return p;
    }

    class showTitleDeed implements MouseListener {

        public void createListener() {
            for (int i = 0; i < 40; i++) {
                Street[i].addMouseListener(this);
            }
        }

        public void mousePressed(MouseEvent m) {
        }

        public void mouseReleased(MouseEvent m) {
        }

        public void mouseEntered(MouseEvent m) {
            Deed.setVisible(true);
            Deed.removeAll();
            for (int i = 0; i < 40; i++) {
                if (m.getSource() == Street[i]) {
                    //updatePropertyStatus(i);//Update prportey status
                    Deed.add(TitleDeed(i, true));
                    setPropertyStatus(i);
                //Deed.add(PropertyStatus());
                //PropertyStatus().revalidate();
                }
            }
        }

        public void mouseExited(MouseEvent m) {
            Deed.removeAll();
            for (int i = 0; i < 40; i++) {
                if (m.getSource() == Street[i]) {
                    Deed.setVisible(false);
                    Deed.revalidate();
                }
            }
        }

        public void mouseClicked(MouseEvent m) {
        }
    }

    private void updatePropertyStatus(int i) {
        String Cowner = "", Cstatus = "", Crent = "", Chouse = "", Ccost = "";
        if (Cell[i].getOwner() == null) {
            Cowner = "...";
            Cstatus = "...";
            Crent = "...";
            Chouse = "...";
            Ccost = "...";
        } else if (Cell[i].getOwner() != null) {
            Cowner = Cell[i].getOwner().getName();
            if (Cell[i].isMortgaged()) {
                Cstatus = "Mortgaged!";
            } else {
                Cstatus = "Active!";
            }
            if (i == 5 || i == 15 || i == 25 || i == 35) {
                Crent = String.valueOf(Cell[i].getRent(Cell[i].getOwner().getNoOfRailRoads()));
            } else {
                Crent = String.valueOf(Cell[i].getRent());
            }
            Chouse = String.valueOf(Cell[i].getNumberOfHouses());
            Ccost = String.valueOf(Cell[i].getCost());
        }

        if (true) {
            owner.setText(Cowner);
            status.setText(Cstatus);
            rent.setText(Crent);
            house.setText(Chouse);
            cost.setText(Ccost);
        }
    }

    public void CreateCell() {
        final String dir = "images/street_images/";
        final Dimension dimen = Board.getSize();
        for (int i = 0; i < 40; i++) {
            final int count = i;
            if (i == 0) {
                Cell[i] = new GoCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 1) {
                Cell[i] = new OldKent() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 2) {
                Cell[i] = new CommunityChestCardCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 3) {
                Cell[i] = new Whitechapel() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 4) {
                Cell[i] = new IncomeTax() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 5) {
                Cell[i] = new KingCross() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 6) {
                Cell[i] = new AngelIslington() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 7) {
                Cell[i] = new ChanceCardCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 8) {
                Cell[i] = new Euston() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 9) {
                Cell[i] = new Pentonville() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 10) {
                Cell[i] = new JailCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 11) {
                Cell[i] = new PallMall() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 12) {
                Cell[i] = new Electric() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 13) {
                Cell[i] = new WhiteHall() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 14) {
                Cell[i] = new NorthumberLand() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 15) {
                Cell[i] = new Marylebone() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 16) {
                Cell[i] = new Bow() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 17) {
                Cell[i] = new CommunityChestCardCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 18) {
                Cell[i] = new Marlborough() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 19) {
                Cell[i] = new Vine() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 20) {
                Cell[i] = new FreeParkingCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 21) {
                Cell[i] = new Strand() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 22) {
                Cell[i] = new ChanceCardCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 23) {
                Cell[i] = new Fleet() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 24) {
                Cell[i] = new Trafalgar() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 25) {
                Cell[i] = new Fenchurch() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 26) {
                Cell[i] = new Leicester() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 27) {
                Cell[i] = new Coventry() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 28) {
                Cell[i] = new WaterWorks() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 29) {
                Cell[i] = new Piccadilly() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 30) {
                Cell[i] = new GotoJailCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 31) {
                Cell[i] = new Regent() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 32) {
                Cell[i] = new Oxford() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 33) {
                Cell[i] = new CommunityChestCardCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 34) {
                Cell[i] = new Bond() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 35) {
                Cell[i] = new Liverpool() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 36) {
                Cell[i] = new ChanceCardCell() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 37) {
                Cell[i] = new Parklane() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 38) {
                Cell[i] = new SuperTax() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }
            if (i == 39) {
                Cell[i] = new MayFair() {

                    public void paintComponent(Graphics g) {
                        Dimension dim = StreetDimension(count);
                        g.drawImage(new ImageIcon(getClass().getResource(dir + BoardComponent.images[count])).getImage(), 0, 0, dim.width, dim.height, null);
                        super.paintComponent(g);

                    }
                };
            }

            Dimension str = StreetDimension(count);
            Cell[i].setOpaque(false);
            Cell[i].setPreferredSize(str);
//            if(Cell[i] instanceof PropertyCell){
//                Cell[i].setToolTipText(Cell[i].getName()+" $"+Cell[i].getCost());
//            }else{
//                Cell[i].setToolTipText(Cell[i].getName());
//            }
        }//end for loop

    }

    private Dimension StreetDimension(int count) {
        final Dimension dimen = Board.getSize();
        Dimension str = new Dimension();
        if (count == 0) {
            str.setSize((int) BoardDimension.getWidth(dimen.height), (int) BoardDimension.getWidth(dimen.height));
        }
        if (count == 10) {
            str.setSize((int) BoardDimension.getWidth(dimen.height), (int) BoardDimension.getWidth(dimen.height));
        }
        if (count == 20) {
            str.setSize((int) BoardDimension.getWidth(dimen.height), (int) BoardDimension.getWidth(dimen.height));
        }
        if (count == 30) {
            str.setSize((int) BoardDimension.getWidth(dimen.height), (int) BoardDimension.getWidth(dimen.height));
        }
        if (count > 0 && count < 10) {
            str.setSize((int) BoardDimension.getHeight(dimen.height), (int) BoardDimension.getWidth(dimen.height));
        }
        if (count > 10 && count < 20) {
            str.setSize((int) BoardDimension.getWidth(dimen.height), (int) BoardDimension.getHeight(dimen.height));
        }
        if (count > 20 && count < 30) {
            str.setSize((int) BoardDimension.getHeight(dimen.height), (int) BoardDimension.getWidth(dimen.height));
        }
        if (count > 30 && count < 40) {
            str.setSize((int) BoardDimension.getWidth(dimen.height), (int) BoardDimension.getHeight(dimen.height));
        }
        return str;
    }

    private void printCells() {
        for (int i = 0; i < 40; i++) {
            System.out.println(Cell[i]);
        }
    }

    private void startGame() {
        for (int i = 0; i < Player.length; i++) {
            Cell[Player[i].position].add(Player[i].token);
        }
        sound.playBackgroundEffect();
        if (!options.isNormalGame()) {
            this.RandomStart();
            this.updatePlayerDetails();
        }

    }

    public void RandomStart() {
        int z = Player.length - 1;
        int y = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                //Cell[i].setOwner(Player[y]);
                Player[y].purchaseProperty(Cell[i], i, 0);
                if (y >= z) {
                    y = 0;
                } else {
                    y++;
                }
            }
        }
    }

    public void checkForWinner() {
    }

    public void winGame() {
    }

    public void runTimer() {
        Time GameTime;
        if (options.getTime().equals("1")) {
            GameTime = new Time(1, 0, 0);
        } else if (options.getTime().equals("1:30")) {
            GameTime = new Time(1, 30, 0);
        } else if (options.getTime().equals("2")) {
            GameTime = new Time(2, 0, 0);
        } else if (options.getTime().equals("2:30")) {
            GameTime = new Time(2, 30, 0);
        } else {
            GameTime = new Time();
        }
        Timer timer = new Timer(GameTime);
        timer.start();
    }

    public void rollDice() {
        try {
            ThrowDice throwing = new ThrowDice(dice1, dice2, Dicetext, DiceControl);
            //throwing.setPriority(Thread.MAX_PRIORITY);
            //throwing.start();
            sound.playDice();
            throwing.Throw();
        } catch (Exception ae) {
            JOptionPane.showMessageDialog(null, "thread error");
        }
    }

    private void endTurn() {
        if (turn < Player.length) {
            turn++;
        }
        if (turn == Player.length) {
            turn = 0;
        }
        turntext.setText(Player[turn].getName() + " to roll");
        turntext.setIcon(Player[turn].token.getIcon());
        Dicemove.setVisible(true);
        Dicebutton.setVisible(true);
        Dicemove.setEnabled(false);
        Dicebutton.setEnabled(true);
        sound.playDiceSK();

        //player AI
        if (Player[turn].isAI()) {
            Dicemove.setVisible(false);
            Dicebutton.setVisible(false);
            Dicemove.setEnabled(false);
            Dicebutton.setEnabled(false);

            playAITurn(turn);
        }
    }

    public void playAITurn(final int turn) {
        try {
            isTurn = true;
            aiDecision = true;
            rollDice();
            movePlayer(DiceControl.getDiceTotal(), Player[turn].getPosition());

            aiAction();
            while (isTurn) {
            }
            if (DiceControl.Dice1 == DiceControl.Dice2) {
                playAITurn(turn);
            }
            endTurn();
        } catch (Exception ex) {
        }
    }

    public void aiAction() {
        //JOptionPane.showMessageDialog(null, "computer to make decision:\nTrade\nmortgage\nbuild\nothers");
        if (GameAI.hasMortgage(Cell, Player[turn])) {
            if (GameAI.spendingPower(Cell, Player[turn]) >= GameAI.unmortgagePriority(Cell, Player[turn]).getMortgageValue()) {
                int value = (int) ((GameAI.unmortgagePriority(Cell, Player[turn]).getCost() / 2) + ((GameAI.unmortgagePriority(Cell, Player[turn]).getCost() / 2) * 0.1));
                GameAI.unmortgagePriority(Cell, Player[turn]).setMortgage(false);
                Player[turn].payBank(value);
                sound.playUmMortgage();
            }
        //JOptionPane.showMessageDialog(null, "Computer to unmortgage " + GameAI.unmortgagePrority(Cell, Player[turn]).getName());
        }
        if (GameAI.tradeAvailable(Cell, Player[turn])) {//deside on trade
            //JOptionPane.showMessageDialog(null, "computer to trade: " + (Cell) GameAI.propertyToTrade(Cell, Player[turn]).elementAt(0));
            Vector v = GameAI.propertyToTrade(Cell, Player[turn]);
            Cell x = (Cell) v.elementAt(0);
            Cell y = (Cell) v.elementAt(2);
            int money = Integer.parseInt(String.valueOf(v.elementAt(1)));
            int demand = Integer.parseInt(String.valueOf(v.elementAt(3)));
            int b = 0;
            if (y != null) {
                b = 1;
            }
            Cell o[] = new Cell[1];
            Cell p[] = new Cell[b];
            for (int i = 0; i < o.length; i++) {
                o[i] = x;
            }
            for (int i = 0; i < p.length; i++) {
                p[i] = y;
            }
            this.tradeProperty(Player[turn], money, p, x.getOwner(), demand, o);
        }
        //Decision for building house...
        if (GameAI.hasPlayerMonopoly(Cell, Player[turn])) {
//            List s = GameAI.playerPropertyInColorGroup(Cell, Player[turn]);
//            List a = GameAI.tradeProps(Cell, Player[turn]);
//            Set ad = GameAI.playerMonopolies(Cell, Player[turn]);
//            Set as = GameAI.propertyMonopoly(Cell, Player);
//            Set sd = GameAI.highHitMonopolies(Cell, Player);
//            Set sx = GameAI.lowHitMonopolies(Cell, Player);
            //JOptionPane.showMessageDialog(null, "computer has a complete monopoly");
            if (GameAI.noOfOtherMonopoly(Cell, Player, Player[turn]) > 0) {//opponent has monopoly
                if (GameAI.hasHigherHit(Cell, Player, Player[turn])) {//opponent has higer monopoly
                    if (GameAI.hasLowHit(Cell, Player, Player[turn])) {
                        //Create housing shortage
                        this.AIBuildHouse(turn, false);
                    } else {
                        //build high hit rate
                        this.AIBuildHouse(turn, true);
                    }
                } else {
                    //build high hit rate
                    this.AIBuildHouse(turn, true);
                }
            } else {
                //build high hit rate                
                this.AIBuildHouse(turn, true);
            }
        } else {
            //build high hit rate            
            this.AIBuildHouse(turn, true);
        }
        isTurn = false;
    }

    public int getCellNo(Cell cell) {
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] == cell) {
                return i;
            }
        }
        return 0;
    }

    public void AIBuildHouse(int current, boolean b) {
        int no = 0;
        if (b) {
            no = getCellNo(GameAI.playerHighestHit(Cell, Player, Player[current]));
        } else {
            no = getCellNo(GameAI.playerLowestHit(Cell, Player, Player[current]));
        }
        while (GameAI.toBuild(Cell, Cell[no], Player[current])) {
            bank.purchaseHouse(Player[current], Cell[no], 1);
            houseimage[no].removeAll();
            if (no > 0 && no < 10) {
                if (Cell[no].getNumberOfHouses() == 1) {
                    addImage(houseimage[no], "images/house/1_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 2) {
                    addImage(houseimage[no], "images/house/2_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 3) {
                    addImage(houseimage[no], "images/house/3_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 4) {
                    addImage(houseimage[no], "images/house/4_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 5) {
                    addImage(houseimage[no], "images/house/5_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }

            }
            if (no > 10 && no < 20) {
                if (Cell[no].getNumberOfHouses() == 1) {
                    addImage(houseimage[no], "images/house/1_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 2) {
                    addImage(houseimage[no], "images/house/2_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 3) {
                    addImage(houseimage[no], "images/house/3_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 4) {
                    addImage(houseimage[no], "images/house/4_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 5) {
                    addImage(houseimage[no], "images/house/5_2.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
            }
            if (no > 20 && no < 30) {
                if (Cell[no].getNumberOfHouses() == 1) {
                    addImage(houseimage[no], "images/house/1_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 2) {
                    addImage(houseimage[no], "images/house/2_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 3) {
                    addImage(houseimage[no], "images/house/3_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 4) {
                    addImage(houseimage[no], "images/house/4_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 5) {
                    addImage(houseimage[no], "images/house/5_1.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
            }
            if (no > 30 && no < 40) {
                if (Cell[no].getNumberOfHouses() == 1) {
                    addImage(houseimage[no], "images/house/1_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 2) {
                    addImage(houseimage[no], "images/house/2_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 3) {
                    addImage(houseimage[no], "images/house/3_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 4) {
                    addImage(houseimage[no], "images/house/4_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
                if (Cell[no].getNumberOfHouses() == 5) {
                    addImage(houseimage[no], "images/house/5_4.png", houseimage[no].getWidth(), houseimage[no].getHeight());
                }
            }

            if (b) {
                no = getCellNo(GameAI.playerHighestHit(Cell, Player, Player[current]));
            } else {
                no = getCellNo(GameAI.playerLowestHit(Cell, Player, Player[current]));
            }
            sound.playBuild();
        }
        updatePlayerDetails();
    }

    public void Bankrupt(Player person) {
        Player pl[] = new Player[Player.length - 1];
        int z = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getOwner() == person) {
                    Cell[i].setOwner(null);
                    Cell[i].setMortgage(false);
                    Cell[i].setNoOfHouses(0);
                }
            }
        }

        for (int j = 0; j < Player.length; j++) {
            if (Player[j] == person) {
                updateMessage(Player[j].getName() + " is bankrupt and out of the game");
                Cell[Player[j].getPosition()].remove(Player[j].token);
                Cell[Player[j].getPosition()].revalidate();
                Cell[Player[j].getPosition()].repaint();
                Player[j] = null;
            //continue loop;
            } else {
                pl[z++] = Player[j];
            }
        }
        Player = pl;
        this.endTurn();
        updatePlayerDetails();
    }

    private void updateMessage(String s) {
        history.append(timelabel.getText() + " - " + s + "\n" + "-------------------------------------------------------------------------" + "\n");
    }

    public void updatePlayerDetails() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //updating player details
                bg.remove(playertab);
                bg.revalidate();
                makePlayersTab(Player);
                System.gc();
            }
        });

    }

    private void tradeProperty(final Player trader, final int tradercash, final Cell traderproperty[], final Player tradee, final int tradeecash, final Cell tradeeproperty[]) {

        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        final JDialog trade = new JDialog();

        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back4.png")).getImage(), 0, 0, 400, 400, null);
                super.paintComponent(g);
            }
        };

        JPanel all = new JPanel();
        JLabel title = new JLabel(tradee.getName(), tradee.token.getIcon(), JLabel.CENTER);
        final JButton ok = new JButton("Accept");
        final JButton can = new JButton("Decline");
        JPanel t1[] = new JPanel[traderproperty.length];
        JPanel t2[] = new JPanel[tradeeproperty.length];
        final String dir = "images/tittle_deeds/";
        JPanel p1 = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 100, 200, null);
                super.paintComponent(g);
            }
        };
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 100, 200, null);
                super.paintComponent(g);
            }
        };
        JPanel tran = new JPanel();

        p1.setOpaque(false);
        p2.setOpaque(false);
        p3.setOpaque(false);

        for (int i = 0; i < t1.length; i++) {
            int no = 0;
            t1[i] = new JPanel();
            t1[i].setPreferredSize(new Dimension(20, 20));
            t1[i].setBorder(raisedetched);
            t1[i].setToolTipText(traderproperty[i].getName());
            for (int j = 0; j < Cell.length; j++) {
                if (Cell[j] instanceof PropertyCell) {
                    if (Cell[j] == traderproperty[i]) {
                        no = j;
                    }
                }
            }
            this.addImage(t1[i], dir + BoardComponent.Deeds[no], 20, 20);
            JLabel l = new JLabel(traderproperty[i].getName().toUpperCase());
            l.setFont(new Font("verdana", Font.BOLD, 10));
            p1.add(t1[i]);
            p1.add(l);
        }
        JLabel cash = new JLabel("CASH: $" + String.valueOf(tradercash));
        cash.setFont(new Font("verdana", Font.BOLD, 10));
        cash.setForeground(Color.WHITE);
        p1.add(cash);
        for (int i = 0; i < t2.length; i++) {
            int no = 0;
            t2[i] = new JPanel();
            t2[i].setPreferredSize(new Dimension(20, 20));
            t2[i].setBorder(raisedetched);
            t2[i].setToolTipText(tradeeproperty[i].getName());
            for (int j = 0; j < Cell.length; j++) {
                if (Cell[j] instanceof PropertyCell) {
                    if (Cell[j] == tradeeproperty[i]) {
                        no = j;
                    }
                }
            }
            this.addImage(t2[i], dir + BoardComponent.Deeds[no], 20, 20);
            JLabel l = new JLabel(tradeeproperty[i].getName().toUpperCase());
            l.setFont(new Font("verdana", Font.BOLD, 10));
            p3.add(t2[i]);
            p3.add(l);
        }
        JLabel cash1 = new JLabel("CASH: $" + String.valueOf(tradeecash));
        cash1.setFont(new Font("verdana", Font.BOLD, 10));
        cash1.setForeground(Color.WHITE);
        p3.add(cash1);

        p2.add(new JLabel(new ImageIcon(getClass().getResource("images/trader.png"))), BorderLayout.CENTER);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playAccept();
                //for trader
                for (int i = 0; i < tradeeproperty.length; i++) {
                    tradee.sellProperty(tradeeproperty[i], tradeeproperty[i].getPosition(), 0);
                }
                for (int i = 0; i < tradeeproperty.length; i++) {
                    trader.purchaseProperty(tradeeproperty[i], tradeeproperty[i].getPosition(), 0);
                }
                trader.setMoney(trader.money + tradeecash);
                //for tradee
                for (int i = 0; i < traderproperty.length; i++) {
                    trader.sellProperty(traderproperty[i], traderproperty[i].getPosition(), 0);
                }
                for (int i = 0; i < traderproperty.length; i++) {
                    tradee.purchaseProperty(traderproperty[i], traderproperty[i].getPosition(), 0);
                }
                tradee.setMoney(tradee.money + tradercash);

                trade.setVisible(false);
                if (!Player[turn].isAI()) {
                    makeTrade();
                }
            }
        });
        can.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playDecline();
                trade.setVisible(false);
                if (!Player[turn].isAI()) {
                    makeTrade();
                }
            }
        });

        title.setFont(new Font("cooper black", 0, 15));
        ok.setFont(new Font("cooper black", 0, 15));
        can.setFont(new Font("cooper black", 0, 15));

        ok.setBackground(Color.WHITE);
        can.setBackground(Color.WHITE);
        //ok.setOpaque(true);

        all.setLayout(new GridLayout(4, 1, 5, 5));
        all.add(title);
        all.add(ok);
        all.add(can);
        all.setBounds(150, 260, 100, 150);
        all.setOpaque(false);

        tran.setLayout(new GridLayout(1, 3));
        tran.setBounds(50, 50, 300, 200);
        tran.setOpaque(false);
        tran.setBorder(raisedetched);
        tran.add(p1);
        tran.add(p2);
        tran.add(p3);

        Dimension d = Board.getSize();

        Cback.setBounds(0, 0, 400, 400);
        Cback.add(all, BorderLayout.CENTER);
        Cback.add(tran);
        Cback.setOpaque(false);
        Cback.setLayout(null);
        trade.setLayout(null);
        trade.add(Cback);
        trade.setBounds((int) (d.height / 2) - 200, (int) (d.height / 2) - 200, 400, 400);

        trade.setUndecorated(true);
        trade.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        trade.setResizable(false);
        //applying AI decision
        if (tradee.isAI()) {
            //dialog.setVisible(true);
//            buy.setVisible(false);
//            auc.setVisible(false);
//            buy.setOpaque(false);
//            auc.setOpaque(false);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    Cell a = new Cell();
                    Cell b = new Cell();
                    if (traderproperty.length > 0) {
                        a = traderproperty[0];
                    }
                    if (tradeeproperty.length > 0) {
                        b = tradeeproperty[0];
                    }
                    if (GameAI.acceptTrade(Cell, a, b, tradercash, tradeecash, tradee)) {
                        ok.doClick(1000);
                    } else {
                        can.doClick(1000);
                    }
                    aiDecision = false;
                //this.notify();
                }
            });
            trade.setModal(true);
            trade.setVisible(true);
            try {
                //this.wait();
            } catch (Exception ex) {
            }
        } else {
            trade.setModal(true);
            trade.setVisible(true);
        }
    }

    private void movePlayer(int dice, int position) {
        int newpos = dice + position;
        if (Player[turn].inJail()) {
            //in jail action here
            if (Player[turn].getJailCount() < 2) {
                playInJail();
            } else {
                Player[turn].payBank(50);
                if (Player[turn].getMoney() < 50) {
                    playNoFunds(turn);
                }
                Player[turn].setInJail(false);
                sound.playOpen();
                updateMessage(Player[turn].getName() + " is out if jail");
            }
        }
        if (!Player[turn].inJail()) {
            if (dice != 0) {
                if (newpos < 40) {
                    Cell[position].remove(Player[turn].token);
                    Cell[position].revalidate();
                    Cell[newpos].add(Player[turn].token);
                    Cell[newpos].revalidate();
                    Player[turn].setPosition(newpos);
                } else if (newpos >= 40) {
                    newpos -= 40;
                    Cell[position].remove(Player[turn].token);
                    Cell[position].revalidate();
                    Cell[newpos].add(Player[turn].token);
                    Cell[newpos].revalidate();
                    Player[turn].setPosition(newpos);
                    //You passed GO - You have been paid $200 salary
                    passGo(turn);
                }
            } else {
                Cell[Player[turn].getPosition()].remove(Player[turn].token);
                Cell[Player[turn].getPosition()].revalidate();
                Cell[position].add(Player[turn].token);
                Cell[position].revalidate();
                Player[turn].setPosition(position);
                newpos = position;
            }

            for (int i = 0; i < Cell.length; i++) {
                Cell[i].revalidate();
                Cell[i].repaint();
                Cell[i].updateUI();
            }
            sound.playSlide();
            updateMessage(Player[turn].getName() + " moved to " + Cell[Player[turn].getPosition()].getName());

            if (Cell[newpos].isAvailable()) {
                if (Cell[newpos].getOwner() != Player[turn] && Cell[newpos].getOwner() == null) {
                    //Option buy house.                    
                    buyProperty(newpos, false);
                } else if (Cell[newpos].getOwner() == Player[turn]) {
                    //You own this property
                    playOwnProperty();
                } else {
                    //pay rent.
                    if (Cell[newpos].isMortgaged()) {
                        playNoRent();
                    } else {
                        if (newpos == 5 || newpos == 15 || newpos == 25 || newpos == 35) {
                            payRent(newpos, "railroad", 0);
                        } else if (newpos == 12 || newpos == 28) {
                            payRent(newpos, "utilities", dice);
                        } else {
                            payRent(newpos, "", 0);
                        }
                    }
                }
            } else {
                //You cannot buy property
                if (newpos == 2 || newpos == 17 || newpos == 33) {
                    //Community Chest
                    playCommunityChest();
                } else if (newpos == 7 || newpos == 22 || newpos == 36) {
                    //Chance
                    playChance();
                } else if (newpos == 10) {
                    //Just visiting jail"
                    playJustVisiting();
                } else if (newpos == 20) {
                    //Free parking"
                    playFreeParking();
                } else if (newpos == 30) {
                    //You are going to jail!
                    sendToJail();
                } else if (newpos == 4) {
                    //Income tax
                    playIncomeTax();
                } else if (newpos == 38) {
                    //Super tax
                    playSuperTax();
                }
            }
        }
    }

    private void buyProperty(final int pos, boolean auction) {
        final JDialog dialog = new JDialog();
        String dir = "images/tittle_deeds/";
        String dir1 = "images/street/";
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel back = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 250, 280, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 250, 280, null);
                }

                super.paintComponent(g);
            }
        };
        JLabel title = new JLabel(" ~*~ For Sale ~*~ ");
        JLabel cost = new JLabel();
        final JButton buy = new JButton("Buy");
        final JButton auc = new JButton("No Thanks");
        Font font = new Font("cooper black", Font.BOLD, 20);
        Font font1 = new Font("cooper black", Font.BOLD, 25);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        buy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (Player[turn].getMoney() >= Cell[pos].getCost()) {
                    sound.playSold();
                    Player[turn].purchaseProperty(Cell[pos], pos, Cell[pos].getCost());
                    updateMessage(Player[turn].getName() + " purchased " + Cell[pos].getName());
                } else {
                    sound.playOhoh();
                    JOptionPane.showMessageDialog(null, "You have insufficient funds!");
                //more code here
                }
                //updating player details panel
                updatePlayerDetails();
                dialog.setVisible(false);
            }
        });
        auc.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playNotSold();
                dialog.setVisible(false);
            }
        });

        String amount = String.valueOf(Cell[pos].getCost());
        cost.setText("$ " + amount);
        cost.setFont(font1);
        title.setFont(font);
        title.setForeground(Color.red);
        p1.setBorder(raisedetched);
        p1.setBackground(Color.black);
        p1.add(title);

        addImage(p2, dir + BoardComponent.Deeds[pos], 100, 150);
        addImage(p3, dir1 + BoardComponent.images[pos], 100, 150);
        p4.setLayout(new GridLayout(1, 2, 10, 10));
        p4.setOpaque(false);
        p4.add(p2);
        p4.add(p3);

        dialog.setLayout(new GridLayout(1, 1));
        dialog.setBounds((int) (Board.getHeight() / 2) - 125, (int) (Board.getHeight() / 2) - 140, 250, 280);
        dialog.add(back);
        back.setOpaque(false);
        back.add(p1);
        back.add(cost);
        back.add(p4);
        back.add(buy);
        back.add(auc);

        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);


        //applying AI decision
        if (Player[turn].isAI()) {
            //dialog.setVisible(true);
            buy.setVisible(false);
            auc.setVisible(false);
            buy.setOpaque(false);
            auc.setOpaque(false);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    if (GameAI.buyDecision(Player[turn], Cell)) {
                        buy.doClick(1000);
                    } else {
                        auc.doClick(1000);
                    }
                    aiDecision = false;
                //this.notify();
                }
            });
            dialog.setModal(true);
            dialog.setVisible(true);
            try {
                //this.wait();
            } catch (Exception ex) {
            }
        } else {
            dialog.setModal(true);
            dialog.setVisible(true);
        }

    }

    private void payRent(final int i, final String s, final int dice) {
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok;
        if (s.equals("railroad")) {
            ok = new JButton("Pay rent of " + Cell[i].getRent(Player[turn].getNoOfRailRoads()) + " !");
        } else if (s.equals("utilities")) {
            ok = new JButton("Pay rent of " + Cell[i].getRent(Player[turn].getNoOfUtilities(), dice) + " !");
        } else {
            ok = new JButton("Pay rent of " + Cell[i].getRent() + " !");
        }
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "payrent.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                if (s.equals("railroad")) {
                    Player[turn].payRentTo(Cell[i].getOwner(), Cell[i].getRent(Player[turn].getNoOfRailRoads()));
                    if (Player[turn].getMoney() < Cell[i].getRent(Player[turn].getNoOfRailRoads())) {
                        playNoFunds(turn);
                    }
                    updateMessage(Player[turn].getName() + " paid $" + Cell[i].getRent(Player[turn].getNoOfRailRoads()) + " rent to " + Cell[i].getOwner().getName());
                } else if (s.equals("utilities")) {
                    Player[turn].payRentTo(Cell[i].getOwner(), Cell[i].getRent(Player[turn].getNoOfUtilities(), dice));
                    if (Player[turn].getMoney() < Cell[i].getRent(Player[turn].getNoOfUtilities(), dice)) {
                        playNoFunds(turn);
                    }
                    updateMessage(Player[turn].getName() + " paid $" + Cell[i].getRent(Player[turn].getNoOfUtilities(), dice) + " rent to " + Cell[i].getOwner().getName());
                } else {
                    Player[turn].payRentTo(Cell[i].getOwner(), Cell[i].getRent());
                    if (Player[turn].getMoney() < Cell[i].getRent()) {
                        playNoFunds(turn);
                    }
                    updateMessage(Player[turn].getName() + " paid $" + Cell[i].getRent() + " rent to " + Cell[i].getOwner().getName());
                }
                sound.playCash();
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(Idialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        //applying AI decision
        if (Player[turn].isAI()) {
            //ok.setVisible(false);
            ok.setBackground(Color.red);
            ok.setOpaque(false);
            ok.setBorderPainted(false);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(2000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }

    }

    private void passGo(final int i) {
        //sound.playGoBell();
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "passgo.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                Player[i].setMoney(Player[i].getMoney() + goMoney);//adding go money to player
                sound.playMoney();
                updateMessage(Player[turn].getName() + " passed GO!");
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(Idialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);

        if (Player[turn].isAI()) {
            ok.setBackground(Color.red);
            ok.setVisible(false);
            ok.setOpaque(false);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    public void GameDetails() {
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        final Dimension d = toolkit.getScreenSize();
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/set.png")).getImage(), 0, 0, d.width - 200, d.height - 200, null);
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(isTurn);
                System.gc();
            }
        });
        String winning = "";
        if (options.getWin().equals("bankrupt")) {
            winning = "BANKRUPCTY";
        } else {
            winning = options.getWin() + "% OWNERSHIP!";
        }
        final JPanel jp = new JPanel();
        JLabel time1 = new JLabel("Time Played:   " + timelabel.getText());
        JLabel time2 = new JLabel("Time Remaining:   ");
        JLabel win = new JLabel("Wining Type:   " + winning);

        time1.setFont(new Font("cooper black", Font.BOLD, 16));
        time2.setFont(new Font("cooper black", Font.BOLD, 16));
        win.setFont(new Font("cooper black", Font.BOLD, 16));

        Dimension dm = new Dimension(330 * Player.length, 500);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        jp.setLayout(new GridLayout(1, 3));
        jp.setBounds(50, 20, 800, 100);
        jp.setOpaque(false);
        jp.add(time1);
        jp.add(time2);
        jp.add(win);

        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds(100, 100, d.width - 200, d.height - 200);
        Idialog.add(Cback);
        Cback.setOpaque(false);

        ok.setBounds(100, 750, 100, 50);

        p1.setLayout(new GridLayout(1, Player.length));
        p1.setPreferredSize(dm);
        p1.setBounds(50, 100, dm.width, dm.height);
        for (int i = 0; i < Player.length; i++) {
            p1.add(this.PlayDetails(i));
        }
        Cback.setLayout(null);
        Cback.setBorder(raisedetched);
        Cback.add(jp);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        Idialog.setModal(true);
        Idialog.setVisible(true);

    }

    private void playChance() {
        sound.playJingle();
        final JDialog Cdialog = new JDialog();
        final String dir = "images/chance/";
        final JLabel title = new JLabel(new ImageIcon(getClass().getResource(dir + "chance_title.png")));
        final JButton drawcard = new JButton("Draw Card");
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        ok.setEnabled(false);
        addImage(p1, dir + "chance.png", 250, 150);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 300, 250, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 300, 250, null);
                }
                super.paintComponent(g);
            }
        };
        Cback.setOpaque(false);
        int x = 0;
        //final int z=3;
        final int z = 1 + (int) (Math.random() * 16);
        drawcard.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playWolf();
                p1.removeAll();
                addImage(p1, dir + String.valueOf(z) + ".png", 250, 150);
                p1.revalidate();
                drawcard.setEnabled(false);
                ok.setEnabled(true);
            }
        });
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Cdialog.setVisible(false);
                //play case here!!!
                switch (z) {
                    case 1:
                        //bank pays u a divident of 50
                        bank.payPlayer(Player[turn], 50);
                        sound.playMoney();
                        break;
                    case 2:
                        //logicmake general repairs on all property, for each house pay 25 and hotel 100
                        Player[turn].payBank((Player[turn].getNoOfHouses() * 25) + (Player[turn].getNoOfHotels() * 100));
                        if (Player[turn].getMoney() < ((Player[turn].getNoOfHouses() * 25) + (Player[turn].getNoOfHotels() * 100))) {
                            playNoFunds(turn);
                        }
                        break;
                    case 3:
                        //get out of jail free card
                        Player[turn].aquiredChanceJailCard();
                        break;
                    case 4:
                        //move back 3 spaces
                        movePlayer(-3, Player[turn].getPosition());
                        break;
                    case 5:
                        //advance to mayfair
                        movePlayer(0, 39);
                        break;
                    case 6:
                        //go directly to jail
                        sendToJail();
                        break;
                    case 7:
                        //advance to the nearest railway and pay owner 2wice the rental.
                        if (Player[turn].getPosition() < 5) {
                            movePlayer(0, 5);
                        } else if (Player[turn].getPosition() > 5) {
                            movePlayer(0, 15);
                        } else if (Player[turn].getPosition() > 15) {
                            movePlayer(0, 25);
                        } else if (Player[turn].getPosition() > 25) {
                            movePlayer(0, 35);
                        } else if (Player[turn].getPosition() > 35) {
                            movePlayer(0, 5);
                        }
                        break;
                    case 8:
                        //advance to the nearest railway and pay owner 2wice the rental.
                        if (Player[turn].getPosition() < 5) {
                            movePlayer(0, 5);
                        } else if (Player[turn].getPosition() > 5) {
                            movePlayer(0, 15);
                        } else if (Player[turn].getPosition() > 15) {
                            movePlayer(0, 25);
                        } else if (Player[turn].getPosition() > 25) {
                            movePlayer(0, 35);
                        } else if (Player[turn].getPosition() > 35) {
                            movePlayer(0, 5);
                        }
                        break;
                    case 9:
                        //take a ride to kingscross station is u pass go collect 200
                        if (Player[turn].getPosition() == 7) {
                            movePlayer(38, 7);
                        } else if (Player[turn].getPosition() == 22) {
                            movePlayer(23, 22);
                        } else if (Player[turn].getPosition() == 36) {
                            movePlayer(9, 36);
                        }
                        break;
                    case 10:
                        //u have been elected chairman pay each player 50
                        for (int i = 0; i < Player.length; i++) {
                            if (i != turn) {
                                Player[turn].payRentTo(Player[i], 50);
                                sound.playCash();
                                if (Player[i].getMoney() < 50) {
                                    playNoFunds(i);
                                }
                            }
                        }
                        break;
                    case 11:
                        //Advance to trafalgar square
                        movePlayer(0, 24);
                        break;
                    case 12:
                        //advance to go collect 200
                        movePlayer(40 - Player[turn].getPosition(), Player[turn].getPosition());
                        break;
                    case 13:
                        //your building loan matured collect 150
                        bank.payPlayer(Player[turn], 150);
                        sound.playMoney();
                        break;
                    case 14:
                        //advance to pall mall if u pass go collect 200
                        if (Player[turn].getPosition() == 7) {
                            movePlayer(4, 7);
                        } else if (Player[turn].getPosition() == 22) {
                            movePlayer(29, 22);
                        } else if (Player[turn].getPosition() == 36) {
                            movePlayer(15, 36);
                        }
                        break;
                    case 15:
                        //Advance to the nearest utility, if unonwned u may buy and if own roll dice again and pay owner a total ten times amount
                        if (Player[turn].getPosition() < 12 || Player[turn].getPosition() > 28) {
                            movePlayer(0, 12);
                        } else if (Player[turn].getPosition() > 12 && Player[turn].getPosition() < 28) {
                            movePlayer(0, 28);
                        }
                        break;
                    case 16:
                        //pay poor tax of 15
                        Player[turn].payBank(15);
                        if (Player[turn].getMoney() < 15) {
                            playNoFunds(turn);
                        }
                        break;
                }
                updateMessage(Player[turn].getName() + " played chance card!");
                updatePlayerDetails();
            }
        });

        Border redline = BorderFactory.createLineBorder(Color.black);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border compound = BorderFactory.createCompoundBorder(redline, raisedetched);
        p1.setBorder(compound);
        Cdialog.setLayout(new GridLayout(1, 1));
        Cdialog.setBounds((int) (Board.getHeight() / 2) - 150, (int) (Board.getHeight() / 2) - 125, 300, 250);
        Cdialog.add(Cback);
        Cback.add(title);
        Cback.add(p1);
        Cback.add(drawcard);
        Cback.add(ok);

        Cdialog.setUndecorated(true);
        Cdialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Cdialog.setResizable(false);


        if (Player[turn].isAI()) {

            ok.setBackground(Color.RED);
            ok.setOpaque(false);
            drawcard.setOpaque(false);
            drawcard.setBackground(Color.RED);
            ok.setBorderPainted(false);
            drawcard.setBorderPainted(false);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    drawcard.doClick();
                    new waittime(ok).start();
                //
                //try{Thread.sleep(500);}catch(Exception ex){}
                //ok.doClick(3000);
                }
            });
            Cdialog.setModal(true);
            Cdialog.setVisible(true);
            aiDecision = false;
        } else {
            Cdialog.setModal(true);
            Cdialog.setVisible(true);
        }
    }

    private void playCommunityChest() {
        sound.playJingle();
        final JDialog Cdialog = new JDialog();
        final String dir = "images/Community_chest/";
        final JLabel title = new JLabel(new ImageIcon(getClass().getResource(dir + "community_title.png")));
        final JButton drawcard = new JButton("Draw Card");
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        //AbstractButton b=new AbstractButton();
        ok.setEnabled(false);
        addImage(p1, dir + "community.png", 250, 150);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 300, 250, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 300, 250, null);
                }
                super.paintComponent(g);
            }
        };
        Cback.setOpaque(false);
        //int x=0;
        //final int z=15;
        final int z = 1 + (int) (Math.random() * 16);
        drawcard.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playWolf();
                p1.removeAll();
                addImage(p1, dir + String.valueOf(z) + ".png", 250, 150);
                p1.revalidate();
                drawcard.setEnabled(false);
                ok.setEnabled(true);
            }
        });
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Cdialog.setVisible(false);
                //play case here!!!
                switch (z) {
                    case 1:
                        //you are assessed for street repairs 40 per house and 115 per hotel
                        Player[turn].payBank((Player[turn].getNoOfHouses() * 40) + (Player[turn].getNoOfHotels() * 115));
                        if (Player[turn].getMoney() < ((Player[turn].getNoOfHouses() * 40) + (Player[turn].getNoOfHotels() * 115))) {
                            playNoFunds(turn);
                        }
                        break;
                    case 2:
                        //bank error in ur favour collect 200
                        bank.payPlayer(Player[turn], 200);
                        sound.playMoney();
                        break;
                    case 3:
                        //pay hospital 100
                        Player[turn].payBank(100);
                        if (Player[turn].getMoney() < 100) {
                            playNoFunds(turn);
                        }
                        break;
                    case 4:
                        //pay school tax of 150
                        Player[turn].payBank(150);
                        if (Player[turn].getMoney() < 150) {
                            playNoFunds(turn);
                        }
                        break;
                    case 5:
                        //go to jail
                        sendToJail();
                        break;
                    case 6:
                        //collect 50 from each player
                        for (int i = 0; i < Player.length; i++) {
                            if (i != turn) {
                                Player[i].payRentTo(Player[turn], 50);
                                sound.playCash();
                                if (Player[i].getMoney() < 50) {
                                    playNoFunds(i);
                                }
                            }
                        }
                        break;
                    case 7:
                        //income tax refund collect 20
                        bank.payPlayer(Player[turn], 20);
                        sound.playMoney();
                        break;
                    case 8:
                        //life insurance matures collect 100
                        bank.payPlayer(Player[turn], 100);
                        sound.playMoney();
                        break;
                    case 9:
                        //from sale of stock u get 45
                        bank.payPlayer(Player[turn], 45);
                        sound.playMoney();
                        break;
                    case 10:
                        //recieve for services 25
                        bank.payPlayer(Player[turn], 25);
                        break;
                    case 11:
                        //you have won 2nd price in a beuty contest collect 10
                        bank.payPlayer(Player[turn], 10);
                        sound.playMoney();
                        break;
                    case 12:
                        //doctors fee pay 50
                        Player[turn].payBank(50);
                        sound.playMoney();
                        if (Player[turn].getMoney() < 50) {
                            playNoFunds(turn);
                        }
                        break;
                    case 13:
                        //christmas funds matures collect 100
                        bank.payPlayer(Player[turn], 100);
                        sound.playMoney();
                        break;
                    case 14:
                        //advance to go
                        movePlayer(40 - Player[turn].getPosition(), Player[turn].getPosition());
                        break;
                    case 15:
                        //get out of jai free
                        Player[turn].aquiredCommunityJailCard();
                        break;
                    case 16:
                        //u inherit 100
                        bank.payPlayer(Player[turn], 100);
                        sound.playMoney();
                        break;

                }
                updateMessage(Player[turn].getName() + " played cummunity chest card");
                //update player details
                bg.remove(playertab);
                bg.revalidate();
                makePlayersTab(Player);
            }
        });
        Border redline = BorderFactory.createLineBorder(Color.black);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border compound = BorderFactory.createCompoundBorder(redline, raisedetched);
        p1.setBorder(compound);
        Cdialog.setLayout(new GridLayout(1, 1));
        Cdialog.setBounds((int) (Board.getHeight() / 2) - 150, (int) (Board.getHeight() / 2) - 125, 300, 250);
        Cdialog.add(Cback);
        Cback.add(title);
        Cback.add(p1);
        Cback.add(drawcard);
        Cback.add(ok);

        Cdialog.setUndecorated(true);
        Cdialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Cdialog.setResizable(false);

        if (Player[turn].isAI()) {
            //drawcard.setVisible(false);
            //ok.setVisible(false);
            drawcard.setBackground(Color.red);
            ok.setBackground(Color.red);
            drawcard.setOpaque(false);
            ok.setOpaque(false);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    drawcard.doClick();
                    new waittime(ok).start();
                //try{Thread.sleep(500);}catch(Exception ex){}
                //ok.doClick(3000);
                }
            });
            Cdialog.setModal(true);
            Cdialog.setVisible(true);
            aiDecision = false;
        } else {
            Cdialog.setModal(true);
            Cdialog.setVisible(true);
        }
    }

    private void playIncomeTax() {
        sound.playTaxRent();
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton pay = new JButton("Pay $200");
        final JButton ten = new JButton("Pay 10%");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "incometax.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        pay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playCash();
                Player[turn].payBank(200);
                Idialog.setVisible(false);
                if (Player[turn].getMoney() < 200) {
                    playNoFunds(turn);
                }
                updateMessage(Player[turn].getName() + " paid $200 income tax!");
                //updating player details
                updatePlayerDetails();
            }
        });
        ten.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //play 10% logic here.
                sound.playCash();
                int x = (int) (Player[turn].getWorth() * 0.10);
                Player[turn].payBank(x);
                Idialog.setVisible(false);
                if (Player[turn].getMoney() < 200) {
                    playNoFunds(turn);
                }
                //Idialog.setVisible(false);
                updateMessage(Player[turn].getName() + " paid 10% income tax");
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(pay);
        Cback.add(ten);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            //drawcard.setVisible(false);
            //pay.setEnabled(false);
            pay.setOpaque(false);
            pay.setBackground(Color.red);
            ten.setOpaque(false);
            ten.setBackground(Color.red);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    if (Player[turn].getWorth() > 200) {
                        pay.doClick(1000);
                    } else {
                        ten.doClick(1000);
                    }

                //try{Thread.sleep(500);}catch(Exception ex){}
                //ok.doClick(3000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playSuperTax() {
        sound.playTaxRent();
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton pay = new JButton("Pay $100");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "supertax.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        pay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playCash();
                Player[turn].payBank(100);
                Idialog.setVisible(false);
                if (Player[turn].getMoney() < 100) {
                    playNoFunds(turn);
                }
                updateMessage(Player[turn].getName() + " paid 100 super tax");
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(pay);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            //pay.setVisible(false);
            pay.setOpaque(false);
            pay.setBackground(Color.red);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    pay.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playFreeParking() {
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "freeparking.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(Idialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            ok.setVisible(false);
            ok.setBackground(Color.red);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playJustVisiting() {
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "visiting_jail.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);

        if (Player[turn].isAI()) {
            ok.setVisible(false);
            ok.setBackground(Color.red);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playOwnProperty() {
        sound.playCheers();
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "owner.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(Idialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            ok.setVisible(false);
            ok.setBackground(Color.red);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void sendToJail() {
        sound.playWhistle();
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Move to jail!");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "send_to_jail.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sound.playSiren();
                //send player to jail
                Cell[Player[turn].getPosition()].remove(Player[turn].token);
                Cell[Player[turn].getPosition()].revalidate();
                Cell[10].add(Player[turn].token);
                Cell[10].revalidate();
                Player[turn].setPosition(10);
                Player[turn].setInJail(true);
                Player[turn].setJailCount(0);
                Idialog.setVisible(false);
                updateMessage(Player[turn].getName() + " went to jail! ");
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);


        if (Player[turn].isAI()) {
            //ok.setVisible(false);
            ok.setBackground(Color.red);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playInJail() {
        sound.playJailDoor();
        updateMessage(Player[turn].getName() + " is in jail");
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JButton pay = new JButton("Bail Out-$50");
        final JButton card = new JButton("Use Card!");
        final JButton buy = new JButton("Buy Card!");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "in_jail.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };

        if (Player[turn].hasChanceJailCard() || Player[turn].hasCommunityJailCard()) {
            card.setEnabled(true);
        } else {
            card.setEnabled(false);
        }
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Player[turn].setJailCount(Player[turn].jailcount + 1);
                Idialog.setVisible(false);
                updateMessage(Player[turn].getName() + " is till in jail.");
                //updating player details
                updatePlayerDetails();
            }
        });
        pay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (Player[turn].getMoney() > 50) {
                    Player[turn].payBank(50);
                    Player[turn].setInJail(false);
                    Idialog.setVisible(false);
                    updateMessage(Player[turn].getName() + " paid $50 bail");
                    updateMessage(Player[turn].getName() + " is out of jail.");
                    sound.playOpen();
                    //updating player details
                    updatePlayerDetails();
                } else {
                    JOptionPane.showMessageDialog(null, "You have insufficient funds funds !");
                }
            }
        });
        card.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        if (Player[turn].hasChanceJailCard()) {
                            Player[turn].useChanceJailCard();
                            Player[turn].setInJail(false);
                            break;
                        }
                    } else {
                        if (Player[turn].hasCommunityJailCard()) {
                            Player[turn].useCommunityJailCard();
                            Player[turn].setInJail(false);
                            break;
                        }
                    }
                }
                updateMessage(Player[turn].getName() + " used jail card.");
                updateMessage(Player[turn].getName() + " is out of jail.");
                sound.playOpen();
                //updating player details
                updatePlayerDetails();
            }
        });
        buy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "not don yet!");
            /*Idialog.setVisible(false);
            //updating player details
            updatePlayerDetails();*/
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);
        Cback.add(pay);
        Cback.add(card);
        Cback.add(buy);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            //drawcard.setVisible(false);
            //ok.setVisible(false);
            ok.setBackground(Color.red);
            pay.setBackground(Color.red);
            card.setBackground(Color.red);
            buy.setBackground(Color.red);
            ok.setOpaque(false);
            pay.setOpaque(false);
            card.setOpaque(false);
            buy.setOpaque(false);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    if (GameAI.jailDecision(Player[turn], Cell) == 1) {
                        ok.doClick(1000);
                    } else if (GameAI.jailDecision(Player[turn], Cell) == 2) {
                        pay.doClick(1000);
                    } else if (GameAI.jailDecision(Player[turn], Cell) == 3) {
                        card.doClick(1000);
                    } else if (GameAI.jailDecision(Player[turn], Cell) == 4) {
                        buy.doClick(1000);
                    }
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playNoFunds(final int turn) {
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Done");
        final JButton pay = new JButton("Bankrupt");
        final JButton card = new JButton("Mortgage");
        final JButton buy = new JButton("Sell House");
        final JLabel money = new JLabel("$" + String.valueOf(Player[turn].getMoney()));
        final JPanel p1 = new JPanel();
        final JPanel p2 = new JPanel();
        addImage(p1, dir + "insufficient_fund.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (Player[turn].getMoney() >= 0) {
                    Idialog.setVisible(false);
                    //updating player details
                    updatePlayerDetails();

                } else {
                    JOptionPane.showMessageDialog(null, "You have no money!");
                }
            }
        });
        pay.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Bankrupt(Player[turn]);
                Idialog.setVisible(false);
            }
        });
        card.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                playMortgage();
                Idialog.setVisible(false);
                playNoFunds(turn);
            }
        });
        buy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                buildHouse(turn);
                Idialog.setVisible(false);
                playNoFunds(turn);
            /*Idialog.setVisible(false);
            //updating player details
            updatePlayerDetails();;*/
            }
        });
        money.setBounds(160, 80, 100, 40);
        money.setFont(new Font("cooper black", Font.BOLD, 30));
        money.setForeground(Color.RED);
        Idialog.setLayout(null);
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Idialog.add(money);
        p1.setBounds(0, 0, 350, 250);
        p2.setPreferredSize(new Dimension(350, 250));
        p2.setLayout(null);
        p2.add(money);
        p2.add(p1);
        Cback.setBounds(0, 0, 380, 300);
        Cback.setOpaque(false);
        Cback.add(p2);
        Cback.add(ok);
        Cback.add(card);
        Cback.add(buy);
        Cback.add(pay);
        sound.playOhoh();
        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            //drawcard.setVisible(false);
            //ok.setVisible(false);
            ok.setBackground(Color.red);
            pay.setBackground(Color.red);
            card.setBackground(Color.red);
            buy.setBackground(Color.red);
            ok.setOpaque(false);
            pay.setOpaque(false);
            card.setOpaque(false);
            buy.setOpaque(false);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    if (Player[turn].getMoney() >= 0) {
                        ok.doClick(1000);
                    } else {
                        for (int i = 0; i < Cell.length; i++) {
                            if (Cell[i] instanceof PropertyCell) {

                                if (Cell[i].getOwner() == Player[turn]) {
                                    if (Cell[i] instanceof UtilityCell) {
                                        Cell[i].setMortgage(true);
                                        bank.payPlayer(Player[turn], Cell[i].getCost() / 2);
                                        sound.playMortgage();
                                        if (Player[turn].getMoney() >= 0) {
                                            ok.doClick(1000);
                                            break;
                                        }
                                    } else if (Cell[i] instanceof RailRoadCell) {
                                        Cell[i].setMortgage(true);
                                        bank.payPlayer(Player[turn], Cell[i].getCost() / 2);
                                        sound.playMortgage();
                                        if (Player[turn].getMoney() >= 0) {
                                            ok.doClick(1000);
                                            break;
                                        }
                                    } else {
                                        if (Cell[i].getNumberOfHouses() == 0) {
                                            Cell[i].setMortgage(true);
                                            bank.payPlayer(Player[turn], Cell[i].getCost() / 2);
                                            sound.playMortgage();
                                            if (Player[turn].getMoney() >= 0) {
                                                ok.doClick(1000);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    //sell house instead
                    }
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void playNoRent() {
        final JDialog Idialog = new JDialog();
        final String dir = "images/";
        final JButton ok = new JButton("Ok");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "no_rent.png", 350, 250);
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                if (Player[turn].isAI()) {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 380, 300, null);
                } else {
                    g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 380, 300, null);
                }
                super.paintComponent(g);
            }
        };
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
                //updating player details
                updatePlayerDetails();
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds((int) (Board.getHeight() / 2) - 190, (int) (Board.getHeight() / 2) - 175, 380, 300);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(Idialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        if (Player[turn].isAI()) {
            //drawcard.setVisible(false);
            //ok.setVisible(false);
            ok.setBackground(Color.red);
            ok.setOpaque(false);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ok.doClick(1000);
                }
            });
            Idialog.setModal(true);
            Idialog.setVisible(true);
            aiDecision = false;
        } else {
            Idialog.setModal(true);
            Idialog.setVisible(true);
        }
    }

    private void menu() {

        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        final JDialog trade = new JDialog();
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back4.png")).getImage(), 0, 0, 400, 400, null);
                super.paintComponent(g);
            }
        };

        JPanel all = new JPanel();
        JLabel title = new JLabel("Menu");
        JButton ok = new JButton("< Resume Game >");
        JButton snd = new JButton("< Bankrupt >");
        JButton rule = new JButton("< Help >");
        JButton quit = new JButton("< Quit Game >");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
            }
        });
        snd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Bankrupt(Player[turn]);
                trade.setVisible(false);
                trade.dispose();
                System.gc();
            }
        });
        rule.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                playHelp();
            }
        });
        quit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trade.setVisible(false);
                trade.dispose();
                sound.closeSounds();
                new MonopolyMenu(options);
                f.dispose();
                f = null;
                System.gc();
            }
        });

        title.setFont(new Font("cooper black", 0, 30));
        ok.setFont(new Font("cooper black", 0, 18));
        snd.setFont(new Font("cooper black", 0, 18));
        rule.setFont(new Font("cooper black", 0, 18));
        quit.setFont(new Font("cooper black", 0, 18));

        ok.setBackground(Color.WHITE);
        snd.setBackground(Color.WHITE);
        rule.setBackground(Color.WHITE);
        quit.setBackground(Color.WHITE);

        all.setLayout(new GridLayout(5, 1, 20, 20));
        all.add(title);
        all.add(ok);
        all.add(snd);
        all.add(rule);
        all.add(quit);
        all.setBounds(100, 50, 200, 300);
        all.setOpaque(false);

        Dimension d = Board.getSize();

        Cback.setBounds(0, 0, 400, 400);
        Cback.add(all, BorderLayout.CENTER);
        Cback.setOpaque(false);
        Cback.setLayout(null);
        trade.setLayout(null);
        trade.add(Cback);
        trade.setBounds((int) (d.height / 2) - 200, (int) (d.height / 2) - 200, 400, 400);

        trade.setUndecorated(true);
        trade.setDefaultCloseOperation(trade.DO_NOTHING_ON_CLOSE);
        trade.setResizable(false);
        trade.setModal(true);
        trade.setVisible(true);
    }

    private void playHelp() {
        final JDialog Idialog = new JDialog();
        final String dir = "images/HELP/";
        final JButton nxt = new JButton(">>>");
        final JButton bck = new JButton("<<<");
        final JButton ok = new JButton("OK");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "1.png", 550, 400);
        help = 1;
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back4.png")).getImage(), 0, 0, 580, 500, null);
                super.paintComponent(g);
            }
        };
        nxt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                p1.removeAll();
                addImage(p1, dir + (help++) + ".png", 550, 400);
                p1.revalidate();
            }
        });
        bck.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                p1.removeAll();
                addImage(p1, dir + (help--) + ".png", 550, 400);
                p1.revalidate();
            }
        });
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Idialog.setVisible(false);
            }
        });
        Idialog.setLayout(new GridLayout(1, 1));
        Idialog.setBounds(200, 300, 580, 500);
        Idialog.add(Cback);
        Cback.setOpaque(false);
        Cback.add(p1);
        Cback.add(bck);
        Cback.add(nxt);
        Cback.add(ok);

        Idialog.setUndecorated(true);
        Idialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Idialog.setResizable(false);
        Idialog.setModal(true);
        Idialog.setVisible(true);

    }

    private class Timer extends Thread {

        Time time, now;

        public Timer(Time t) {
            this.time = t;
            now = new Time();
        }

        public Timer() {
            this.time = new Time(10, 0, 0);
            now = new Time();
        }

        public void run() {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(2);
            int hr = 0;
            int min = 0;
            int sec = 0;

            for (int i = 0; i >= 0; i++) {
                for (int j = 0; j < 60; j++) {
                    for (int k = 0; k < 60; k++) {
                        //timelabel.setText(nf.format(hr) + ":" + nf.format(min) + ":" + nf.format(sec++));
                        now.setTime(i, j, k);
                        timelabel.setText(now.toString());
                        int x = now.compareTo(time);
                        System.out.println("--------------------------> " + x);
                        if (x >= 0) {
                            System.out.println("--------------------------> CHECK FOR WIN");
                        }
                        try {
                            sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                    sec = 0;
                //timelabel.setText(nf.format(hr) + ":" + nf.format(min++) + ":" + nf.format(sec));
                }
                min = 0;
            //timelabel.setText(nf.format(hr++) + ":" + nf.format(min) + ":" + nf.format(sec));
            }

        }
        //}
    }//end date and time class

    private class waittime extends Thread {

        JButton bt;

        public waittime(JButton b) {
            bt = b;
        }

        public void run() {
            try {
                sleep(2000);
                bt.doClick();
            } catch (Exception ex) {
            }
        }
    }

//    public void test() {
//        Cell[1].setOwner(Player[1]);
//        Cell[3].setOwner(Player[1]);
//        Cell[6].setOwner(Player[0]);
//        Cell[8].setOwner(Player[0]);
//        Cell[9].setOwner(Player[0]);
//        Cell[11].setOwner(Player[1]);
//        Cell[13].setOwner(Player[1]);
//        Cell[14].setOwner(Player[1]);
//        Cell[16].setOwner(Player[1]);
//        Cell[18].setOwner(Player[1]);
//        Cell[19].setOwner(Player[0]);
//        Cell[21].setOwner(Player[0]);
//        Cell[23].setOwner(Player[0]);
//        Cell[24].setOwner(Player[0]);
//        Cell[26].setOwner(Player[0]);
//        Cell[27].setOwner(Player[0]);
//        Cell[29].setOwner(Player[0]);
//        Cell[31].setOwner(Player[1]);
//        Cell[32].setOwner(Player[1]);
//        Cell[34].setOwner(Player[0]);
//
//    }
}