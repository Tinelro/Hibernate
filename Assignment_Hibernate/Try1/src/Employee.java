import javax.swing.*;
import java.io.Serializable;

public class Employee extends JFrame implements Serializable {

     private String firstName;
     private int age;
     private String address;
     private int salary;
     private int id;

    public Employee() {}

    public Employee(String firstName, int age, String address, int salary) {
        this.firstName = firstName;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public Employee(String firstName, int age, String address, int salary, int id) {
        this.firstName = firstName;
        this.age = age;
        this.address = address;
        this.salary = salary;
        this.id = id;
    }

    public String getFirstName() {return firstName;}
    public int getAge() {
        return age;
    }
    public String getAddress() {
        return address;
    }
    public int getSalary() {
        return salary;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setAge(int age) {this.age = age;}
    public void setAddress(String address) {this.address = address;}
    public void setSalary(int salary) {this.salary = salary;}

    @Override
    public String toString() {
        return getId() + "\t" +
                getFirstName() + "\t" +
                getAge() + "\t" +
                getAddress() + "\t" +
                getSalary();
    }
}

