import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/item")
public class ItemJSONServlet extends HttpServlet {


    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from item");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String code = rs.getString("code");
                String description = rs.getString("description");
                String qty = rs.getString("qtyOnHand");
                String price = rs.getString("unitPrice");

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code", code);
                objectBuilder.add("description", description);
                objectBuilder.add("qtyOnHand", qty);
                objectBuilder.add("price", price);

                arrayBuilder.add(objectBuilder);
            }

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_OK);
            response.add("message", "Data retrieved successfully");
            response.add("data", arrayBuilder);

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());

        }catch (Exception e) {
            JsonObjectBuilder errorResponse = Json.createObjectBuilder();
            errorResponse.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errorResponse.add("message", "Error");

            resp.setContentType("application/json");
            resp.getWriter().write(errorResponse.build().toString());

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String code = jsonObject.getString("id");
        String desc = jsonObject.getString("name");
        String qty = jsonObject.getString("address");
        String price = jsonObject.getString("address");
    }
}
