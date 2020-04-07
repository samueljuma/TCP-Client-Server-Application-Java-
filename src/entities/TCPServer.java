package entities;

import entities.TCPClient.ClientHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    //variables
    private static int serverPort;
    private static ArrayList<Student> studentList;
    private static ArrayList<LogEntry> logList;
    private String studentFileName;
    private String logFileName;
    
    private static final int MAXIMUM_NO_OF_CLIENTS=4;
    
    private static TCPClient tCPClient = new TCPClient();

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(MAXIMUM_NO_OF_CLIENTS);

    //main method
    public static void main(String[] args) throws IOException {
        serverPort = 5000;
        Socket client;
      
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Server started");
        while (true) {
            System.out.println("Waiting for a client...");
            client = serverSocket.accept();
            System.out.println("Client accepted");
            ClientHandler clientThread = tCPClient.new ClientHandler(client);
            
            clients.add(clientThread);
            pool.execute(clientThread);

        }

    }
    public class Connection {
    
    //variables
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<Student> studentList;
    private ArrayList<LogEntry> logList;
    
    public void connection(Socket clientSocket, ArrayList<Student> studentList,ArrayList<LogEntry> logList){
               
    }
    
}
    public class WriteToFile {
    
    //variables
    private PrintWriter prn;
    private ArrayList<Student> studentList;
    private ArrayList<LogEntry> logList;
    private String studentFileName;
    private String logFileName;

    public WriteToFile(ArrayList<Student> studentList,ArrayList<LogEntry> logList) {
        this.studentList = studentList;
        this.logList = logList;
    }
    
    
    
}
}
