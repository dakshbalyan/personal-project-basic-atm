package MainApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    Connection c;
    Statement s;
    public DatabaseConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                    "root",
                    "example");
            s = c.createStatement();
        }
        catch (Exception e){
            System.out.println("Exception : "+e+" || caught");
        }
    }
}
