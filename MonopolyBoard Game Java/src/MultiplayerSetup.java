
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.border.*;
import monopolyUML.Cell;
import monopolyUML.Player;

public class MultiplayerSetup implements ActionListener, KeyListener, Runnable {

    JFrame f;
    //JDialog dialog;
    JPanel hostpanel, hostpanel1, hostpanel2, joinpanel, joinpanel1, joinpanel2, joinpanel3, bg, status, dialog;
    JButton host, cancel, connect, disconnect, back, start, cont;
    JLabel hostlabel, iplabel, portlabel, namelabel, tokenlabel, tokenimage, playerno, statuslabel, pno, pcon, addp;
    JTextField hosttext, iptext, porttext, nametext;
    JComboBox tokenbox, playno;
    JList games;
    ImageIcon bgicon;
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    private JTextArea hostmessage,  joinmessage;
    Thread thread;
    String tokenimages[] = {"images/Player_icons/barrow.gif", "images/Player_icons/boot.gif", "images/Player_icons/car.gif", "images/Player_icons/dog.gif", "images/Player_icons/iron.gif", "images/Player_icons/ship.gif", "images/Player_icons/thimble.gif"};
    String tokenimages2[] = {"images/Player_icons/barrow.png", "images/Player_icons/boot.png", "images/Player_icons/car.png", "images/Player_icons/dog.png", "images/Player_icons/iron.png", "images/Player_icons/ship.png", "images/Player_icons/thimble.png"};
    String tokennames[] = {"BARROW", "BOOT", "CAR", "DOG", "IRON", "SHIP", "THIMBLE"};
    String number[] = {"2", "3", "4"};
    String contype = "";
    Vector gamelist = new Vector();
    Player Player[];
    Vector clients = new Vector();
    Options options;

    //Connection Management
    private static final int BUFFER_SIZE = 255;
    private static final long CHANNEL_WRITE_SLEEP = 10L;
    private ByteBuffer writeBuffer;
    private ByteBuffer readBuffer;
    private boolean running;
    private SocketChannel channel;
    private Selector readSelector;
    private CharsetDecoder asciiDecoder;
    public MonopolyServer monoserver;
    ChatterServer chatserver;
    MultiplayerBoard Game;
    ChatterClient chat;
    public int turn;
    public int noofplayers = 0;
    public int conplayers = 0;
    public int addedplayers = 0;
    public boolean ishost = false;

    public MultiplayerSetup(Options options) {
        this.options = options;
        setClient();
        f = new JFrame();
        hostpanel = new JPanel();
        joinpanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 800, 175, joinpanel);
                super.paintComponent(g);
            }
        };
        joinpanel1 = new JPanel();
        joinpanel2 = new JPanel();
        joinpanel3 = new JPanel();
        hostpanel1 = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 800, 175, hostpanel1);
                super.paintComponent(g);
            }
        };
        hostpanel2 = new JPanel();
        host = new JButton("Start Server", new ImageIcon(getClass().getResource("images/conicon.png")));
        cancel = new JButton("Connect", new ImageIcon(getClass().getResource("images/log.gif")));
        connect = new JButton("Join Game");
        disconnect = new JButton("Disconnect");
        back = new JButton(new ImageIcon(getClass().getResource("images/return.png")));
        cont = new JButton("Add Player", new ImageIcon(getClass().getResource("images/Edit.gif")));
        start = new JButton(new ImageIcon(getClass().getResource("images/gamestart_icon.gif")));
        hostlabel = new JLabel("   Game Host IP");
        iplabel = new JLabel("   Your IP");
        portlabel = new JLabel("   Port No");
        namelabel = new JLabel("  Player Name");
        tokenlabel = new JLabel("  Token");
        playerno = new JLabel("Number of Players");
        statuslabel = new JLabel("please start game....");
        pno = new JLabel(String.valueOf(noofplayers));
        pcon = new JLabel("NIL");
        addp = new JLabel("NIL");
        tokenimage = new JLabel(new ImageIcon(getClass().getResource("images/Player_icons/barrow.gif")));
        tokenimage.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        hosttext = new JTextField("192.168.0.102");
        iptext = new JTextField(getLocalHost());
        porttext = new JTextField("12345");
        nametext = new JTextField();
        tokenbox = new JComboBox(tokennames);
        playno = new JComboBox(number);
        games = new JList();
        games.setVisibleRowCount(5);
        games.setFixedCellWidth(200);
        games.setFixedCellHeight(20);
        games.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //connect.setEnabled(false);
        //disconnect.setEnabled(false);
        start.setEnabled(false);
        hostmessage = new JTextArea();
        joinmessage = new JTextArea();
        hostmessage.setBackground(Color.black);
        joinmessage.setBackground(Color.black);
        hostmessage.setForeground(Color.red);
        joinmessage.setForeground(Color.red);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        BackgroundImage();

        hostpanel1.setLayout(new GridLayout(5, 2, 10, 10));
        hostpanel1.setBorder(raisedetched);
        hostpanel1.setOpaque(false);
        hostpanel1.add(hostlabel);
        hostpanel1.add(hosttext);
        hostpanel1.add(iplabel);
        hostpanel1.add(iptext);
        hostpanel1.add(portlabel);
        hostpanel1.add(porttext);
        hostpanel1.add(playerno);
        hostpanel1.add(playno);
        //hostpanel1.add(host);
        //hostpanel1.add(cancel);

        JScrollPane jt = new JScrollPane(hostmessage);
        hostpanel2.setBorder(raisedetched);
        hostpanel2.setLayout(new GridLayout(1, 1, 10, 10));
        hostpanel2.setOpaque(false);
        hostpanel2.add(jt);

        //hostpanel.setBorder(BorderFactory.createTitledBorder("Host Game"));
        hostpanel.setLayout(new GridLayout(1, 2));
        hostpanel.setOpaque(false);
        hostpanel.add(hostpanel1);
        //hostpanel.add(hostpanel2);
        statusPanel();
        hostpanel.add(status);
        hostpanel.setPreferredSize(new Dimension(700, 175));

        joinpanel1.setLayout(new GridLayout(3, 2, 10, 10));
        joinpanel1.setOpaque(false);
        joinpanel1.add(namelabel);
        joinpanel1.add(nametext);
        joinpanel1.add(tokenlabel);
        joinpanel1.add(tokenbox);
        joinpanel1.add(new JLabel());
        joinpanel1.add(tokenimage);
        //joinpanel1.add(connect);
        //joinpanel1.add(disconnect);

        joinpanel2.setLayout(new GridLayout(1, 2));
        joinpanel2.setOpaque(false);
        joinpanel2.add(games);

        joinpanel3.setLayout(new GridLayout(1, 1));
        joinpanel3.setOpaque(false);
        joinpanel3.add(new JScrollPane(joinmessage));

        //joinpanel.setBorder(BorderFactory.createTitledBorder("Join Game"));
        joinpanel.setLayout(new GridLayout(1, 2));
        joinpanel.setOpaque(false);
        joinpanel.add(joinpanel1);
        joinpanel.add(joinpanel2);
        joinpanel.setPreferredSize(new Dimension(700, 175));
        //joinpanel.add(joinpanel3);
        //joinpanel.add(games);
        games.setBorder(BorderFactory.createTitledBorder("Connected Game Players"));
        hostpanel.setBounds(100, 150, 800, 175);
        joinpanel.setBounds(100, 350, 800, 120);
        games.setBounds(100, 480, 200, 150);
        back.setBounds(350, 560, 150, 70);
        start.setBounds(350, 480, 150, 70);

        back.setPreferredSize(new Dimension(150, 50));
        start.setPreferredSize(new Dimension(150, 70));
        host.setPreferredSize(new Dimension(150, 50));
        cancel.setPreferredSize(new Dimension(150, 50));
        cont.setPreferredSize(new Dimension(150, 50));
        connect.setPreferredSize(new Dimension(150, 50));

        bg.setLayout(null);
        //bg.add(hostpanel);
        //bg.add(joinpanel);
        //bg.add(games);
        //bg.add(back);
        //bg.add(start);


        f.setUndecorated(true);
        Dimension d = toolkit.getScreenSize();
        f.setSize(d);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
        f.setVisible(true);

        back.addActionListener(this);
        host.addActionListener(this);
        connect.addActionListener(this);
        tokenbox.addActionListener(this);
        start.addActionListener(this);
        disconnect.addActionListener(this);
        cancel.addActionListener(this);

        hosttext.addKeyListener(this);
        iptext.addKeyListener(this);
        porttext.addKeyListener(this);
        nametext.addKeyListener(this);

        ChooseConnection();
    }

    public void ChooseConnection() {
        dialog = new JPanel();
        JPanel backbg = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 800, 300, joinpanel);
                super.paintComponent(g);
            }
        };
        JButton hostgame = new JButton(new ImageIcon(getClass().getResource("images/hostgame.png")));
        JButton joingame = new JButton(new ImageIcon(getClass().getResource("images/joingame.png")));

        hostgame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ishost = true;
                dialog.setVisible(false);
                hostGame();
            }
        });
        joingame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                joinGame();
            }
        });

        hostgame.setOpaque(false);
        hostgame.setBackground(Color.WHITE);
        hostgame.setBorderPainted(false);
        joingame.setOpaque(false);
        joingame.setBackground(Color.WHITE);
        joingame.setBorderPainted(false);

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        p2.add(hostgame);
        p2.add(joingame);
        p2.setOpaque(false);

        p1.add(p2);
        p1.setOpaque(false);
        p1.setBorder(raisedetched);
        p1.setPreferredSize(new Dimension(700, 220));
        backbg.add(p1);
        backbg.add(back);
        backbg.setOpaque(false);

        //bg.remove(dialog);
        bg.add(dialog);
        bg.revalidate();
        bg.repaint();

        Dimension d = toolkit.getScreenSize();

        dialog.setLayout(new GridLayout(1, 1));
        dialog.setBounds((int) (d.width / 2) - 400, (int) (d.height / 2) - 250, 800, 300);
        dialog.setAutoscrolls(true);
        dialog.add(backbg);
        //dialog.setUndecorated(true);
        //dialog.setSize(800,250);
        //dialog.setModal(true);
        dialog.setVisible(true);


    }

    public void hostGame() {
        dialog = new JPanel();
        JPanel backbg = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 800, 250, joinpanel);
                super.paintComponent(g);
            }
        };
        cont.setEnabled(false);
        cont.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                addPlayer();
            }
        });
        backbg.add(hostpanel);
        backbg.add(host);
        backbg.add(cont);
        backbg.add(back);


        backbg.setOpaque(false);

        Dimension d = toolkit.getScreenSize();

        bg.remove(dialog);
        bg.add(dialog);
        bg.revalidate();
        bg.repaint();

        dialog.setBounds((int) (d.width / 2) - 400, (int) (d.height / 2) - 250, 800, 250);
        dialog.add(backbg);
        dialog.setLayout(new GridLayout(1, 1));
//        dialog.setUndecorated(true);
//        dialog.setSize(800, 250);
//        //dialog.setAlwaysOnTop(true);
//        dialog.setModal(true);
        dialog.setVisible(true);
    //dialog.setModal(true);
    }

    public void joinGame() {
        dialog = new JPanel();
        JPanel backbg = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 800, 250, joinpanel);
                super.paintComponent(g);
            }
        };
        JLabel iplabel1 = new JLabel("    Host IP Address");
        JLabel yourlabel = new JLabel("    Your IP Address");
        JLabel portlabel1 = new JLabel("    Connection port no");
        //JTextField ipText = new JTextField("192.168.0.102");
        final JTextField yourText = new JTextField(getLocalHost());
        final JTextField portText = new JTextField("12345");

        yourText.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                if (yourText.getText().length() >= 15) {
                    e.consume();
                }
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        portText.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                if (portText.getText().length() >= 5) {
                    e.consume();
                }
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        JPanel p = new JPanel();
        JPanel p1 = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back3.png")).getImage(), 0, 0, 700, 150, joinpanel);
                super.paintComponent(g);
            }
        };
        final JButton b = new JButton("Connect", new ImageIcon(getClass().getResource("images/log.gif")));
        b.setPreferredSize(new Dimension(150, 50));
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startConnection();
                b.setEnabled(false);
            }
        });
        cont.setEnabled(false);
        cont.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                addPlayer();
            }
        });
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        p.add(iplabel1);
        p.add(hosttext);
        p.add(yourlabel);
        p.add(yourText);
        p.add(portlabel1);
        p.add(portText);
        p.setLayout(new GridLayout(3, 2, 10, 10));
        p.setOpaque(false);
        p.setBorder(raisedetched);

        statusPanel();
        p1.add(p);
        //p1.add(hostmessage);
        p1.add(status);
        p1.setLayout(new GridLayout(1, 2));
        p1.setPreferredSize(new Dimension(700, 150));
        p1.setOpaque(false);
        p1.setBorder(raisedetched);

        backbg.add(p1);
        backbg.add(b);
        backbg.add(cont);
        backbg.add(back);

        backbg.setOpaque(false);

        bg.remove(dialog);
        bg.add(dialog);
        bg.revalidate();
        bg.repaint();

        Dimension d = toolkit.getScreenSize();

        dialog.setBounds((int) (d.width / 2) - 400, (int) (d.height / 2) - 250, 800, 250);
        dialog.add(backbg);
        dialog.setLayout(new GridLayout(1, 1));
//        dialog.setUndecorated(true);
//        dialog.setSize(800, 250);
//        //dialog.setAlwaysOnTop(true);
//        dialog.setModal(true);
        dialog.setVisible(true);
    //dialog.setModal(true);
    }

    public void addPlayer() {
        dialog = new JPanel();
        JPanel backbg = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back5.png")).getImage(), 0, 0, 800, 250, joinpanel);
                super.paintComponent(g);
            }
        };

        backbg.add(joinpanel);
        backbg.add(connect);
        backbg.add(back);


        backbg.setOpaque(false);
        joinpanel2.add(status);

        bg.remove(dialog);
        bg.add(dialog);
        bg.revalidate();
        bg.repaint();

        Dimension d = toolkit.getScreenSize();

        dialog.setBounds((int) (d.width / 2) - 400, (int) (d.height / 2) - 250, 800, 250);
        dialog.add(backbg);
        dialog.setLayout(new GridLayout(1, 1));
//        dialog.setUndecorated(true);
//        dialog.setSize(800, 250);
//        //dialog.setAlwaysOnTop(true);
//        dialog.setModal(true);
        dialog.setVisible(true);

    }

    public void GameReady() {
        dialog = new JPanel();
        JPanel backbg = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back4.png")).getImage(), 0, 0, 800, 250, joinpanel);
                super.paintComponent(g);
            }
        };
        JLabel text = new JLabel("Please wait while the Host Player starts the game...!");
        text.setForeground(Color.RED);
        text.setFont(new Font("cooper black", Font.BOLD, 20));
        final JPanel img = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/ready.png")).getImage(), 0, 0, 250, 180, null);
                super.paintComponent(g);
            }
        };//(new ImageIcon(getClass().getResource("images/ready.png")));
        img.setPreferredSize(new Dimension(250, 180));
        img.setOpaque(false);

        backbg.add(img);

        if (ishost) {
            start.setEnabled(true);
            backbg.add(start);
        } else {
            backbg.add(text);
        }

        backbg.setOpaque(false);

        bg.remove(dialog);
        bg.revalidate();
        bg.repaint();
        bg.add(dialog);
        bg.revalidate();
        bg.repaint();

        Dimension d = toolkit.getScreenSize();

        dialog.setBounds((int) (d.width / 2) - 400, (int) (d.height / 2) - 250, 800, 250);
        dialog.add(backbg);
        dialog.setLayout(new GridLayout(1, 1));
//        dialog.setUndecorated(true);
//        dialog.setSize(800, 250);
//        //dialog.setAlwaysOnTop(true);
//        dialog.setModal(true);
        dialog.setVisible(true);
    //dialog.setModal(true);
    }

    public void statusPanel() {
        status = new JPanel();
        JLabel nolabel = new JLabel("Number of Players :");
        JLabel conlabel = new JLabel("Players Connected :");
        JLabel stlabel = new JLabel("Status :");
        JLabel addlabel = new JLabel("Players added :");

        pno.setForeground(Color.RED);
        pcon.setForeground(Color.RED);
        addp.setForeground(Color.RED);
        statuslabel.setForeground(Color.BLUE);

        status.add(nolabel);
        status.add(pno);
        status.add(conlabel);
        status.add(pcon);
        status.add(addlabel);
        status.add(addp);
        status.add(stlabel);
        status.add(statuslabel);
        status.setBorder(BorderFactory.createTitledBorder("Game Status"));
        status.setLayout(new GridLayout(4, 2, 10, 10));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            try {
                dialog.setVisible(false);
                monoserver.serverchannel.close();
                monoserver.f.setVisible(false);
                monoserver.f.dispose();
            //connection.close();
            } catch (Exception ae) {
            }
            new MonopolyMenu(options);
            f.dispose();
        }
        if (e.getSource() == host) {
            int x = Integer.parseInt(String.valueOf(playno.getSelectedItem()));
            chatserver = new ChatterServer("192.168.0.102");
            monoserver = new MonopolyServer(x, Integer.parseInt(porttext.getText()));
            monoserver.start();
            chatserver.start();
            try {
                Thread.sleep(3000);
            } catch (Exception ae) {
            }
            startConnection();
            host.setEnabled(false);
        //start.setEnabled(true);
        }
        if (e.getSource() == connect) {
            if (nametext.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Enter your name to proceed!");
            } else {
                chat = new ChatterClient("192.168.0.102", nametext.getText());
                //chat.start();
                Vector vec = new Vector();
                vec.add(nametext.getText());
                vec.add(tokenimages[tokenbox.getSelectedIndex()]);
                vec.add(tokenimages2[tokenbox.getSelectedIndex()]);
                //clients.clear();
                clients.add(vec);
                //sendVector(vec);
                gamelist.clear();
                for (int i = 0; i < clients.size(); i++) {
                    Vector c = (Vector) clients.elementAt(i);
                    gamelist.add(c.elementAt(0));
                }
                games.setListData(gamelist);
                disconnect.setEnabled(true);
                sendMessage("#-NEWPLAYER-" + nametext.getText() + "-" + tokenimages[tokenbox.getSelectedIndex()] + "-" + tokenimages2[tokenbox.getSelectedIndex()]);
                try {
                    Thread.sleep(1000);
                } catch (Exception ae) {
                }
                addedplayers++;
                addp.setText(String.valueOf(addedplayers));
                connect.setEnabled(false);

                if (addedplayers == conplayers) {
                    dialog.setVisible(false);
                    GameReady();
                }
            }

        }
        if (e.getSource() == tokenbox) {
            tokenimage.setIcon(new ImageIcon(getClass().getResource(tokenimages[tokenbox.getSelectedIndex()])));
        }
        if (e.getSource() == start) {
            sendMessage("#-NEWGAME");
            startGame();
        }
        if (e.getSource() == disconnect) {
            host.setEnabled(true);
            cancel.setEnabled(true);
            connect.setEnabled(true);
            clients.clear();
        //closeConnection();
        }
        if (e.getSource() == cancel) {
            startConnection();
            host.setEnabled(false);
            cancel.setEnabled(false);
            connect.setEnabled(true);
        }
    }

    public void setClient() {
        writeBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        asciiDecoder = Charset.forName("US-ASCII").newDecoder();

    }

    // manipulates displayArea in the event-dispatch thread
    public void startGame() {
        if (clients.size() > 1) {
            //new loadingSplash();//splash page
            Player = new Player[clients.size()];
            for (int i = 0; i < clients.size(); i++) {
                Vector cast = (Vector) clients.elementAt(i);
                Player[i] = new Player(cast.elementAt(0).toString(), 1500, 0, new JLabel(new ImageIcon(getClass().getResource(cast.elementAt(1).toString()))), new JLabel(new ImageIcon(getClass().getResource(cast.elementAt(2).toString()))), false, false);
            }
            Thread t = new Thread(new loadingSplash());
            t.start();
            dialog.setVisible(false);
            Game = new MultiplayerBoard(Player, this, chat, options);
            f.dispose();
        //hostmessage.append("\n"+data);
        } else {
            JOptionPane.showMessageDialog(null, "Please add Players to Start game!");
        }
    }

    //get the IP address of the user's computer
    public String getLocalHost() {
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            hostmessage.append("\n" + "Unknown Host");
        }
        return localHost;
    }

    public void BackgroundImage() {
        bgicon = new ImageIcon(getClass().getResource("images/back6.png"));
        bg = new JPanel() {

            @Override
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

    private void addNewPlayer(String[] analyzer) {
        Vector vec = new Vector();
        vec.add(analyzer[2]);
        vec.add(analyzer[3]);
        vec.add(analyzer[4]);
        //clients.clear();
        clients.add(vec);
        System.out.println(clients);
        gamelist.clear();
        for (int j = 0; j < clients.size(); j++) {
            Vector c = (Vector) clients.elementAt(j);
            gamelist.add(c.elementAt(0));
        }
        games.setListData(gamelist);
    //connect.setEnabled(false);
    }

    private void connect(String hostname) {
        try {
            readSelector = Selector.open();
            InetAddress addr = InetAddress.getByName(hostname);
            channel = SocketChannel.open(new InetSocketAddress(addr, Integer.parseInt(porttext.getText())));
            channel.configureBlocking(false);
            channel.register(readSelector, SelectionKey.OP_READ, new StringBuffer());
            hostmessage.append("\nYour connection was successful!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "could not connect to server !");
        }
    }

    private void readIncomingMessages() {
        // check for incoming mesgs
        try {
            // non-blocking select, returns immediately regardless of how many keys are ready
            readSelector.selectNow();

            // fetch the keys
            Set readyKeys = readSelector.selectedKeys();

            // run through the keys and process
            Iterator i = readyKeys.iterator();
            while (i.hasNext()) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();
                SocketChannel channel = (SocketChannel) key.channel();
                readBuffer.clear();

                // read from the channel into our buffer
                long nbytes = channel.read(readBuffer);

                // check for end-of-stream
                if (nbytes == -1) {
                    hostmessage.append("\n" + "disconnected from server: end-of-stream");
                    channel.close();
                    shutdown();
                //it.shutdown();
                } else {
                    // grab the StringBuffer we stored as the attachment
                    StringBuffer sb = (StringBuffer) key.attachment();
                    // use a CharsetDecoder to turn those bytes into a string
                    // and append to our StringBuffer
                    readBuffer.flip();
                    String str = asciiDecoder.decode(readBuffer).toString();
                    sb.append(str);
                    readBuffer.clear();
                    // check for a full line and write to STDOUT
                    String line = sb.toString();
                    if (!line.startsWith("#")) {
                        line = "#-" + line;
                    }
                    String analyzer[] = line.split("-");
                    for (int k = 0; k < analyzer.length; k++) {
                        System.out.println(">>>" + analyzer[k]);
                    }
                    if (line.startsWith("#-NO-")) {
                        int z = Integer.parseInt(analyzer[2]);
                        this.noofplayers = z;
                        pno.setText(analyzer[2]);
                    }
                    if (line.startsWith("#-CON-")) {
                        int z = Integer.parseInt(analyzer[2]);
                        this.conplayers = z;
                        pcon.setText(analyzer[2] + " / " + noofplayers);
                        if (conplayers == noofplayers) {
                            statuslabel.setText("All players ready...");
                            cont.setEnabled(true);
                        } else {
                            statuslabel.setText("Please wait...");
                        }
                    }
                    if (line.startsWith("#-NEWPLAYER")) {
                        System.out.println("new player");
                        addNewPlayer(analyzer);
                        addedplayers++;
                        addp.setText(String.valueOf(addedplayers));
                        if (addedplayers == conplayers) {
                            dialog.setVisible(false);
                            GameReady();
                        }
                    }
                    if (line.startsWith("#-TURN")) {
                        this.turn = Integer.parseInt(analyzer[2]);
                        hostmessage.append("\nYour turn is: " + turn);
                    }
                    if (line.startsWith("#-NEWGAME")) {
                        startGame();
                    }
                    if (line.startsWith("#-MOVEPLAYER")) {
                        Game.moveToken(Game.Player[Integer.parseInt(analyzer[2])], Integer.parseInt(analyzer[3]));
                    }
                    if (line.startsWith("#-NEWTURN-")) {
                        System.out.println("turn recieved : " + turn);
                        if (Integer.parseInt(analyzer[2]) == turn) {
                            Game.startTurn();
                            System.out.println("turn processed ");
                        } else {
                            int otherturn = Integer.parseInt(analyzer[2]);
                            Game.otherTurn(otherturn);
                            System.out.println("not this turn");
                        }
                    }

                    if (line.startsWith("#-INJAIL")) {
                        Game.Player[Integer.parseInt(analyzer[2])].setInJail(true);
                    }
                    if (line.startsWith("#-OUTJAIL")) {
                        Game.Player[Integer.parseInt(analyzer[2])].setInJail(false);
                    }                    
                    if (line.startsWith("#-PURCHASEPROPERTY")) {
                        int pos = Integer.parseInt(analyzer[3]);
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Player[Integer.parseInt(analyzer[2])].purchaseProperty(Game.Cell[pos], pos, Game.Cell[pos].getCost());
                        Game.purchasedProperty(Game.Player[x]);
                    }
                    if (line.startsWith("#-PAYRENT")) {
                        int x = Integer.parseInt(analyzer[3]);
                        int y = Integer.parseInt(analyzer[4]);

                        int amount = 0;
                        if (analyzer[2].equals("RAIL")) {
                            Game.Player[x].payRentTo(Game.Cell[y].getOwner(), Game.Cell[y].getRent(Player[x].getNoOfRailRoads()));
                            amount = Game.Cell[y].getRent(Player[x].getNoOfRailRoads());
                        }
                        if (analyzer[2].equals("UTIL")) {
                            int z = Integer.parseInt(analyzer[5]);
                            Game.Player[x].payRentTo(Game.Cell[y].getOwner(), Game.Cell[y].getRent(Player[x].getNoOfUtilities(), z));
                            amount = Game.Cell[y].getRent(Player[x].getNoOfUtilities(), z);
                        }
                        if (analyzer[2].equals("NORM")) {
                            Game.Player[x].payRentTo(Game.Cell[y].getOwner(), Game.Cell[y].getRent());
                            amount = Game.Cell[y].getRent();
                        }
                        if (Game.Player[turn] == Game.Cell[y].getOwner()) {
                            Game.playRentPaid(x, amount);
                        }
                        Game.updatePlayerDetails();
                    }
                    if (line.startsWith("#-PAYBANK")) {
                        int x = Integer.parseInt(analyzer[2]);
                        int y = Integer.parseInt(analyzer[3]);
                        Game.Player[x].payBank(y);
                    }
                    if (line.startsWith("#-PAYPLAYER")) {
                        int x = Integer.parseInt(analyzer[2]);
                        int y = Integer.parseInt(analyzer[3]);
                        Game.bank.payPlayer(Game.Player[x], y);
                    }
                    if (line.startsWith("#-AQUIREDCHANCECARD")) {
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Player[x].aquiredChanceJailCard();
                    }
                    if (line.startsWith("#-AQUIREDCCHESTCARD")) {
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Player[x].aquiredCommunityJailCard();
                    }
                    if (line.startsWith("#-USECOMMUNITYCARD")) {
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Player[x].useCommunityJailCard();
                    }
                    if (line.startsWith("#-USECHANCECARD")) {
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Player[x].useChanceJailCard();
                    }
                    if (line.startsWith("#-CARDDRAWN")) {
                        int x = Integer.parseInt(analyzer[2]);
                        int y = Integer.parseInt(analyzer[3]);
                        int z = Integer.parseInt(analyzer[4]);
                        Game.cardDrawn(Game.Player[x], y, z);
                    }
                    if (line.startsWith("#-PURCHASEHOUSE")) {
                        int x = Integer.parseInt(analyzer[2]);
                        int y = Integer.parseInt(analyzer[3]);
                        Game.houseAdded(x, y);
                    }
                    if (line.startsWith("#-SELLHOUSE")) {
                        int x = Integer.parseInt(analyzer[2]);
                        int y = Integer.parseInt(analyzer[3]);
                        Game.houseRemoved(x, y);
                    }
                    if (line.startsWith("#-MORTGAGE")) {
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Cell[x].setMortgage(true);
                    }
                    if (line.startsWith("#-UNMORTGAGE")) {
                        int x = Integer.parseInt(analyzer[2]);
                        Game.Cell[x].setMortgage(false);
                    }
                    if (line.startsWith("#-UPDATEPLAYERDETAILS")) {
                        Game.updatePlayerDetails2();
                    }
                    if (line.startsWith("#-TRADE")) {
                        int a = Integer.parseInt(analyzer[2]);
                        int b = Integer.parseInt(analyzer[5]);
                        int m1 = Integer.parseInt(analyzer[3]);
                        int m2 = Integer.parseInt(analyzer[6]);
                        String k = analyzer[4];
                        String l = analyzer[7];
                        String sa[] = k.split(".");
                        String sd[] = l.split(".");
                        int x[] = new int[sa.length];
                        int y[] = new int[sd.length];
                        Cell c1[] = new Cell[sa.length];
                        Cell c2[] = new Cell[sd.length];
                        for (int j = 0; j < x.length; j++) {
                            x[j] = Integer.parseInt(sa[j]);
                            System.out.print("----------------> " + sa[j]);
                        }
                        for (int j = 0; j < y.length; j++) {
                            y[j] = Integer.parseInt(sd[j]);
                            System.out.print("----------------> " + sd[j]);
                        }
                        for (int j = 0; j < c1.length; j++) {
                            c1[j] = Game.Cell[x[j]];
                        }
                        for (int j = 0; j < c2.length; j++) {
                            c2[j] = Game.Cell[y[j]];
                        }
                        Game.tradeProperty(Game.Player[a], m1, c1, Game.Player[b], m2, c2);

                    }
                    if (line.startsWith("#-MESSAGECHAT")) {
                        String namer = analyzer[2];
                        String mess = analyzer[3];
                        Game.log.append(namer + " >>> " + mess + "\n");
                        //log.append(namer + " >>> " + mess + "\n");
                        System.out.println("YES!!!");
                    }
                    if (line.startsWith("#-MESSAGES")) {
                        Game.updateMessage2(analyzer[2]);
                    }


                    System.out.println("no containing value");
                    sb.delete(0, sb.length());
                    hostmessage.append("\n" + line);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Client error" + e.getMessage());
            e.printStackTrace();
            hostmessage.append("\n Connection terminated!");
            try {
                channel.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiplayerSetup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendMessage(String mesg) {
        prepWriteBuffer(mesg);
        channelWrite(channel, writeBuffer);
    }

    public void prepWriteBuffer(String mesg) {
        // fills the buffer from the given string
        // and prepares it for a channel write
        writeBuffer.clear();
        writeBuffer.put(mesg.getBytes());
        writeBuffer.putChar('\n');
        writeBuffer.flip();
    }

    public void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
        long nbytes = 0;
        long toWrite = writeBuffer.remaining();

        // loop on the channel.write() call since it will not necessarily
        // write all bytes in one shot
        try {
            while (nbytes != toWrite) {
                nbytes += channel.write(writeBuffer);

                try {
                    Thread.sleep(CHANNEL_WRITE_SLEEP);
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "error sending " + e.getMessage());
        }

        // get ready for another write if needed
        writeBuffer.rewind();
    }

    public void shutdown() {
        running = false;
        thread = null;
        System.exit(1);
    }

    public void run() {
        connect(hosttext.getText());
        //it = new InputThread(this);
        //it.start();
        running = true;
        while (running) {
            readIncomingMessages();
            // nap for a bit
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
        }
    }

    private void startConnection() {
        thread = new Thread(this);
        thread.start();
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == hosttext) {
            if (hosttext.getText().length() >= 15) {
                e.consume();
            }
        }
        if (e.getSource() == nametext) {
            if (nametext.getText().length() >= 7) {
                e.consume();
            }
        }
        if (e.getSource() == porttext) {
            if (porttext.getText().length() >= 5) {
                e.consume();
            }
        }
        if (e.getSource() == iptext) {
            if (iptext.getText().length() >= 15) {
                e.consume();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}