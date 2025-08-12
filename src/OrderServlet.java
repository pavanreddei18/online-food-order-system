package swiggy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        int uid = (int) session.getAttribute("userId");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String sel = "SELECT o.id AS oid, m.id AS item_id, m.item_name AS nm, m.price FROM orders o JOIN menu m ON o.item_id = m.id WHERE o.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sel);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();

            boolean hasOrders = false;

            while (rs.next()) {
                hasOrders = true;
                int oid = rs.getInt("oid");
                int itemId = rs.getInt("item_id");
                String name = rs.getString("nm");
                double price = rs.getDouble("price");

                out.printf("<div class='order-card' data-item-id='%d'>", itemId);
                out.println("<img src='' alt='Food item' />");
                out.println("<div>");
                out.printf("<h3>%s</h3>", name);
                out.printf("<p>Order #%d</p>", oid);
                out.printf("<div class='price'>₹%.2f</div>", price);
                out.println("</div></div>");
            }

            if (!hasOrders) {
                out.println("<p style='text-align:center;'>You have no orders yet.</p>");
            }

        } catch (SQLException e) {
            out.println("<p style='color:red; text-align:center;'>Error loading orders: " + e.getMessage() + "</p>");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        resp.setContentType("text/plain");
        if (session == null || session.getAttribute("userId") == null) {
            resp.getWriter().println("User not logged in");
            return;
        }
        int uid = (int) session.getAttribute("userId");
        String itemIdStr = req.getParameter("item_id");
        try (Connection conn = DBConnection.getConnection()) {
            int iid = Integer.parseInt(itemIdStr);
            String ins = "INSERT INTO orders(user_id,item_id) VALUES(?,?)";
            PreparedStatement ps = conn.prepareStatement(ins);
            ps.setInt(1, uid);
            ps.setInt(2, iid);
            int r = ps.executeUpdate();
            resp.getWriter().println(r > 0 ? "Order placed successfully" : "Order failed");
        } catch (Exception e) {
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
