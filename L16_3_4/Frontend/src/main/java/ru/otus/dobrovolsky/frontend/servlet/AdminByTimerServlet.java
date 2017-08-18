package ru.otus.dobrovolsky.frontend.servlet;


import ru.otus.dobrovolsky.FrontendMain;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AdminByTimerServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin_by_timer.html";

    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final int PERIOD_MS = 5000;
    private static final Logger LOGGER = Logger.getLogger(AdminByTimerServlet.class.getName());
    private static Map<String, Object> pageVariables = null;
    private String login;
    private String pass;
    private SocketClientChannel client;
    private Map<String, Object> cacheMap = new HashMap<>();

    public AdminByTimerServlet(SocketClientChannel client) {
        this.client = client;
        client.init();
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        pageVariables = new HashMap<>();

        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));

        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());

        //cache data
        cacheMap = FrontendMain.getCacheMap();
        pageVariables.putAll(cacheMap);

        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);

        String pass = (String) request.getSession().getAttribute("pass");
        pageVariables.put("pass", pass != null ? pass : "null");

        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");


        if ((login == null) || (pass == null)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        login = login == null ? request.getParameter("login") : login;
        pass = pass == null ? request.getParameter("pass") : pass;

        if ((login == null) || (pass == null)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Login / password is empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!login.equals("admin") || !pass.equals("admin")) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        pageVariables = createPageVariablesMap(request);

        for (Map.Entry<String, Object> entry : pageVariables.entrySet()) {
            LOGGER.info(entry.getKey() + "  " + entry.getValue());
        }

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
