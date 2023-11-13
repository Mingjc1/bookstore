package cn.edu.swu.ws;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

public class LoginServlet extends HttpServlet{
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String user = request.getParameter("user");
         String pass = request.getParameter("pass");
         String code = request.getParameter("code");
         HttpSession session = request.getSession(true);

         if (code == null || !code.equalsIgnoreCase((String) session.getAttribute(AuthCodeServlet.AUTH_CODE))){
             response.sendRedirect("./index.html");
             return;
         }

         response.setContentType("text/html");
         if (user != null && user.equals("admin")){
             if(pass != null && pass.equals("123456")){
                //登录成功，在session中添加成功登录的标记
                 session.setAttribute(AuthFilter.LOGIN_STATUS, AuthStatus.LOGIN_SUCCESS);



                 //返回一个重定向
                 response.sendRedirect("./main.html");
                 /*
               //返回html
                 try(Writer writer = response.getWriter()){
                     writer.write("<br><br><center>");
                     writer.write(" <h1 style=\"color: red\">欢迎登录网上书城！！</h1>");
                     writer.write(this.printParameters(request));
                     writer.write("<br><br>");
                     writer.write(this.printHeaders(request));
                     writer.write("</center>");
                 }*/
                return;
             }
         }

         try(Writer writer = response.getWriter()) {

             response.setHeader("Refresh", "5;url=./index.html");
             writer.write("<center><h1 style='color:red'>用户名密码不正确，需要重新登陆，5秒后自动跳转！</h1></center>");
         }
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"用户名或密码不正确，请重新登录！");
     }
    private String printHeaders(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style='width:70%' cell-padding='5px' cell-spacing='20px'>");
        sb.append("<tr style='background-color:#336699;color:#FFF'><th>Header名称</th><th>Header值</th></tr>");

        Enumeration<String> names = request.getHeaderNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            sb.append(String.format("<tr style='background-color:#eee'><td>%s</td><td>%s</td></tr>",
                    name, request.getHeader(name)));
        }
        sb.append("</table>");

        return sb.toString();
    }

    private String printParameters(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style='width:70%' cell-padding='5px' cell-spacing='20px'>");
        sb.append("<tr style='background-color:#336699;color:#FFF'><th>参数名称</th><th>参数值</th></tr>");

        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            sb.append(String.format("<tr style='background-color:#eee'><td>%s</td><td>%s</td></tr>",
                    name, request.getParameter(name)));
        }
        sb.append("</table>");

        return sb.toString();
    }
}
