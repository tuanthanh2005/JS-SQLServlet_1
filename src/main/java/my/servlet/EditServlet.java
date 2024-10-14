/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author trant
 */
public class EditServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String method = request.getMethod();
            if (method.equalsIgnoreCase("get")) {
                //1b1 lấy giá trị tham số từ client

                String id = request.getParameter("id");
                // b2 xử lý yêu cầu ( truy cập csdl để xía user id)
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    conn = DatabaseUtil.getConnection();
                    ps = conn.prepareStatement("select*from users where id = " + id);
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Update user</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Update User</h1>");
                        out.println("<form action='EditServlet' method='POST'>"
                                + "<input type='hidden' name='id' value=" + id + ">"
                                + "<table border='0'>"
                                + "<tr>"
                                + "<td>Name</td>"
                                + "<td><input type='text' name='uname' value=" + rs.getString(2) + " required /></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td>Password</td>"
                                + "<td><input type='password' name='upass' value=" + rs.getString(3) + " required /></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td>Email</td>"
                                + "<td><input type='email' name='email' value=" + rs.getString(4) + " /></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td>Country</td>"
                                + "<td>"
                                + "<select name='country'>"
                                + " <option value='Vietnam'" + (rs.getString(5).equals("Vietnam") ? " selected" : "") + ">Vietnam</option>"
                                + " <option value='USA'" + (rs.getString(5).equals("USA") ? " selected" : "") + ">USA</option>"
                                + " <option value='UK'" + (rs.getString(5).equals("UK") ? " selected" : "") + ">UK</option>"
                                + " <option value='Other'" + (rs.getString(5).equals("Other") ? " selected" : "") + ">Other</option>"
                                + "</select>"
                                + "</td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td colspan=2><input type='submit' value='Edit & Save' /></td>"
                                + "</tr>"
                                + "</table>"
                                + "</form>");
                        out.println("</body>");
                        out.println("</html>");

                    }
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Loi:" + e.toString());
                    out.print("<h2>Thao Tcas xóa user thất bại</h2>");
                }
            } else if (method.equalsIgnoreCase("post")) {
                // xử lý post
                //b1 lấy tham số của client
                String uname = request.getParameter("uname");
                String upass = request.getParameter("upass");
                String email = request.getParameter("email");
                String country = request.getParameter("country");
                String id = request.getParameter("id");
                // xử lý csdl để thêm mới user
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = DatabaseUtil.getConnection();

                    // 3 sout " kết nối ok"
                    ps = conn.prepareStatement("update users set name=?, password=?,email=?, country=? where id=?");
                    ps.setString(1, uname);
                    ps.setString(2, upass);
                    ps.setString(3, email);
                    ps.setString(4, country);
                    ps.setInt(5, Integer.parseInt(id));

                    //4 kết quả
                    int kq = ps.executeUpdate();
                    // xử lý trả về
                    if (kq > 0) {
                        out.print("<h2> Cập nhật user thành công</h2>");
                    } else {
                        out.print("<h2> Cập nhật user thất bại</h2>");
                    }
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Loi:" + e.toString());
                    out.println("<h2>Cap nhat user thất bại</h2>");

//chèn nội dung của ViewServlet vào kết quả phản hồi
                    request.getRequestDispatcher("ViewServlet").include(request, response);

                }
            }


            /* TODO output your page here. You may use following sample code. */
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
