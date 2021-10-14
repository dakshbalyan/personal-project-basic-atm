package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField cardNO_field;
    JPasswordField uPASS_field;
    JButton signIN_button, clear_button, signUP_button, exit_button;

    Login(){
        super("Online Banking Sim");

        label1 = new JLabel("WELCOME USER");
//        label1.setFont(new Font("Oswald", Font.BOLD, 38));

        label2 = new JLabel("Card No:");
        label3 = new JLabel("PIN:");

        cardNO_field = new JTextField(15);
        uPASS_field = new JPasswordField(15);

        signIN_button = new JButton("SIGN IN");
        clear_button = new JButton("CLEAR FIELDS");
        signUP_button = new JButton("SIGN UP");
        exit_button = new JButton("Exit");

        setLayout(new GridLayout(5,5,5,5));

        add(label1); add(label2); add(cardNO_field);
        add(label3); add(uPASS_field);
        add(signIN_button); add(clear_button); add(signUP_button); add(exit_button);

        signIN_button.addActionListener(this);
        clear_button.addActionListener(this);
        signUP_button.addActionListener(this);
        exit_button.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae){
        try{
            DatabaseConnection conn = new DatabaseConnection();
            String cardNO = cardNO_field.getText();
            String pin = String.valueOf(uPASS_field.getPassword());
            String q = "select * from login where cardno = '"+cardNO+"' and pin = '"+pin+"' ;";
            ResultSet rs = conn.s.executeQuery(q);

            if(ae.getSource()==signIN_button){
                if(rs.next()){
                    new Transaction(pin).setVisible(true);
                    setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or Password");
                }
            }else if(ae.getSource()==clear_button){
                uPASS_field.setText("");
                cardNO_field.setText("");
            }else if(ae.getSource()==signUP_button){
                new SignUP().setVisible(true);
                setVisible(false);
            }else if(ae.getSource()==exit_button){
                System.exit(0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
//            System.out.println("Error : " + e);
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
//        new Login();
    }
}
