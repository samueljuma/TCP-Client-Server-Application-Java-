
package entities;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class TCPServer {
    
    //variables
    private static int serverPort;
    private static ArrayList<Student> studentList;
    private static ArrayList<LogEntry> logList;
    private String studentFileName;
    private String logFileName;
    
    //main method
    public static void main(String[] args) {
        serverPort=5000;
        Socket server;
        DataInputStream in;
        DataOutputStream out;
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started");
            System.out.println("Waiting for a client...");
            server = serverSocket.accept();
            System.out.println("Client accepted");
            
            //take input from client
            in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
           
            String msg="";
            while (!msg.equals("over")) {   
                msg = in.readUTF();
            System.out.println("Client says "+msg);
                
            }
            
            //send output to socket
            out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to "+server.getLocalSocketAddress());
            
            
            //close connection
//            in.close();
            server.close();
            
            System.out.println("server closed");
            
        } catch (IOException ex) {
            
        }
        
    }
}
