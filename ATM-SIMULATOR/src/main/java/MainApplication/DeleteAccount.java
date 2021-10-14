package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class DeleteAccount extends JFrame implements ActionListener {
    JLabel l1;
    JButton b1, b2;
    DatabaseConnection conn = new DatabaseConnection();
    String pin = "", formno = "", cardno = "";
    public DeleteAccount(String pin){
        super("CAUTION !!!");
        this.pin = pin;

        l1 = new JLabel("Are you sure you want to Delete Account ?");
        b1 = new JButton("Yes");
        b2 = new JButton("No");

        setLayout(new FlowLayout(FlowLayout.CENTER, 5 , 5));

        add(l1); add(b1); add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(350, 130);
        setLocation(750,400);
        setVisible(true);

    }
    private void delete(){
        try{
            String q1 = "select * from login where pin = '" + pin + "';";
            ResultSet rs1 = conn.s.executeQuery(q1);
            if (rs1.next()) {
                formno = rs1.getString("formno");
                cardno = rs1.getString("cardno");
            }
            String q2 = "delete from bank where pin = '" + pin + "' ";
            String q3 = "delete from signup3 where cardno = '" + cardno + "' ";
            String q4 = "delete from login where pin = '" + pin + "' ";
            String q5 = "delete from signup where formno = '" + formno + "' ";

            conn.s.executeUpdate(q2);
            conn.s.executeUpdate(q4);
            conn.s.executeUpdate(q3);
            conn.s.executeUpdate(q5);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == b1){
            delete();
            JOptionPane.showMessageDialog(null, "Account Deleted Successfully !");
            setVisible(false);
            new Login().setVisible(true);
        }else if(ae.getSource() == b2){
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new DeleteAccount("").setVisible(true);
    }
}
