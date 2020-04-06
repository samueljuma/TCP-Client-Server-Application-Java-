
package entities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    
    private static int portNumber;
    private static final String SERVERNAME="localhost";
     
    static DataInputStream input= null;
     static DataOutputStream out;
    // main method
    public static void main(String[] args) {
        portNumber = 5000;
       
        try {
            Socket client = new Socket(SERVERNAME,portNumber);
            System.out.println("connected");
            
            //take input from terminal
            input = new DataInputStream(System.in);
            
            //send output to socket
            out = new DataOutputStream(client.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("No connection");
        }
        
        try {
            String msg="";
            while (!msg.equals("Over")) { 
             msg=input.readLine();
            out.writeUTF(msg); 
            }
            
        } catch (IOException e) {
        }
        //close connecion
        try {
            input.close();
            out.close();
           
            
        } catch (IOException ex) {
        }
        
        
        
        
    }
    
}
