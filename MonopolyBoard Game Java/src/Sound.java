
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdulsalam M.T Umar
 */
public class Sound implements ControllerListener {

    public Player p;
    public Player back;
    public Player music;
    public Clip bgclip,  clip;
    Component visual;
    Component control;
    JPanel musicpanel;

    public Sound() {
        musicpanel = new JPanel();
    }

    public void playMenuBackgrounds() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/no1likeu.mp3").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playBoardBackgrounds() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/no1likeu.mp3").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playDice() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/DICEROLL.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playDiceSK() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/DICESK.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playSiren() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/SIRENS.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playJailDoor() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/JAILDOOR.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playSold() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/AUCSOLD.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playNotSold() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/NOSALE.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playOhoh() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/OHOH.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playSlide() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/SLIDE.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playMoney() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/MONEY.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playCash() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/CASHREG.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playGoBell() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/GOBELL.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playTaxRent() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/TAXRET.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playWhistle() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/WHISTLE.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playJingle() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/JINGLE.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playWolf() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/WOLFWSL.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playBuild() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/BUILD.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playCheers() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/CHEERS.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playOpen() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/OPEN.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playAccept() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/FOGEY.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playDecline() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/CURSING.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playComputer() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/CMPTRS.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playMortgage() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/JACKHAMR.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playUmMortgage() {
        try {
            //URL url=new URL("sound/no1likeu.mp3");
            File song = new File(getClass().getResource("sound/MAGIC.WAV").getFile());
            p = Manager.createPlayer(song.toURI().toURL());
            p.start();
        } catch (Exception ex) {
            Logger.getLogger(MonopolyMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playBackgroundEffect() {
        try {
            URL url = this.getClass().getClassLoader().getResource("sound/gameback.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void musicPlayer() {
        try {
            File song = new File(getClass().getResource("sound/BUILD.WAV").getFile());

            // create a new player and add listener
            music = Manager.createPlayer(song.toURI().toURL());
            music.start(); // start player
            //visual = music.getVisualComponent();
            control = music.getControlPanelComponent();
            musicpanel.setLayout(new GridLayout(1, 1));
            musicpanel.add(control);
        //music.addController((Controller) music.getControlPanelComponent());


        } catch (Exception ex) {
        }
    }

    public void closeSounds() {
        if (clip.isRunning()) {
            clip.stop();
        }

    //p.stop();
    //clip.stop();
    }

    public void controllerUpdate(ControllerEvent c) {
        if (music == null) {
            return;
        }
        if (c instanceof RealizeCompleteEvent) {

            if ((visual = music.getVisualComponent()) != null) {
                musicpanel.add(visual);
            }

        }
    }
}
