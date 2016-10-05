package mediaPlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import java.util.Vector;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Media extends JPanel {

    private Player player;
    private File file;
    public Vector playlist = new Vector();
    private int playingIndex = 0;
    public JPanel bg;
    public final JCheckBox bgmusic;

    public Media() {
        bg = new JPanel();
        bgmusic = new JCheckBox("Music Player");
        bg.setLayout(new BorderLayout());
        add(bg);
        JButton openFile = new JButton("Open file to play");
        openFile.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        openFile();
                        createPlayer();
                    }
                });
        bgmusic.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (bgmusic.isSelected()) {
                    openFile();
                    createPlayer();
                } else {
                    removePreviousPlayer();
                }
            }
        });

        bg.add(bgmusic, BorderLayout.NORTH);
        setLayout(new GridLayout(3, 1));
        setPreferredSize(new Dimension(200, 100));
        setVisible(true);
    }

    public void setPlaylist(Vector playlist) {
        this.playlist = playlist;
    }

    private void openFile() {
        if (playlist.isEmpty()) {
            file = null;
            JOptionPane.showMessageDialog(null,"No playlist detected");
            bgmusic.setSelected(false);
        } else {
            String path = String.valueOf(playlist.elementAt(playingIndex));
            file = new File(path);
        }
    }

    private void createPlayer() {
        if (file == null) {
            return;
        }

        removePreviousPlayer();

        try {
            // create a new player and add listener
            player = Manager.createPlayer(file.toURL());
            player.addControllerListener(new EventHandler());
            player.start(); // start player 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid file or location",
                    "Error loading file", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void removePreviousPlayer() {
        if (player == null) {
            return;
        }

        player.stop();

        Component visual = player.getVisualComponent();
        Component control = player.getControlPanelComponent();

        bg.setBackground(Color.black);

        if (visual != null) {
            bg.remove(visual);
        }
        if (control != null) {
            bg.remove(control);
        }
        player.deallocate();
    }

    public static void main(String args[]) {
        Media app = new Media();
    }

    public void playNextInList() {
        playingIndex++;
        if (playingIndex >= playlist.size()) {
            playingIndex = 0;
        }
        String path = String.valueOf(playlist.elementAt(playingIndex));
        file = new File(path);
        createPlayer();
    }

    // inner class to handler events from media player
    private class EventHandler implements ControllerListener {

        public void controllerUpdate(ControllerEvent e) {
            if (e instanceof RealizeCompleteEvent) {
                //bg.setBackground(Color.BLACK);

                // load Visual and Control components if they exist
                Component visualComponent = player.getVisualComponent();

                if (visualComponent != null) {
                    bg.add(visualComponent, BorderLayout.CENTER);
                }
                bg.setBackground(new Color(255, 255, 204));
                Component controlsComponent = player.getControlPanelComponent();

                if (controlsComponent != null) {
                    bg.add(controlsComponent, BorderLayout.SOUTH);
                }

                bg.doLayout();
                bg.revalidate();
                doLayout();
                revalidate();
            }
            if (e instanceof EndOfMediaEvent) {
                playNextInList();
            }
        }
    }
} 
