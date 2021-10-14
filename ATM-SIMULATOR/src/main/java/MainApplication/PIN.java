package MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PIN extends JFrame implements ActionListener {
    String pin;
    JLabel l1, l2, l3, l4;
    JTextField tf1, tf2, tf3;
    JButton b1, b2, b3;

    public PIN(String pin){
        super("PIN change dialog");
        this.pin = pin;

        l1 = new JLabel("Change PIN");
        l2 = new JLabel("Enter Current PIN:");
        l3 = new JLabel("Enter New PIN:");
        l4 = new JLabel("Re-enter New PIN: ");

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();

        b1 = new JButton("Save");
        b2 = new JButton("Back");
        b3 = new JButton("Exit");

        setLayout(new GridLayout(5,5,5,5));

        add(l1); add(l2); add(tf1); add(l3); add(tf2); add(l4); add(tf3);

        add(b1); add(b2); add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setSize(750,750);
        setLocation(500,200);
        setVisible(true);

    }

    public synchronized void pinChange(String newPIN){
        try{
            DatabaseConnection conn = new DatabaseConnection();
            String q1 = "update bank set pin = '" + newPIN + "' where pin = '" + pin + "' ";
            String q2 = "update login set pin = '" + newPIN + "' where pin = '" + pin + "' ";
            String q3 = "update signup3 set pin = '" + newPIN + "' where pin = '" + pin + "' ";

            conn.s.executeUpdate(q1);
            conn.s.executeUpdate(q2);
            conn.s.executeUpdate(q3);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        try{
            String currPIN = tf1.getText();
            String newPIN = tf2.getText();

            if(ae.getSource()==b1){
                if(!currPIN.equals(pin) || !newPIN.equals(tf3.getText())){
                    JOptionPane.showMessageDialog(null, "Entered PIN incorrect!");
                }
                else if (tf1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter Current PIN");
                }
                else if (tf2.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter new PIN");
                }
                else if (tf3.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Re-Enter new PIN");
                }else {

                    Thread t = new Thread() {
                        public void run() {
                            pinChange(newPIN);
                        }
                    };

                    try {
                        t.start();
                        t.join();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "PIN changed successfully!");
                    setVisible(false);
                    new Transaction(newPIN).setVisible(true);
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
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PIN("").setVisible(true);
    }
}
