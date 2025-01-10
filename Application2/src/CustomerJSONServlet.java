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
import java.sql.SQLException;

@WebServlet(urlPatterns = "/cus")
public class CustomerJSONServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    try{
        Connection connection = dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");

        ResultSet rs = pstm.executeQuery();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        while (rs.next()) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("id", rs.getString("id"));
            builder.add("name", rs.getString("name"));
            builder.add("address", rs.getString("address"));
            arrayBuilder.add(builder);
        }

        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("status", HttpServletResponse.SC_OK);
        response.add("message", "Data retrieved successfully");
        response.add("data", arrayBuilder);



        resp.setContentType("application/json");
        resp.getWriter().write(response.build().toString());

    } catch (SQLException e) {

        JsonObjectBuilder errorResponse = Json.createObjectBuilder();
        errorResponse.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        errorResponse.add("message", "Error");

        resp.setContentType("application/json");
        resp.getWriter().write(errorResponse.build().toString());
    }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Read JSON Object
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");


        //Save On Database
        Connection connection=null;
        try {
             connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer (id, name, address) VALUES (?, ?, ?)");
            connection.setAutoCommit(false);

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);

            pstm.executeUpdate();


            //Build Response Object
            JsonObjectBuilder Dataobjects = Json.createObjectBuilder();
            Dataobjects.add("id", id);
            Dataobjects.add("name", name);
            Dataobjects.add("address", address);


            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_OK);
            response.add("message", "Successfully added customer");
            response.add("data", Dataobjects);

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());


            connection.commit();
            System.out.println("Transaction committed successfully!");
        } catch (SQLException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message", "Error!!!!");
            response.add("data", "");

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());
        }finally {

            if (connection != null) {
                try {
                    System.out.println("closed");
                    connection.close();
                    connection.commit();
                } catch (Exception e) {
                    System.out.println("error???"+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Delete from customer where id = ?");
            pstm.setString(1, id);

            pstm.executeUpdate();

            JsonObjectBuilder Dataobjects = Json.createObjectBuilder();
            Dataobjects.add("id", id);



            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_OK);
            response.add("message", "Successfully Deleted customer");
            response.add("data", Dataobjects);

            connection.close();

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());

        } catch (SQLException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message", "Error!!!!");
            response.add("data", "");

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name = ?, address = ? WHERE id = ?");
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, id);

            pstm.executeUpdate();

            //Build Response Object
            JsonObjectBuilder Dataobjects = Json.createObjectBuilder();
            Dataobjects.add("id", id);
            Dataobjects.add("name", name);
            Dataobjects.add("address", address);


            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_OK);
            response.add("message", "Successfully added customer");
            response.add("data", Dataobjects);

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());




        } catch (SQLException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message", "Error!!!!");
            response.add("data", "");

            resp.setContentType("application/json");
            resp.getWriter().write(response.build().toString());

        }
    }
}