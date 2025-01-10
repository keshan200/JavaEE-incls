import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/m")
public class Main extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject json = reader.readObject();

        String id = json.getString("id");
        String name = json.getString("name");
        String address = json.getString("address");


        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into customer (id, name, address) values (?, ?, ?)");

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);

            pstm.executeUpdate();



            JsonObjectBuilder dataObjects = Json.createObjectBuilder();
            dataObjects.add("id", id);
            dataObjects.add("name", name);
            dataObjects.add("address", address);

            //Response Object
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_CREATED);
            response.add("message", "success");
            response.add("data", dataObjects);


            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());


        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message", "error");
            response.add("data", "");


            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());
        }


    }
}