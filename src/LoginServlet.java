import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT password FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String storedPass = rs.getString("password");
                if(password.equals(storedPass)) {
                    out.println("Login successful");
                } else {
                    out.println("Invalid password");
                }
            } else {
                out.println("No user found");
            }
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
