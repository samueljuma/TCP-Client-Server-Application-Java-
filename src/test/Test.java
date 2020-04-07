
package test;

import entities.Student;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Test {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        // write object info in file
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./src/data/Studententry.bin"));
        Student student = new Student("S0001", 0);
        objectOutputStream.writeObject(student);
        objectOutputStream.close();
        
        // 
        
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./src/data/Studententry.bin"));
        
        Student studentRead = (Student) objectInputStream.readObject();
        
        objectInputStream.close();
        System.out.println(studentRead);

    }
    
}
