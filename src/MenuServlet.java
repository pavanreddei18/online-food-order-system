package swiggy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<div id='menu-container' style='display:grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap:20px; max-width:1200px; margin:auto; padding:20px;'>");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM menu";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("item_name");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");

                out.printf("<div class=\"menu-item\" data-item-id=\"%d\" " +
                        "style='background:#fff; border-radius:10px; box-shadow:0 4px 12px rgba(0,0,0,0.1); overflow:hidden; display:flex; flex-direction:column; cursor:pointer;'>", id);

                // Image placeholder will be inserted by frontend JS
                out.println("<div class='menu-details' style='padding:16px;'>");

                out.printf("<h3 style='margin:0; font-size:1.2rem; color:#333;'>%s</h3>", name);
                out.printf("<p style='margin:8px 0; color:#555; font-size:0.95rem; line-height:1.4;'>%s</p>", desc);
                out.printf("<div class='menu-price' style='color:#e41c24; font-weight:bold; margin-top:8px; font-size:1.1rem;'>₹%.2f</div>", price);

                // Tags
                out.println("<div class='menu-tags' style='margin-top:10px;'>");
                String lname = name.toLowerCase();
                if (lname.contains("vegan")) out.println("<span class='tag vegan' style='display:inline-block; font-size:0.75rem; padding:4px 8px; border-radius:12px; color:#fff; margin-right:6px; margin-top:5px; font-weight:600; text-transform:capitalize; background:#27ae60;'>Vegan</span>");
                if (lname.contains("spicy") || lname.contains("wings")) out.println("<span class='tag spicy' style='display:inline-block; font-size:0.75rem; padding:4px 8px; border-radius:12px; color:#fff; margin-right:6px; margin-top:5px; font-weight:600; text-transform:capitalize; background:#e74c3c;'>Spicy</span>");
                if (lname.contains("cheese") || lname.contains("dessert") || lname.contains("cheesecake")) out.println("<span class='tag dessert' style='display:inline-block; font-size:0.75rem; padding:4px 8px; border-radius:12px; color:#fff; margin-right:6px; margin-top:5px; font-weight:600; text-transform:capitalize; background:#9b59b6;'>Dessert</span>");
                out.println("</div>");

                // Order button
                out.println("<button style='background:#e41c24; color:#fff; border:none; border-radius:6px; padding:12px; margin:16px 0 0 0; font-size:1rem; font-weight:bold; width:100%; cursor:pointer; display:flex; align-items:center; justify-content:center; gap:6px;'>Order &bull; ₹" + String.format("%.2f", price) + "</button>");

                out.println("</div>"); // close menu-details
                out.println("</div>"); // close menu-item
            }

        } catch (SQLException e) {
            out.println("<p style='color:red; text-align:center;'>Error loading menu: " + e.getMessage() + "</p>");
        }

        out.println("</div>"); // close menu-container
    }
}

