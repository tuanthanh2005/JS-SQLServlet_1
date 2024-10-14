package my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String data = "";

            try {
                // Load the database driver
              conn = DatabaseUtil.getConnection();

                // Prepare the SQL statement
                ps = conn.prepareStatement("SELECT * FROM users");
                rs = ps.executeQuery();

                // Construct the HTML table
                data += "<table border =\"1\">";
                data += "<tr><th>Id</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>";

                while (rs.next()) {
                    data += "<tr>";
                    data += "<td>" + rs.getInt(1) + "</td>";
                    data += "<td>" + rs.getString(2) + "</td>";
                    data += "<td>" + rs.getString(3) + "</td>";
                    data += "<td>" + rs.getString(4) + "</td>";
                    data += "<td>" + rs.getString(5) + "</td>";
                    data += "<td> <a href='EditServlet?id=" + rs.getInt(1) + "'>Edit</a></td>";
                    data += "<td> <a href='DeleteServlet?id=" + rs.getInt(1) + "'>Delete</a></td>";
                    data += "</tr>";
                }
                data += "</table>";

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                out.println("Thất Bại.");
            } 
//                // Close the result set and the statement
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//                // Close the connection
//                if (conn != null) conn.close();
            

            // Output the HTML content
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>User List</h1>");
            out.println(data);
            out.println("</body>");
            out.println("</html>");
        }
    }

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
