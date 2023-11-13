package cn.edu.swu.ws;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("text/html");
        try(Writer writer=response.getWriter()){
            writer.write("<center><h1>Hello World from Servlet</h1></center>");
        }
        //super.doGet(request, response);
    }
}
