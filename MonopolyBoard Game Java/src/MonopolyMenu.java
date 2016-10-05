
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class MonopolyMenu implements ActionListener {

    JFrame f;
    JPanel menus, bg;
    JButton single, multi, settings, help, exit, back;
    ImageIcon bgicon;
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    Sound sound = new Sound();
    Options options;
    int helper = 1;

    public MonopolyMenu(Options options) {
        this.options = options;
        f = new JFrame();//JFrame("Salamtura's Monopoly");
        menus = new JPanel();
        single = new JButton("< New Game >");
        multi = new JButton("< Multiplayer >");
        settings = new JButton("< Settings >");
        help = new JButton("< Help >");
        exit = new JButton("< Exit Game >");
        Font font = new Font("cooper black", Font.BOLD, 20);

        single.setFont(font);
        multi.setFont(font);
        settings.setFont(font);
        help.setFont(font);
        exit.setFont(font);

        single.setBackground(Color.white);
        multi.setBackground(Color.white);
        settings.setBackground(Color.white);
        help.setBackground(Color.white);
        exit.setBackground(Color.white);

        single.setForeground(Color.DARK_GRAY);
        multi.setForeground(Color.DARK_GRAY);
        settings.setForeground(Color.DARK_GRAY);
        help.setForeground(Color.DARK_GRAY);
        exit.setForeground(Color.DARK_GRAY);

        BackgroundImage();
        Dimension d = toolkit.getScreenSize();
        menus.setBounds((int) (d.width / 2) - 110, (int) (d.height / 2), 220, 270);
        menus.setLayout(new GridLayout(5, 2, 10, 10));
        menus.setOpaque(false);
        menus.add(single);
        menus.add(multi);
        menus.add(settings);
        menus.add(help);
        menus.add(exit);

        bg.setLayout(null);
        bg.add(menus);

        //sound.playMenuBackgrounds();

        f.setUndecorated(true);
        f.setSize(d);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        single.addActionListener(this);
        multi.addActionListener(this);
        settings.addActionListener(this);
        help.addActionListener(this);
        exit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == single) {
            new AddPlayer(options);
            //sound.p.stop();
            sound = null;
            f.dispose();
            f = null;
            System.gc();
        }
        if (e.getSource() == multi) {
            //sound.p.stop();
            sound = null;
            new MultiplayerSetup(options);
            f.dispose();
            f = null;
            System.gc();
        }
        if (e.getSource() == settings) {
            //sound.p.stop();
            sound = null;
            new OptionsMenu(options);
            f.dispose();
            f = null;
            System.gc();
        }
        if (e.getSource() == help) {
            //goto help
            playHelp();
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }

    }

    public void BackgroundImage() {
        bgicon = new ImageIcon(getClass().getResource("images/wallpaper_1.png"));
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

    private void playHelp() {
        final JDialog Idialog = new JDialog();
        final String dir = "images/HELP/";
        final JButton nxt = new JButton(">>>");
        final JButton bck = new JButton("<<<");
        final JButton ok = new JButton("OK");
        final JPanel p1 = new JPanel();
        addImage(p1, dir + "1.png", 550, 400);
        helper = 1;
        JPanel Cback = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(getClass().getResource("images/back4.png")).getImage(), 0, 0, 580, 500, null);
                super.paintComponent(g);
            }
        };
        nxt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                p1.removeAll();
                addImage(p1, dir + (helper++) + ".png", 550, 400);
                p1.revalidate();
            }
        });
        bck.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                p1.removeAll();
                addImage(p1, dir + (helper--) + ".png", 550, 400);
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

    public static void main(String args[]) {
        Options op = new Options(true, "bankrupt", "no", "no");
        MonopolyMenu m = new MonopolyMenu(op);

    }
}