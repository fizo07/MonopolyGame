
import javax.swing.*;
import java.awt.*;

class loadingSplash implements Runnable {
    JFrame f;
    JPanel dialog;
    JPanel image;
    JLabel load;
    Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();

    public loadingSplash() {
        f=new JFrame();
        dialog = new JPanel();
        image = new JPanel();
        load = new JLabel(new ImageIcon(getClass().getResource("images/loading.gif")));

        Dimension d = toolkit.getScreenSize();
        dialog.setLayout(new GridLayout(1, 1));
        dialog.setBackground(Color.black);
        dialog.add(load);
        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        f.add(dialog);
        f.setLayout(new GridLayout(1,1));

        f.setUndecorated(true);
        f.setSize(d);
        f.setVisible(true);
    }

    public static void main(String args[]) {
        new loadingSplash();
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        
        f.setVisible(false);        
        f.dispose();
        f=null;
        System.gc();
    }
}