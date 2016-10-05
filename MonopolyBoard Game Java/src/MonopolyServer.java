
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Abdulsalam M.T Umar
 */
public class MonopolyServer extends Thread implements Runnable {

    JDialog f;
    JTextArea ta;
    ServerSocketChannel serverchannel;
    int port;
    int num_players;
    int con_players = 0;
    InetAddress addr;
    Selector readselector;
    Selector acceptselector;
    //KeySelector selectkey;
    SelectionKey selectKey;
    boolean running;
    LinkedList clients;
    ByteBuffer readBuffer;
    ByteBuffer writeBuffer;
    CharsetDecoder asciiDecoder;
    static final int BUFFER_SIZE = 6000;
    static final long CHANNEL_WRITE_SLEEP = 10L;
    public int turn = -1;

    public MonopolyServer(int num_players, int port) {
        this.num_players = num_players;
        this.port = port;
        //ServerSocket();
        clients = new LinkedList();
        readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        writeBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        asciiDecoder = Charset.forName("US-ASCII").newDecoder();

        //setting up GUI
        f = new JDialog();
        ta = new JTextArea();
        f.setLayout(new GridLayout(1, 1));
        f.add(new JScrollPane(ta));
        ta.setLineWrap(true);
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.RED);
        f.setAlwaysOnTop(false);
        f.setSize(500, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    @Override
    public void run() {
        runServerSocket();
        running = true;
        // block while we wait for a client to connect
        while (running) {
            // check for new client connections
            acceptNewConnections();

            // check for incoming mesgs
            readIncomingMessages();

            // sleep a bit
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
            }
        }
    }

    public void runServerSocket() {
        try {
            ta.append("\nHosting server......");
            serverchannel = ServerSocketChannel.open();
            serverchannel.configureBlocking(false);
            addr = InetAddress.getLocalHost();
            serverchannel.socket().bind(new InetSocketAddress(addr, port));
            readselector = Selector.open();
            ta.append("\nconnection successfull. waiting for clients...");
        } catch (IOException ex) {
            Logger.getLogger(MonopolyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acceptNewConnections() {
        try {
            SocketChannel clientChannel;
            // since sSockChan is non-blocking, this will return immediately
            // regardless of whether there is a connection available
            while ((clientChannel = serverchannel.accept()) != null && (con_players < num_players)) {
                addNewClient(clientChannel);
                sendBroadcastMessage("login from: " + clientChannel.socket().getInetAddress(), clientChannel);
                sendMessage(clientChannel, "Welcome " + clientChannel.socket().getInetAddress().getHostName() + " to mutiplayer setup, there are " + clients.size() + " users online.");

                try {
                    Thread.sleep(500);
                } catch (Exception ae) {
                }
                sendMessage(clientChannel,"NO-"+num_players);
                try {
                    Thread.sleep(500);
                } catch (Exception ae) {
                }
                con_players++;
                sendMessage(clientChannel,"CON-"+con_players);
                try {
                    Thread.sleep(500);
                } catch (Exception ae) {
                }
                sendBroadcastMessage("CON-"+con_players,clientChannel);
            }
        } catch (IOException ioe) {
            //log.warn("error during accept(): ", ioe);
        } catch (Exception e) {
            //log.error("exception in acceptNewConnections()", e);
        }
    }

    public void addNewClient(SocketChannel chan) {
        try {
            clients.add(chan);
            chan.configureBlocking(false);
            SelectionKey readKey = chan.register(readselector, SelectionKey.OP_READ, new StringBuffer());
        } catch (IOException ex) {
            Logger.getLogger(MonopolyServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readIncomingMessages() {
        try {
            // non-blocking select, returns immediately regardless of how many keys are ready
            readselector.selectNow();

            // fetch the keys
            Set readyKeys = readselector.selectedKeys();

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
                    //log.info("disconnect: " + channel.socket().getInetAddress() + ", end-of-stream");
                    channel.close();
                    clients.remove(channel);
                    sendBroadcastMessage("logout: " + channel.socket().getInetAddress(), channel);
                } else {
                    // grab the StringBuffer we stored as the attachment
                    StringBuffer sb = (StringBuffer) key.attachment();
                    // use a CharsetDecoder to turn those bytes into a string
                    // and append to our StringBuffer
                    readBuffer.flip();
                    String str = asciiDecoder.decode(readBuffer).toString();
                    readBuffer.clear();
                    sb.append(str);

                    // check for a full line
                    String line = sb.toString();
                    line = line.trim();
                    //displaying recieved message...
                    ta.append("\n" + channel.socket().getInetAddress().getHostName() + ">>> " + line);
                    //StringTokenizer st;
                    String analyzer[] = line.split("-");
                    if (analyzer[0].equals("#")) {
                        sendBroadcastMessage(line, channel);
                        Thread.sleep(500);
                        if (line.startsWith("#-NEWPLAYER-")) {
                            sendMessage(channel, "#-TURN-" + (++turn));
                            //turn = turn + 1;
                            System.out.println("turn sent");
                        }
                        if (line.startsWith("#-ENDTURN")) {
                            if (Integer.parseInt(analyzer[2]) < (turn)) {
                                int x = Integer.parseInt(analyzer[2]);
                                x += 1;
                                sendBroadcastMessage("#-NEWTURN-" + x+"-*", channel);
                                Thread.sleep(500);
                                sendMessage(channel,"#-NEWTURN-" + x+"-*");
                                System.out.println("current turn is : " + x);
                            } else {
                                int x = 0;
                                sendBroadcastMessage("#-NEWTURN-" + x+"-*", channel);
                                Thread.sleep(500);
                                sendMessage(channel,"#-NEWTURN-" + x+"-*");
                                System.out.println("current turn: " + x);
                            }
                        }
                        System.out.println("sending to all");
                        
                        
                    }
                    if (analyzer[0].equals("*")) {
                        System.out.println("sending to 1");
                        sendMessage(channel, line);
                    }

                    sb.delete(0, sb.length());
                }

            }
        } catch (Exception e) {
            //log.error("exception in run()", e);
        }

    }

    public void sendMessage(SocketChannel channel, String mesg) {
        //ta.append("\n"+mesg);
        prepWriteBuffer(mesg);
        channelWrite(channel, writeBuffer);
    }

    public void sendBroadcastMessage(String mesg, SocketChannel from) {
        //ta.append("\n"+mesg);
        prepWriteBuffer(mesg);
        Iterator i = clients.iterator();
        while (i.hasNext()) {
            SocketChannel channel = (SocketChannel) i.next();
            if (channel != from) {
                channelWrite(channel, writeBuffer);
            }
        }
    }

    public void prepWriteBuffer(String mesg) {
        // fills the buffer from the given string
        // and prepares it for a channel write
        writeBuffer.clear();
        writeBuffer.put(mesg.getBytes());
        //writeBuffer.putChar('\n');
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
        }

        // get ready for another write if needed
        writeBuffer.rewind();
    }
    
}
