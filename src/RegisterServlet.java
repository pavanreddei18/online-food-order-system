package swiggy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        resp.setContentType("text/plain");
        try (Connection conn = DBConnection.getConnection()) {
            String ins = "INSERT INTO users (name,email,password) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            int row = ps.executeUpdate();
            if (row > 0) {
                resp.getWriter().println("Registered successfully");
            } else {
                resp.getWriter().println("Registration failed");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.getWriter().println("Email already exists");
        } catch (SQLException e) {
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}

