import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Customer Servlet");

        //Establish database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","Ijse@123");
            ResultSet resultSet = connection.prepareStatement("select * from customer").executeQuery();


            //create Json Array
            JsonArrayBuilder allcus = Json.createArrayBuilder();

            while (resultSet.next()) {
                String id     = resultSet.getString("id");
                String name = resultSet.getString("name");
                String addres = resultSet.getString("address");

                System.out.println(id + " " + name + " " + addres);

                JsonObjectBuilder obj = Json.createObjectBuilder();
                obj.add("id", id);
                obj.add("name",name);
                obj.add("address",addres);

                allcus.add(obj);

            }

            resp.setContentType("application/json");

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.addHeader("Name","Keshan");

            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY);

            resp.getWriter().write(allcus.build().toString());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id"); // Corrected from "name" to "id"
        String name = req.getParameter("name"); // Corrected from "email" to "name"
        String adrs = req.getParameter("adrs"); // Corrected from "age" to "adrs"

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");

            String query = "INSERT INTO customer (id, name, address) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, adrs);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

            if (preparedStatement.executeUpdate() > 0) {
                objectBuilder.add("status", "success");
                objectBuilder.add("message", "Customer saved successfully!");
            } else {
                objectBuilder.add("status", "error");
                objectBuilder.add("message", "Failed to save customer.");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}