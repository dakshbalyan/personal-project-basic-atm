package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SignUP extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9,
    l10, l11, l12;
    JTextField tf1, tf2, tf3, tf4, tf5;
    JRadioButton r1, r2, r3;
    JButton b, exit_button;
    JComboBox c1, c2, c3;

    Random rand = new Random();
    int formNo = Math.abs((rand.nextInt()%9000)+1000);
    String formNO = String.valueOf(formNo);
    int num = Integer.parseInt(formNO);

    SignUP(){
        super("SignUP Application Form");
        System.out.println(num);
        l1 = new JLabel("Application Form NO."+formNO);
        l2 = new JLabel("Enter Personal Details");
        l3 = new JLabel("Name:");
        l4 = new JLabel("Father's Name:");
        l5 = new JLabel("Date of Birth:");
        l6 = new JLabel("Gender:");
        l7 = new JLabel("Email:");
        l8 = new JLabel("City:");
        l9 = new JLabel("State:");
        l10 = new JLabel("Date");
        l11 = new JLabel("Month");
        l12 = new JLabel("Year");

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();

        b = new JButton("Continue");
        exit_button = new JButton("Exit");

        r1 = new JRadioButton("Male");
        r2 = new JRadioButton("Female");
        r3 = new JRadioButton("Other");

        String[] date = {"1","2","3" };
        c1 = new JComboBox(date);

        String[] month = {"January", "February", "March"};
        c2 = new JComboBox(month);

        String[] year = {"1995", "1996", "1997", "1998"};
        c3 = new JComboBox(year);

        setLayout(new GridLayout(10,10,5,5));

        add(l1); add(l2); add(l3); add(tf1); add(l4); add(tf2);
        add(l5); add(l10); add(c1); add(l11); add(c2); add(l12); add(c3); // date of birth
        add(l6); add(r1); add(r2); add(r3); // gender
        add(l7); add(tf3); add(l8); add(tf4); add(l9); add(tf5); //details

        add(b); add(exit_button);


        b.addActionListener(this);
        exit_button.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);

    }

    public  void actionPerformed(ActionEvent ae){
        String name = tf1.getText();
        String fname = tf2.getText();

        String _date = (String)c1.getSelectedItem();
        String _month = (String)c2.getSelectedItem();
        String _year = (String)c3.getSelectedItem();

        String _gender = "";
        if(r1.isSelected()){
            _gender = "Male";
        }else if(r2.isSelected()){
            _gender = "Female";
        }else if(r3.isSelected()){
            _gender = "Other";
        }

        String email = tf3.getText();
        String city = tf4.getText();
        String state = tf5.getText();

        try{
            if(ae.getSource()==b){
                if(name.equals("") || fname.equals("") || _gender.equals("")
                        || email.equals("") || city.equals("") || state.equals("")){
                    JOptionPane.showMessageDialog(null, "Fill All fields");
                    return;
                }
                DatabaseConnection conn = new DatabaseConnection();
                String q = "insert into signup values ('"+formNO+"','"+name+"','"+fname+"','"+_date+"','"+_month+"','"+_year+"','"+_gender+"','"+email+"','"+city+"','"+state+"');";
                conn.s.executeUpdate(q);

                new SignUP2(formNO).setVisible(true);
                setVisible(false);
            }else if(ae.getSource()==exit_button){
                System.exit(0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SignUP().setVisible(true);
    }

}
