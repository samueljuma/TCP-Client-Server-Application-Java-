
package entities;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection {
    
    //variables
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<Student> studentList;
    private ArrayList<LogEntry> logList;
    
    public void connection(Socket clientSocket, ArrayList<Student> studentList,ArrayList<LogEntry> logList){
        
    }
    
}
