package entities;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {
    
    private static int portNumber;
    private static final String SERVERNAME = "localhost";

    //
    static DataInputStream input = null;
    static DataOutputStream out;

    // main method
    public static void main(String[] args) {
        portNumber = 5000;
        
        try {
            Socket client = new Socket(SERVERNAME, portNumber);
            System.out.println("connected");

            //take input from terminal
            input = new DataInputStream(System.in);

            //send output to socket
            out = new DataOutputStream(client.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("No connection");
        }
        
        try {
            String msg = "";
            while (!msg.equals("Over")) {                
                msg = input.readLine();
                out.writeUTF(msg);                
            }
            
        } catch (IOException e) {
        } //close connecion
        finally {
            try {
                input.close();
                out.close();
                
            } catch (IOException ex) {
            }
            
        }
        
    }
    
    public class ClientHandler implements Runnable {
        
        private Socket client;
        private DataInputStream in;
        private DataOutputStream out;

        //constructor       
        public ClientHandler(Socket clientSocket) throws IOException {
            this.client = clientSocket;
            
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            out = new DataOutputStream(client.getOutputStream());
        }
        
        @Override
        public void run() {
            try {
                String msg = "";
                while (!msg.equals("Over")) {                    
                    msg = in.readUTF();
                    System.out.println("Client says " + msg);
                    out.writeUTF("Thank you for connecting to " + client.getLocalSocketAddress());
                    
                }
            } catch (IOException e) {
                try {
                    client.close();
                } catch (IOException ex) {
                    System.err.println("Error");
                }
            } finally {
                try {
                    in.close();
                    out.close();
                    client.close();
                } catch (IOException ex) {
                }
                
            }
            
        }
    }
    
}
