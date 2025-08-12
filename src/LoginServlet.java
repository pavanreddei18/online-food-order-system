package swiggy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        resp.setContentType("text/plain");
        try (Connection conn = DBConnection.getConnection()) {
            String sel = "SELECT id, password FROM users WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(sel);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String stored = rs.getString("password");
                int uid = rs.getInt("id");
                if (password.equals(stored)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("userId", uid);
                    resp.getWriter().println("Login successful:" + uid);
                } else {
                    resp.getWriter().println("Invalid password");
                }
            } else {
                resp.getWriter().println("No user found");
            }
        } catch (SQLException e) {
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}

