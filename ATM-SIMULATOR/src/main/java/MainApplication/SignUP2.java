package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;

public class SignUP2 extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4, l5;
//    JTextField tf1;
    JRadioButton r1, r2;
    JButton b1, b2;

    Random rand = new Random();
    int _PIN = Math.abs((rand.nextInt()%9000)+1000);
    long _cardNo = Math.abs((rand.nextInt()%9000)+1000);
    String PIN = String.valueOf(_PIN);
    String cardNo = String.valueOf(_cardNo);
    String formNo;

    SignUP2(String formNo){
        super("Final Details");
        this.formNo = formNo;

        l1 = new JLabel("Account Details");
        l2 = new JLabel("Form No: "+formNo);
        l3 = new JLabel("Account Type: ");
        l4 = new JLabel("Card No. :"+ cardNo);
        l5 = new JLabel("PIN :"+ PIN);

//        tf1 = new JTextField();

        r1 = new JRadioButton("Savings");
        r2 = new JRadioButton("Current");

        b1 = new JButton("Submit");
        b2 = new JButton("Cancel");

        setLayout(new GridLayout(10,10,5,5));

        add(l1); add(l2); add(l3); add(r1); add(r2); add(l4); add(l5);
//        add(tf1);
        add(b1); add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae){
        String acType = null;
        if(r1.isSelected()){
            acType = "Savings";
        }else if(r2.isSelected()){
            acType = "Current";
        }

        try{
            if(ae.getSource()==b1){
                Date date = new Date();
                DatabaseConnection conn = new DatabaseConnection();
                String q = "select * from signup where formno ='"+formNo+"';";
                ResultSet rs = conn.s.executeQuery(q);
                rs.next();
                String name = rs.getString("name");
                String q1 = "insert into signup3 values('"+cardNo+"','"+acType+"','"+formNo+"','"+PIN+"','"+name+"');";
                String q2 = "insert into login values('"+formNo+"','"+cardNo+"','"+PIN+"');";
                String q3 = "insert into bank values('"+PIN+"','"+date+"','"+"0"+"');";

                conn.s.executeUpdate(q1);
                conn.s.executeUpdate(q2);
                conn.s.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "Note down: \nCard No = "+cardNo+"\tPIN =" + PIN);
                new Deposit(PIN).setVisible(true);
                setVisible(false);
            }
            else if(ae.getSource()==b2){
                System.exit(0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SignUP2("").setVisible(true);
    }
}
