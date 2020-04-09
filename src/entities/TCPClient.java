package entities;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

public class TCPClient {

    private static final int portNumber = 5000;
    private static final String SERVERNAME = "localhost";
    
    static final ArrayList<Student> STUDENTS_ARRAY_LIST = new ArrayList<>();
    
    static final ArrayList<LogEntry> LOG_ENTRY__ARRAY_LIST = new ArrayList<>();
    
    
    static TCPServer server = new TCPServer();

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

        try {
            
            out.writeUTF("Hello");
            //Menu
            menu();
            
            //date format
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
            Date timeNow;
            String studentNumber;
            String pinCode;
            Student student;

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
                        student = new Student(studentNumber, Integer.parseInt(pinCode));
                        
                        if (!server.isPincodeValid(pinCode)) {
                            //invalid pin
                            System.out.println(server.serverResponses[2]);
                        }                        
                        else if(server.isContained(STUDENTS_ARRAY_LIST, student)){
                            timeNow =new Date();
                            String date = formatter.format(timeNow);
                            
                            //allow login
                            LogEntry logEntry =new LogEntry(studentNumber,date);
                            LOG_ENTRY__ARRAY_LIST.add(logEntry);
                            System.out.println(server.serverResponses[3]);
                        }else{
                            //unregistered
                            System.out.println(server.serverResponses[1]);
                        }
                        menu();
                        break;
                    case "2":
                        out.writeUTF("New Student");
                        System.out.println("Enter Student Number: ");
                        studentNumber = input.readLine();
                        System.out.println("Enter 4 digit Pin Code: ");
                        pinCode = input.readLine();

                        student = new Student(studentNumber, Integer.parseInt(pinCode));
                        if (!server.isPincodeValid(pinCode)) {
                            //invalid pin
                            System.out.println(server.serverResponses[2]);
                        }                        
                        else if(server.isContained(STUDENTS_ARRAY_LIST, student)){
                            //student exists
                            System.out.println(server.serverResponses[0]);
                        }else{
                            //create Student   
                            STUDENTS_ARRAY_LIST.add(student);
                            System.out.println(server.serverResponses[4]);
                        }
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
        try {
            TCPServer.WriteToFile writeToFile = new TCPServer().new WriteToFile(STUDENTS_ARRAY_LIST, LOG_ENTRY__ARRAY_LIST);
        } catch (FileNotFoundException ex) {
            
        }

    }
    
    

    public static void menu() {
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
                 String request="";
                while (!request.contains("hello")) {
                    request = in.readUTF();
                   
                }
                out.writeUTF("Thank you for connecting to " + client.getLocalSocketAddress());
                
            } catch (IOException e) {
//                System.err.println("Error in Client handler:"+e);
            } finally {
                try {
                    out.close();
                    in.close();
                } catch (IOException e) {

                }

            }
        }
    }

}
