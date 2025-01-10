import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.*;
import java.io.IOException;


@WebServlet(urlPatterns = "/json")
public class JSONServlet extends HttpServlet {

    //Response body -> JSON send
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //data sending from response header
        /*resp.setHeader("Accept", "application/json");*/


        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("id", 23);
        objectBuilder.add("name", "John Doe");

        JsonObjectBuilder address1 = Json.createObjectBuilder();
        address1.add("street", "Some Street");
        address1.add("city", "Some City");

       JsonObjectBuilder address2 = Json.createObjectBuilder();
       address2.add("street", "uk");
       address2.add("city", "uk");


        JsonArrayBuilder allAddress = Json.createArrayBuilder();
        allAddress.add(address1);
        allAddress.add(address2);

        objectBuilder.add("address", allAddress);


        resp.setContentType("application/json");
        resp.getWriter().println(objectBuilder.build());
    }


    //request body -> JSON Read
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();

            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String address = jsonObject.getString("address");


            System.out.println(id + " " + name + " " + address);






            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",HttpServletResponse.SC_CREATED);
            response.add("message","Created");
            response.add("data",jsonObject);

            resp.setContentType("application/json");
            resp.getWriter().println(response.build().toString());

        }catch (Exception e){
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.add("message","Internal Server Error");
            response.add("data","");

            resp.setContentType("application/json");
            resp.getWriter().println(response.build().toString());
        }
    }
}
