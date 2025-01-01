import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/lcy")
public class LifeCycleServlet extends HttpServlet {


    public LifeCycleServlet() {
        System.out.println("Inside LifeCycleServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do get Trigger");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Inside init");//servelt ekat adala req,respo handle karann
    }

    @Override
    public void destroy() {
        System.out.println("Inside destroy");
    }
}
