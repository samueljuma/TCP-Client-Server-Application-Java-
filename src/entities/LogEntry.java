
package entities;

public class LogEntry {
    
    //variables
    private String studentNumber;
    private int pinCode;
    private String dateTime;

    /*
    *constructors
    */
    public LogEntry() {
    }

    public LogEntry(String studentNumber, String dateTime) {
        this.studentNumber = studentNumber;
        this.dateTime = dateTime;
    }

    public LogEntry(String studentNumber, int pinCode, String dateTime) {
        this.studentNumber = studentNumber;
        this.pinCode = pinCode;
        this.dateTime = dateTime;
    }
    
    /*
    *setters and getters
    */

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }
    
    //to String
    @Override
    public String toString() {
        return studentNumber+" "+dateTime;
    }
    
    
    
    
    
}
