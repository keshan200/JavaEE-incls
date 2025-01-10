import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/dbcp")
public class InBuiltDBCPServlet extends HttpServlet {


    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");


                System.out.println(id + " ," + name + " , " + address);
            }



        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
