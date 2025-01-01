import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/new")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String servletPath = req.getServletPath();
       String requestURI = req.getRequestURI();
       String contextPath = req.getContextPath();
       String method = req.getMethod();
       String pathInfo = req.getPathInfo();

        System.out.println("Servlrt Path :"+servletPath);
        System.out.println("Servlrt URI :"+requestURI);
        System.out.println("Servlrt Context Path :"+contextPath);
        System.out.println("Req Method :"+method);
        System.out.println(" Path Info :"+pathInfo);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("doPost Method IS Invoked");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("doPut Method IS Invoked");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("doDelete Method IS Invoked");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("doOption Method IS Invoked");
    }
}