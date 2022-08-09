import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class GUI extends JFrame {
    private JComboBox editComboBox;
    private JComboBox readComboBox;
    private JPanel mainPanel;
    private JTextArea jta;
    private JButton EXITButton;
    private Transaction tx;
    private Session session;

    private void quit() {
        System.out.println("Bye!");
        session.close();
        HibernateUtil.close();
        System.exit(0);
    }

    public GUI() {
        this.setContentPane(mainPanel);

        EXITButton.addActionListener((event) -> quit());

        final PrintStream sysOut = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                jta.append(String.valueOf((char) b));
                sysOut.write(b);
            }
        }));

        try {
            session = HibernateUtil.createSessionFactory().openSession();

            editComboBox.addActionListener(e -> {

                int editChoice = editComboBox.getSelectedIndex();
                int id = 0;
                try {
                    switch (editChoice) {

                        case 1:
                            String firstName = JOptionPane.showInputDialog("First Name:");
                            int age = Integer.parseInt(JOptionPane.showInputDialog("Age:"));
                            String address = JOptionPane.showInputDialog("Address:");
                            int salary = Integer.parseInt(JOptionPane.showInputDialog("Salary:"));
                            Employee employee = new Employee(firstName, age, address, salary);

                            tx = session.beginTransaction();
                            session.persist(employee);
                            tx.commit();

                            System.out.println("\nNew entry added: " + firstName + "\n");
                            break;

                        case 2:
                            id = Integer.parseInt(JOptionPane.showInputDialog("ID:"));

                            Employee retrievedEmployee = session.get(Employee.class, id);

                            firstName = JOptionPane.showInputDialog("First Name:");
                            if (!firstName.isEmpty()) {
                                retrievedEmployee.setFirstName(firstName);
                            }

                            String strAge = JOptionPane.showInputDialog("Age:");
                            if (!strAge.isEmpty() && Integer.parseInt(strAge) > 0) {
                                retrievedEmployee.setAge(Integer.parseInt(strAge));
                            }

                            address = JOptionPane.showInputDialog("Address:");
                            if (!address.isEmpty()) {
                                retrievedEmployee.setAddress(address);
                            }

                            String strSalary = JOptionPane.showInputDialog("Salary:");
                            if (!strSalary.isEmpty() && Integer.parseInt(strSalary) > 0) {
                                retrievedEmployee.setSalary(Integer.parseInt(strSalary));
                            }

                            tx = session.beginTransaction();
                            session.update(retrievedEmployee);
                            tx.commit();

                            System.out.println("\n Updated entry: " + id);
                            break;

                        case 3:
                            String st = JOptionPane.showInputDialog("Choose ID of entry to delete");
                            id = Integer.parseInt(st);

                            retrievedEmployee = session.get(Employee.class, id);

                            tx = session.beginTransaction();
                            session.delete(retrievedEmployee);
                            tx.commit();

                            System.out.println("\n id: " + id + " was deleted\n");
                            break;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("\n NumberFormatException occurred");
                } catch (NullPointerException npe) {
                    System.out.println("\n id: " + id + " doesn't exist");
                }catch(IllegalArgumentException iae) {
                    System.out.println("\n id: " + id + " doesn't exist");
                    tx.rollback();
                }
            });

            readComboBox.addActionListener(e1 -> {
                int readChoice = readComboBox.getSelectedIndex();

                try {
                    switch (readChoice) {
                        case 1:
                            String hql;
                            Query query;
                            List<Employee> employeeList;

                            hql = "from Employee ";
                            query = session.createQuery(hql);

                            employeeList = query.list();

                            System.out.println("\n    List of all employees: \n");

                            for (Employee emp : employeeList) {
                                System.out.println(emp);
                            }
                            break;

                        case 2:
                            String fnSearch = JOptionPane.showInputDialog(
                                    null, "Type first name to search", "Filter by first name");

                            hql = " from Employee  where firstName LIKE " + "'%" + fnSearch + "%'";

                            query = session.createQuery(hql);

                            employeeList = query.list();

                            System.out.println("\n   Filtered by first name (like) '" + fnSearch + "' :\n");

                            for (Employee emp : employeeList) {
                                System.out.println(emp);
                            }
                            break;

                        case 3:
                            int ageSearch = Integer.parseInt((JOptionPane.showInputDialog(
                                    null, "Type age to search:", "Filter by age")));

                            hql = " from Employee  where age < " + (ageSearch + 5) + " AND age > " + (ageSearch - 5);

                            query = session.createQuery(hql);

                            employeeList = query.list();

                            System.out.println("\n   Filtered by age " + ageSearch + " (+5, -5) :\n");

                            for (Employee emp : employeeList) {
                                System.out.println(emp);
                            }
                            break;

                        case 4:
                            String adSearch = (JOptionPane.showInputDialog(
                                    null, "Type address to search", "Filter by address"));

                            hql = " from Employee  where address LIKE " + "'%" + adSearch + "%'";

                            query = session.createQuery(hql);

                            employeeList = query.list();

                            System.out.println("\n   Filtered by address (like) '" + adSearch + "' :\n");

                            for (Employee emp : employeeList) {
                                System.out.println(emp);
                            }
                            break;

                        case 5:
                            int salSearch = Integer.parseInt((JOptionPane.showInputDialog(
                                    null, "Type salary to search", "Filter by salary")));

                            hql = " from Employee  where salary < " + (500 + salSearch) + " AND salary>" + (salSearch - 500);

                            query = session.createQuery(hql);

                            employeeList = query.list();

                            System.out.println("\n   Filtered by salary " + salSearch + " (+500, -500):\n");

                            for (Employee emp : employeeList) {
                                System.out.println(emp);
                            }
                            break;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormatException occurred");
                }
            });

        } catch (HibernateException exc) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Transaction failed");
        }
    }
}


