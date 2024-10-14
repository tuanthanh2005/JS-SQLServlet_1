/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package my.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseUtil {
    public static Connection getConnection() {
        Connection conn =null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=demo","test", "123");
        } catch (Exception e) {
            System.out.println("Loi :"+ e.toString());
        }
       return conn;
           
    }

  
    
}



