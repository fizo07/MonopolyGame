
import javax.swing.*;
import java.awt.*;

class introSplash {

    JFrame dialog;
    JPanel image;
    JLabel img, load;
    JProgressBar pb;
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();

    public introSplash() {
        dialog = new JFrame();
        image = new JPanel();
        img = new JLabel(new ImageIcon(getClass().getResource("images/poster.png")));
        pb = new JProgressBar();
        Dimension d = toolkit.getScreenSize();
        Dimension dm = new Dimension(600, 20);

        img.setBounds((d.width / 2) - 244, (d.height / 2) - 370, 488, 708);
        pb.setBounds(d.width / 2 - 300, d.height / 2 + 380, 600, 20);
        pb.setPreferredSize(dm);
        pb.setStringPainted(true);
        pb.setBorderPainted(true);
        pb.setForeground(Color.BLACK);


        dialog.setBackground(Color.BLACK);
        dialog.setLayout(null);
        dialog.add(img);
        dialog.add(pb);

        dialog.setUndecorated(true);
        dialog.setSize(d);
        dialog.setVisible(true);
        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
            pb.setValue(i);
        }

        Thread t = new Thread(new loadingSplash());
        t.start();
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
        }
        Options op=new Options(true,"bankrupt","no","no");
        new MonopolyMenu(op);
        dialog.setVisible(false);
        dialog = null;
        dialog.dispose();
        System.gc();

    }

    public static void main(String args[]) {
        new introSplash();
    }
}