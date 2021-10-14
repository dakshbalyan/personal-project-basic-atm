package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transaction extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6;
    String pin, cardNo;

    public Transaction(String pin){
        super("Transaction Window");
        this.pin = pin;

        l1 = new JLabel("Select desired Transaction");
        b1 = new JButton("Deposit");
        b2 = new JButton("Withdrawl");
        b3 = new JButton("PIN Change");
        b4 = new JButton("Balance Enquiry");
        b5 = new JButton("Delete Account");
        b6 = new JButton("Exit");

        setLayout(new GridLayout(10,10,5,5));


        add(l1); add(b1); add(b2); add(b3); add(b4); add(b5); add(b6);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try{
            if (ae.getSource() == b1) {
                setVisible(false);
                new Deposit(pin).setVisible(true);
            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Withdrawl(pin).setVisible(true);
            } else if (ae.getSource() == b3) {
                setVisible(false);
                new PIN(pin).setVisible(true);
            } else if (ae.getSource() == b4) {
                setVisible(false);
                new Balance(pin).setVisible(true);
            }else if (ae.getSource() == b5) {
                setVisible(false);
                new Delete(pin).setVisible(true);
            } else if (ae.getSource() == b6) {
                System.exit(0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Transaction("").setVisible(true);
    }
}
