
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.*;
import java.nio.charset.*;
import java.nio.channels.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ChatterClient.java
 *
 * A basic example of a multi-user chat application using the JDK 1.4 NIO libraries
 * 
 * @author <a href="mailto:bret@hypefiend.com">bret barker</a>
 * @version 1.0
 */
public class ChatterClient extends Thread {

    private static final int BUFFER_SIZE = 255;
    private static final long CHANNEL_WRITE_SLEEP = 10L;
    private static final int PORT = 10997;
    private ByteBuffer writeBuffer;
    private ByteBuffer readBuffer;
    private boolean running;
    private SocketChannel channel;
    private String host;
    private Selector readSelector;
    private CharsetDecoder asciiDecoder;
    private InputThread it;
    public JPanel chat;
    public JTextArea log;
    public JTextField type;
    public JButton send;
    public String name;
    public MultiplayerBoard board;
    JFrame f;

    public static void main(String args[]) {
        String host = "192.168.0.102";
        ChatterClient cc = new ChatterClient(host, "abdul");
        cc.start();
    }

    public ChatterClient(String host, String player) {
        this.name = player;
        this.host = host;
        writeBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        asciiDecoder = Charset.forName("US-ASCII").newDecoder();

        f = new JFrame();
        chat = new JPanel();
        log = new JTextArea();
        type = new JTextField();
        send = new JButton("Send");

//        f.add(chat);
//        f.setVisible(true);
//        f.setSize(500, 500);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setClient(MultiplayerBoard board) {
        this.board = board;
    }

    public JPanel ChatPanel() {
        chat.setLayout(null);
        chat.setPreferredSize(new Dimension(350, 350));
        chat.add(log);
        chat.add(type);
        chat.add(send);
        log.setBounds(0, 0, 350, 200);
        type.setBounds(0, 210, 250, 50);
        send.setBounds(260, 210, 90, 50);

        log.setEditable(false);
        log.setLineWrap(true);
        log.setBackground(Color.LIGHT_GRAY);
        log.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        type.setBackground(Color.LIGHT_GRAY);
        type.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        send.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sendMessage("#-MESSAGECHAT-" + name + "-" + type.getText().trim() + "-*");
                log.append(name + " >>> " + type.getText().trim() + "\n");
            }
        });


        return chat;
    }

    public void run() {
        connect(host);
        it = new InputThread(this);
        it.start();

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

    private void connect(String hostname) {
        try {
            readSelector = Selector.open();
            InetAddress addr = InetAddress.getByName(hostname);
            channel = SocketChannel.open(new InetSocketAddress(addr, PORT));
            channel.configureBlocking(false);
            channel.register(readSelector, SelectionKey.OP_READ, new StringBuffer());
        } catch (UnknownHostException uhe) {
        } catch (ConnectException ce) {
        } catch (Exception e) {
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
                    System.out.println("disconnected from server: end-of-stream");
                    channel.close();
                    shutdown();
                    it.shutdown();
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
                    String analyzer[] = line.split("-");
                    //if ((line.indexOf("\n") != -1) || (line.indexOf("\r") != -1)) {
                    if (line.startsWith("#-MESSAGECHAT")) {
                        String namer = analyzer[2];
                        String mess = analyzer[3];
                        board.log.append(namer + " >>> " + mess + "\n");
                        log.append(namer + " >>> " + mess + "\n");
                        System.out.println("YES!!!");
                    }
                    if (line.startsWith("#-UPDATEPLAYERDETAILS")) {
                        board.updatePlayerDetails2();
                    }
                    if (line.startsWith("#-MESSAGE")) {
                        board.updateMessage2(analyzer[2]);
                    }
                    sb.delete(0, sb.length());
                    System.out.print("\n==========================  " + line);
                    System.out.print("> ");
                //}
                }
            }
        } catch (IOException ioe) {
        } catch (Exception e) {
        }
    }

    public void sendMessage(String mesg) {
        prepWriteBuffer(mesg);
        channelWrite(channel, writeBuffer);
    }

    private void prepWriteBuffer(String mesg) {
        // fills the buffer from the given string
        // and prepares it for a channel write
        writeBuffer.clear();
        writeBuffer.put(mesg.getBytes());
        writeBuffer.putChar('\n');
        writeBuffer.flip();
    }

    private void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
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
        } catch (ClosedChannelException cce) {
        } catch (Exception e) {
        }

        // get ready for another write if needed
        writeBuffer.rewind();
    }

    public void shutdown() {
        running = false;
        interrupt();
    }

    /** 
     * InputThread reads user input from STDIN
     */
    class InputThread extends Thread {

        private ChatterClient cc;
        private boolean running;

        public InputThread(ChatterClient cc) {
            this.cc = cc;
        }

        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            running = true;
            while (running) {
                try {
                    String s;
                    System.out.print("> ");
                    System.out.flush();
                    s = br.readLine();
                    if (s.length() > 0) {
                        cc.sendMessage(s + "\n");
                    }
                    if (s.equals("quit")) {
                        running = false;
                    }
                } catch (IOException ioe) {
                    running = false;
                }
            }
            cc.shutdown();
        }

        public void shutdown() {
            running = false;
            interrupt();
        }
    }
}
