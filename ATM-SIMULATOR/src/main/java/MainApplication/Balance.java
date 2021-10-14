package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Balance extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5;
    JButton b1, b2;
    String pin="", date="", balance="", cardNO="", name="", acType="";
    public Balance(String pin){
        super("Balance Inquiry");
        this.pin = pin;
        try{
            DatabaseConnection conn = new DatabaseConnection();
            String q1 = "select * from bank where pin = '"+pin+"';";
            ResultSet rs1 = conn.s.executeQuery(q1);
            if(rs1.next()) {
                date = rs1.getString("date");
                balance = rs1.getString("amount");

            }
            String q2 = "select * from signup3 where pin = '"+pin+"';";
            ResultSet rs2 = conn.s.executeQuery(q2);

            if(rs2.next()) {
                cardNO = rs2.getString("cardno");
                name = rs2.getString("name");
                acType = rs2.getString("atype");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        l1 = new JLabel("Date: "+date);
        l2 = new JLabel("Card NO: "+cardNO);
        l3 = new JLabel("Name: "+name);
        l4 = new JLabel("Account Type: "+acType);
        l5 = new JLabel("Balance: "+balance);

        b1 = new JButton("Back");
        b2 = new JButton("Exit");

        setLayout(new GridLayout(10,10,5,5));


        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);
        add(b1);
        add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            setVisible(false);
            new Transaction(pin).setVisible(true);
        }
        else if(ae.getSource()==b2){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Balance("").setVisible(true);
    }
}
