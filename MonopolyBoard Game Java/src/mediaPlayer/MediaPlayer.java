/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mediaPlayer;

/**
 *
 * @author Abdulsalam M.T Umar
 */
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.*;
import javax.media.*;

public class MediaPlayer extends JFrame {
   private Player player;
   private File file;
   private ArrayList<File> filelist = new ArrayList();
   //private String cale;
   private int playingIndex=0;
   public int lungime;
   public int latime;

   public MediaPlayer(int lung, int lat)
   {
//      super( "Demonstrating the Java Media Player" );
      //cale=new String("C:/xampp/htdocs/projects/led/java/clips/");
      lungime=lung;
      latime =lat;

      try {
        playlist();
      } catch (Exception ex) {
      }

      setBackground(Color.black);

      ListIterator li = filelist.listIterator();
//      System.out.println("Forward Reading");

      //Forward direction
      file =  (File) li.next();
      createPlayer();

      setSize( lungime, latime );
      setVisible(true);
   }

   private File openFile(String path) {
      file = new File(path) ;
      try{
          if(!file.canRead()){
              file=null;
          }
      }catch(Exception e){
        System.out.println("Fisierul nu poate fi citit");
      }
      return file;
   }

   private void createPlayer()
   {
      if ( file == null )
         return;

      removePreviousPlayer();

      try {
         // create a new player and add listener
         System.out.println( file.toURL());
         player = Manager.createPlayer( file.toURL() );
         setBackground(Color.black);
         player.addControllerListener( new EventHandler() );
         player.start();  // start player
      }
      catch ( Exception e ){
         JOptionPane.showMessageDialog( this,
            "Invalid file or location", "Error loading file",
            JOptionPane.ERROR_MESSAGE );
      }
   }

   private void removePreviousPlayer()
   {
      if ( player == null )
         return;

      player.stop();

      Component visual = player.getVisualComponent();
      Component control = player.getControlPanelComponent();

      setBackground(Color.black);

      if ( visual != null )
         remove( visual );
      if ( control != null )
         remove( control );
      player.deallocate();
//      player.close();
   }

   public static void main(String args[])
   {
      MediaPlayer app = new MediaPlayer(800, 600);

//      app.addWindowListener(
//         new WindowAdapter() {
//            public void windowClosing( WindowEvent e )
//            {
//               System.exit(0);
//            }
//         }
//      );
   }

   // inner class to handler events from media player
   private class EventHandler implements ControllerListener {
      public void controllerUpdate( ControllerEvent e ) {
         if ( e instanceof RealizeCompleteEvent ) {
            //Container c = getContentPane();

            // load Visual and Control components if they exist
            Component visualComponent =
               player.getVisualComponent();

            if ( visualComponent != null )
               add( visualComponent, BorderLayout.CENTER );

            Component controlsComponent =
               player.getControlPanelComponent();

            if ( controlsComponent != null )
               add( controlsComponent, BorderLayout.SOUTH );

            doLayout();
            validate();
         }
        if(e instanceof EndOfMediaEvent) {
          System.out.println("Received - EndOfMediaEvent");
//          file = new File("C:/xampp/htdocs/projects/led/java/clips/33.AVI") ;
//          createPlayer();
          playNextInList();
        }
      }
   }

   private void playlist() throws FileNotFoundException, IOException{
        FileInputStream fstream;
        fstream = new FileInputStream("PlayerList.txt");
        filelist.clear();

        //DataInputStream in = new DataInputStream(fstream);
        //BufferedReader d = new BufferedReader(new InputStreamReader(fstream));
        BufferedReader in=new BufferedReader(new InputStreamReader(fstream));

        while (in.read() !=0) {
          filelist.add(openFile(in.readLine()));
        }

        in.close();
   }
    void playNextInList()  {
      playingIndex++;
      if(playingIndex == filelist.size())
      playingIndex = 0;
      file= (File)filelist.get(playingIndex);
      createPlayer();
    }

}
