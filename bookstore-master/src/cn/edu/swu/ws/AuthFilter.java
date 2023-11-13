package cn.edu.swu.ws;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class AuthFilter extends HttpFilter {
    public final static String LOGIN_STATUS = "LOGIN_STATUS";
    private String[] ignoreUrls = new String[]{};

    @Override
    public void init(FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("ignoreUrls");
        if (ignoreUrls != null){
            this.ignoreUrls = urls.split(";");
        }
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 对其它资源进行登录验证
        HttpSession session = request.getSession(true);

        String uri = request.getRequestURI();
        for (String url :ignoreUrls){
            if (uri.endsWith(url)){
                chain.doFilter(request,response);
                return;
            }
        }


        //System.out.println(uri);
        //if (uri.endsWith("/index.html") || uri.endsWith("/login") || uri.endsWith(".png")|| uri.endsWith(".jpg")|| uri.endsWith(".gif")|| uri.endsWith(".css")|| uri.endsWith(".js")){
        //    chain.doFilter(request,response);
        //    return;
        //}


        AuthStatus status = (AuthStatus) session.getAttribute(LOGIN_STATUS);

        if (status != null && status.equals(AuthStatus.LOGIN_SUCCESS))  {
            chain.doFilter(request, response);
            return;
        } else {
            response.sendRedirect("./index.html");
        }
    }

}
