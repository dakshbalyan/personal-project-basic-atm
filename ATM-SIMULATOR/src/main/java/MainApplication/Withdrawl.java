package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JTextField tf1;
    JButton b1, b2, b3;
    String pin;
    public Withdrawl(String pin) {
        super("Withdrawl Form");
        this.pin = pin;

        l1 = new JLabel("Withdraw the desired amount");
        l2 = new JLabel("PIN: "+pin);
        l3 = new JLabel("Enter the Amount");

        tf1 = new JTextField();

        b1 = new JButton("Withdraw");
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

    public synchronized void withdraw(int amount){
        try{
            Date date = new Date();
            DatabaseConnection conn = new DatabaseConnection();
            String q1 = "select * from bank where pin = '" + pin + "';";

            ResultSet rs = conn.s.executeQuery(q1);

            rs.next();
            int balance = Integer.parseInt(rs.getString("amount"));
            System.out.println(Thread.currentThread().getName()+" starts withdrawl "+amount);
            if (amount <= balance) {
                balance -= amount;
                String q2 = "update bank set date = '" + date + "', amount = '" + String.valueOf(balance) + "' where pin = '" + pin + "';";
                conn.s.executeUpdate(q2);

                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Withdrawn Successfully!");
                setVisible(false);
                new Transaction(pin).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient Balance in account!");
                return;
            }

            System.out.println(Thread.currentThread().getName()+" completes withdrawl "+amount);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void actionPerformed(ActionEvent ae){
            if(ae.getSource()==b1){
                if(tf1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter the amount you want Withdrawn !");
                    return;
                }
                else{
                    final int amount = Integer.parseInt(tf1.getText());

                    Thread t1 = new Thread(){
                      public void run(){
//                          for(int i=0;i<10;i++)
                            withdraw(amount);
                      }
                    };
//                    Thread t2 = new Thread(){
//                        public void run(){
////                            for(int i=0;i<10;i++)
//                                withdraw(amount);
//                        }
//                    };
//                    Thread t3 = new Thread(){
//                        public void run(){
////                            for(int i=0;i<10;i++)
//                            withdraw(amount);
//                        }
//                    };
//                    Thread t4 = new Thread(){
//                        public void run(){
////                            for(int i=0;i<10;i++)
//                            withdraw(amount);
//                        }
//                    };
//                    Thread t5 = new Thread(){
//                        public void run(){
////                            for(int i=0;i<10;i++)
//                            withdraw(amount);
//                        }
//                    };

                    try{
                        t1.start();
//                        t2.start();
//                        t3.start();
//                        t4.start();
//                        t5.start();
//                        t.join();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
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
        new Withdrawl("").setVisible(true);
    }
}
