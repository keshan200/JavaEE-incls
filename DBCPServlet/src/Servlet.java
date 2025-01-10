import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/db")
public class Servlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



   /*   BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/company");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("Ijse@123");
        basicDataSource.setMaxTotal(5);//connection kiyak hadanwada
        basicDataSource.setInitialSize(5);//kiyak initialize karanawada


        //Common interface to all servlet
        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("dataSource", basicDataSource);*/


        //Common interface to all servlet
        ServletContext servletContext = req.getServletContext();
        BasicDataSource basicDataSource = (BasicDataSource) servletContext.getAttribute("dataSource");

        //get Connection
        try {
            Connection connection = basicDataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
            }
            connection.close();
           resp.setContentType("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("errorr>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("DBCP");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Common interface to all servlet
        ServletContext servletContext = req.getServletContext();
        BasicDataSource basicDataSource = (BasicDataSource) servletContext.getAttribute("dataSource");


        //get Connection
        try {
            Connection connection = basicDataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
            }
            resp.setContentType("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("errorr>>>>"+e.getMessage());
            throw new RuntimeException(e);
        }

    }
}