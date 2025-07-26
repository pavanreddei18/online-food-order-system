import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            int row = ps.executeUpdate();
            if(row > 0) {
                out.println("Registered successfully");
            } else {
                out.println("Registration failed");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            out.println("Email already exists");
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
