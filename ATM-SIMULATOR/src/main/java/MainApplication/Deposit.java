package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class Deposit extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JTextField tf1;
    JButton b1, b2, b3;
    String pin;
    public Deposit(String pin) {
        super("Deposit Form");
        this.pin = pin;

        l1 = new JLabel("Deposit the desired amount");
        l2 = new JLabel("PIN: "+pin);
        l3 = new JLabel("Enter the Amount");

        tf1 = new JTextField();

        b1 = new JButton("Deposit");
        b2 = new JButton("Back");
        b3 = new JButton("Exit");

        setLayout(new GridLayout(5,5,5,5));

        add(l1); add(l2); add(l3); add(tf1);
        add(b1); add(b2); add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);
    }

    public synchronized void deposit(int amount){
        Date date = new Date();
        try{
            DatabaseConnection conn = new DatabaseConnection();
            String q1 = "select * from bank where pin = '" + pin + "';";

            ResultSet rs = conn.s.executeQuery(q1);
            int totalAmnt = 0;
            if (rs.next()) {
                totalAmnt = amount + Integer.parseInt(rs.getString("amount"));
            }
            String q2 = "update bank set date = '" + date + "', amount = '" + String.valueOf(totalAmnt) + "' where pin = '" + pin + "';";
            conn.s.executeUpdate(q2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            if(tf1.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Enter the amount you want deposited !");
                return;
            }
            else{
                int amount = Integer.parseInt(tf1.getText());
                Thread t = new Thread(){
                    public void run(){
                            deposit(amount);

                    }
                };

                try{
                    t.start();
                    t.join();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Deposited Successfully!");
                setVisible(false);
                new Transaction(pin).setVisible(true);
            }
        }
        else if(ae.getSource()==b2){
            setVisible(false);
            new Transaction(pin).setVisible(true);
        }
        else if(ae.getSource()==b3){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
