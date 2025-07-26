import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM menu";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            JSONArray menuArray = new JSONArray();
            while(rs.next()) {
                JSONObject item = new JSONObject();
                item.put("id", rs.getInt("id"));
                item.put("item_name", rs.getString("item_name"));
                item.put("description", rs.getString("description"));
                item.put("price", rs.getDouble("price"));
                menuArray.put(item);
            }
            out.print(menuArray.toString());
        } catch (SQLException e) {
            out.print("{\"error\":\""+ e.getMessage() +"\"}");
        }
    }
}
