
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trant
 */
public class Test {
    public static void main(String[] args) {
        Connection conn;
        try {
            
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                System.out.println("Drive Ok");
                conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=demo","sa", "1234");
                System.out.println("Connect OK");
        } catch (Exception e) {
            
            System.out.println("Loi: " + e.toString());
        }
    }
}
