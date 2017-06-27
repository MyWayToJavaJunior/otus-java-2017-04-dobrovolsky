package ru.otus.dobrovolsky.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASS_PARAMETER_NAME = "pass";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private static String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_PARAMETER_NAME, login == null ? "" : login);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPass = request.getParameter(PASS_PARAMETER_NAME);

        if ((requestLogin != null) || (requestPass != null)) {
            saveToSession(request, requestLogin, requestPass);
            saveToCookie(response, requestLogin, requestPass);
        }

        String page = getPage(requestLogin);
        response.getWriter().println(page);

        setOK(response);
    }

    private void saveToCookie(HttpServletResponse response, String requestLogin, String requestPass) {
        response.addCookie(new Cookie("L12-login", requestLogin + requestPass));
    }

    private void saveToSession(HttpServletRequest request, String requestLogin, String requestPass) {
        request.getSession().setAttribute("login", requestLogin);
        request.getSession().setAttribute("pass", requestPass);
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
