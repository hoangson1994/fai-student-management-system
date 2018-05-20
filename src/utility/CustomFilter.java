package utility;

import com.googlecode.objectify.ObjectifyService;
import design_java_rest.RESTHandle;
import design_java_rest.entity.RESTConstantHttp;
import design_java_rest.util.RESTStringUtil;
import entity.Student;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFilter implements Filter {

    private static ArrayList<String> acceptMethods = new ArrayList<>();
    static {
        acceptMethods.add("GET");
        acceptMethods.add("OPTIONS");
        acceptMethods.add("DELETE");
        acceptMethods.add("PUT");
        acceptMethods.add("POST");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ObjectifyService.register(Student.class);
        RESTHandle.doOption((HttpServletResponse) response, acceptMethods);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
