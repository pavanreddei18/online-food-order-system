
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int itemId = Integer.parseInt(request.getParameter("item_id"));

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO orders (user_id, item_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, itemId);

            int row = ps.executeUpdate();
            if(row > 0) {
                out.println("Order placed successfully");
            } else {
                out.println("Order placement failed");
            }
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
