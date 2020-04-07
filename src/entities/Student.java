
package entities;

import java.io.Serializable;

public class Student implements Serializable{
    // variables
    private String studentNumber;
    private int pinCode;
    
    /*
    *constructors
    */
    public Student() {
        
    }
    public Student(String studentNumber, int pinCode) {
        this.studentNumber = studentNumber;
        this.pinCode = pinCode;
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

    // represent student details as String
    @Override
    public String toString() {
        return studentNumber+" "+pinCode;
    }
    
    
}
