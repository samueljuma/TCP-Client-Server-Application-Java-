
package entities;

import java.io.PrintWriter;
import java.util.ArrayList;

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
