package entities;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    
    private static final int portNumber =5000;
    private static final String SERVERNAME = "localhost";

    //
    static DataInputStream input = null;
    static DataOutputStream out;

    // main method
    public static void main(String[] args) {
        
        //connect
        try {
            Socket client = new Socket(SERVERNAME, portNumber);
//            System.out.println("connected");

            //take input from terminal
            input = new DataInputStream(System.in);

            //send output to socket
            out = new DataOutputStream(client.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("No connection");
        }
        
        //Menu
        menu();
       
        
        try {
            String studentNumber;
            String pinCode;
            String msg = "";
            while (!msg.equals("3")) {  
                msg = input.readLine();
                switch (msg) {
                    case "1":              
                        out.writeUTF("Student is about to log in");
                        System.out.println("Enter Student Number: ");
                        studentNumber = input.readLine();
                        System.out.println("Enter 4 digit Pin Code: ");
                        pinCode = input.readLine();
                        System.out.println("*******"+new Student(studentNumber,Integer.parseInt(pinCode))+ " loged in********\n");
                        menu();
                        break;
                    case "2":                        
                        out.writeUTF("New Student");
                        System.out.println("Enter Student Number: ");
                        studentNumber = input.readLine();
                        System.out.println("Enter 4 digit Pin Code: ");
                        pinCode = input.readLine();
                        System.out.println("*******"+new Student(studentNumber,Integer.parseInt(pinCode))+" exists**********\n");
                        menu();
                        break;
                    case "3":
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try Again: ");
                        break;
                }
                               
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
    
    public static void menu(){
                System.out.println("PLEASE MAKE YOUR SELECTION"
                + "\n**************************"
                + "\n1. Current Student"
                + "\n2. New Student"
                + "\n3. Exit"
                + "\nEnter your option: ");
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
                    System.out.println(msg);
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
