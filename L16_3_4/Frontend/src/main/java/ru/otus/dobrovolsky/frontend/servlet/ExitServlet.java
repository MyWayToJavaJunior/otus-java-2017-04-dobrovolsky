package ru.otus.dobrovolsky.frontend.servlet;

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

        response.getWriter().println(TemplateProcessor.getInstance().getPage(EXIT_PAGE_TEMPLATE, null));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
