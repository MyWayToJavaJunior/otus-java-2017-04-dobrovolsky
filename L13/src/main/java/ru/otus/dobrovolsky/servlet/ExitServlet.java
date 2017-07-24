package ru.otus.dobrovolsky.servlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExitServlet extends HttpServlet {

    private static final String EXIT_PAGE_TEMPLATE = "exit.html";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("logout") != null) {
            request.getSession().invalidate();
        }

        response.getWriter().println(TemplateProcessor.instance().getPage(EXIT_PAGE_TEMPLATE, null/*pageVariables*/));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
}
