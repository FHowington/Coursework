import java.math.BigInteger;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SecureChatClient extends JFrame implements Runnable, ActionListener {

    public static final int PORT = 8765;

    ObjectInputStream myReader;
    ObjectOutputStream myWriter;
    JTextArea outputArea;
    JLabel prompt;
    JTextField inputField;
    String myName, serverName;
    Socket connection;
    SymCipher cipher;

    public SecureChatClient() {
        try {
            myName = JOptionPane.showInputDialog(this, "Enter your user name: ");
            serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
            InetAddress addr = InetAddress.getByName(serverName);
            connection = new Socket(addr, PORT);   // Connect to server with new
            // Socket

            // Establishes output and flushes
            myWriter = new ObjectOutputStream(connection.getOutputStream());
            myWriter.flush();

            // Establishes input stream
            myReader = new ObjectInputStream(connection.getInputStream());

            // Read in E and E, which are the first two output of Server
            BigInteger E = (BigInteger) myReader.readObject();
            System.out.println("Received E: " + E);
            BigInteger N = (BigInteger) myReader.readObject();
            System.out.println("Received N: " + N);

            // Reads type of cypher to be used, which is next output
            String cyphertype = (String) myReader.readObject();

            // Creates cipher of correct type
            if (cyphertype.equals("Add")) {
                System.out.println("Chosen Cipher is Add128");
                cipher = new Add128();
            } else {
                System.out.println("Chosen Cipher is Substitute");
                cipher = new Substitute();
            }

            BigInteger key = new BigInteger(1, cipher.getKey());
            key = key.modPow(E, N);
            System.out.println("Symmetric key is: " + key);
            myWriter.writeObject(key);
            myWriter.flush();

            // Send name to Server.  Server will need this to announce sign-on and sign-off
            // of clients
            myWriter.writeObject(cipher.encode(myName));
            myWriter.flush();

            // Set title to identify chatter
            this.setTitle(myName);

            Box b = Box.createHorizontalBox();
            outputArea = new JTextArea(8, 30);
            outputArea.setEditable(false);
            b.add(new JScrollPane(outputArea));

            outputArea.append("Welcome to the Chat Group, " + myName + "\n");

            // This is where user will type input
            inputField = new JTextField("");
            inputField.addActionListener(this);

            prompt = new JLabel("Type your messages below:");
            Container c = getContentPane();

            c.add(b, BorderLayout.NORTH);
            c.add(prompt, BorderLayout.CENTER);
            c.add(inputField, BorderLayout.SOUTH);

            // Thread to receive input from the server
            Thread outputThread = new Thread(this);
            outputThread.start();

            addWindowListener(
                    new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            try {
                                myWriter.writeObject(cipher.encode("CLIENT CLOSING"));
                                myWriter.flush();
                                // Close connection for a smooth client exit
                                connection.close();
                            } catch (IOException ex) {
                                System.out.println(ex + ", closing client!");
                            }
                        }
                    }
            );

            setSize(500, 200);
            setVisible(true);

        } catch (Exception e) {
            System.out.println("Problem starting client!");
        }
    }

    public void run() {
        while (true) {
            try {
                byte[] currMsg = (byte[]) myReader.readObject();
                System.out.println("Received bytes:" + Arrays.toString(currMsg));
                // Decodes input message and adds it to the user window
                String msgDecoded = cipher.decode(currMsg);
                System.out.print("Decoded bytes: [");
                for (int i = 0; i < msgDecoded.length() - 1; i++) {
                    System.out.print((int) msgDecoded.charAt(i) + ", ");
                }
                System.out.println((int) msgDecoded.charAt(msgDecoded.length() - 1) + "]");
                System.out.println("String result: " + msgDecoded);
                outputArea.append(msgDecoded + "\n");
            } catch (Exception e) {
                if (connection.isClosed()) {
                    System.out.println("Session terminated.");
                    System.exit(0);
                }
                System.out.println(e + ", closing client!");
                break;
            }
        }
        System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {
        String currMsg = e.getActionCommand();      // Get input value
        inputField.setText("");
        try {
            System.out.println("Sending message: " + myName + ":" + currMsg);
            System.out.println("Message as bytes: " + Arrays.toString((myName + ":" + currMsg).getBytes()));
            System.out.println("Encrypted message: " + Arrays.toString(cipher.encode(myName + ":" + currMsg)));

            // Encodes the used input using the symmetric cypher, appends the username, and sends it to the server
            myWriter.writeObject(cipher.encode(myName + ":" + currMsg));
            myWriter.flush();
        } catch (IOException ex) {
            System.out.println(ex + ", closing client!");
        }
    }

    public static void main(String[] args) {
        SecureChatClient JR = new SecureChatClient();
        JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}


