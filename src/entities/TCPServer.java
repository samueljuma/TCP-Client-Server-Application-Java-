package entities;

import entities.TCPClient.ClientHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private static final int serverPort = 5000;
    private static ArrayList<Student> studentList;
    private static ArrayList<LogEntry> logList;
    private String studentFileName;
    private String logFileName;
    
    String[] serverResponses ={"******Student exists******"
            + "", "******Un-Registered Student******", "******Invalid Pin******"
            + "", "******Welcome******","******Student Created******"};
    
    static final TCPClient CLIENT = new TCPClient();

    private static final int MAXIMUM_NO_OF_CLIENTS = 4;

    private static TCPClient tCPClient = new TCPClient();

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(MAXIMUM_NO_OF_CLIENTS);

    //main method
    public static void main(String[] args) throws IOException {
        Socket client;
        studentList = TCPClient.STUDENTS_ARRAY_LIST;
        logList = TCPClient.LOG_ENTRY__ARRAY_LIST;
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Server started");
        while (true) {
//            System.out.println("Waiting for a client...");
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
        private ArrayList<Student> mStudentList;
        private ArrayList<LogEntry> mLogList;

        public Connection(ArrayList<Student> mStudentList, ArrayList<LogEntry> mLogList) throws ClassNotFoundException {

            this.mStudentList = mStudentList;
            this.mLogList = mLogList;
            try {
                //studentEntries
                out = new ObjectOutputStream(new FileOutputStream("./src/data/Studententry.bin"));
                for (int i = 0; i < mStudentList.size(); i++) {
                    out.writeObject(mStudentList.get(i));
                }

                out.close();

                //
                in = new ObjectInputStream(new FileInputStream("./src/data/Studententry.bin"));
                    Student student;
                for (Student mStudentList1 : this.mStudentList) {
                    student= (Student) in.readObject();
                }
                     
//                    System.out.println(student);
                
                in.close();
                
                // logEntries
                out = new ObjectOutputStream(new FileOutputStream("./src/data/Logentry.bin"));
                for (int i = 0; i < mLogList.size(); i++) {
                    out.writeObject(mLogList.get(i));
                }

                out.close();

                //
                in = new ObjectInputStream(new FileInputStream("./src/data/Logentry.bin"));
                for (LogEntry logEntryList : mLogList) {
                    LogEntry logEntry = (LogEntry) in.readObject();
//                    System.out.println(logEntry);
                }
                in.close();
            } catch (IOException ex) {
                System.err.println("Error in connection inner class");
            }

        }

        public ArrayList<LogEntry> getmLogList() {
            return mLogList;
        }

        public ArrayList<Student> getmStudentList() {
            return mStudentList;
        }
        

    }

    public class WriteToFile {

        //variables
        private PrintWriter prn;
        private ArrayList<Student> studentList;
        private ArrayList<LogEntry> logList;
        private static final String studentFileName = "Studententry.txt";
        private static final String logFileName = "Logentry.txt";

        public WriteToFile(ArrayList<Student> studentList, ArrayList<LogEntry> logList) throws FileNotFoundException {
            this.studentList = studentList;
            this.logList = logList;

            prn = new PrintWriter("./src/data/"+studentFileName);
            for (int i = 0; i < studentList.size(); i++) {
                prn.println(studentList.get(i).toString());
            }
            prn.flush();
            prn.close();
            
            prn= new PrintWriter("./src/data/"+logFileName);
            for (int i = 0; i < logList.size(); i++) {
                prn.println(logList.get(i).toString());
            }
            prn.flush();
            prn.close();

        }

    }
    public boolean isContained(ArrayList<Student> students, Student student){
        for(int i=0;i<students.size();i++){
            if(student.getStudentNumber().equals(students.get(i).getStudentNumber()) && student.getPinCode()==students.get(i).getPinCode())
                return true;
            }
        
        return false;
    }
    public boolean isPincodeValid(String pincode){
        try {
            Integer.parseInt(pincode);
            if(pincode.length()!= 4){
            return false;
        }            
        } catch (NumberFormatException e) {
            return false;
        }        
        return true;
    }
}
