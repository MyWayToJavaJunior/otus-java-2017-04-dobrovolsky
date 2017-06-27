package ru.otus.dobrovolsky.servlet;

import ru.otus.dobrovolsky.base.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final int PERIOD_MS = 3000;

    private final DBService dbService;

    private String login;
    private String pass;

    public AdminServlet(DBService dbService) {
        this.dbService = dbService;
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));

        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());

        //cache data
        pageVariables.put("queryCacheHitCount", dbService.getQueryCacheHitCount());
        pageVariables.put("queryCacheMissCount", dbService.getQueryCacheMissCount());
        pageVariables.put("secondLevelCacheHitCount", dbService.getSecondLevelCacheHitCount());
        pageVariables.put("secondLevelCacheMissCount", dbService.getSecondLevelCacheMissCount());
        pageVariables.put("secondLevelHitU", dbService.getSecondLevelHitU());
        pageVariables.put("secondLevelMissU", dbService.getSecondLevelMissU());
        pageVariables.put("secondLevelHitP", dbService.getSecondLevelHitP());
        pageVariables.put("secondLevelMissP", dbService.getSecondLevelMissP());
        pageVariables.put("secondLevelHitA", dbService.getSecondLevelHitA());
        pageVariables.put("secondLevelMissA", dbService.getSecondLevelMissA());
        pageVariables.put("queries", dbService.getQueries());
        pageVariables.put("queryCachePutCount", dbService.getQueryCachePutCount());
        pageVariables.put("secondLevelCachePutCount", dbService.getSecondLevelCachePutCount());

        pageVariables.put("getSessionOpenCount", dbService.getSessionOpenCount());
        pageVariables.put("getSessionCloseCount", dbService.getSessionCloseCount());

        pageVariables.put("secondLevelPutCountU", dbService.getSecondLevelPutCountU());
        pageVariables.put("secondLevelSizeU", dbService.getSecondLevelSizeU());
        pageVariables.put("secondLevelPutCountP", dbService.getSecondLevelPutCountP());
        pageVariables.put("secondLevelSizeP", dbService.getSecondLevelSizeP());
        pageVariables.put("secondLevelPutCountA", dbService.getSecondLevelPutCountA());
        pageVariables.put("secondLevelSizeA", dbService.getSecondLevelSizeA());

        pageVariables.put("secondLevelCacheRegionNames", dbService.getSecondLevelCacheRegionNames());

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

//        String clearCache = request.getParameter("clear");
//        if (clearCache != null) {
//            SessionFactory sessionFactory = dbService.getSessionFactory();
//
//            sessionFactory.getCache().evictEntityRegion(UserDataSet.class);
//            sessionFactory.getCache().evictEntityRegion(PhoneDataSet.class);
//            sessionFactory.getCache().evictEntityRegion(AddressDataSet.class);
//
//            sessionFactory.getCache().evictEntityRegions();
//            sessionFactory.getCache().evictCollectionRegions();
//            sessionFactory.getCache().evictDefaultQueryRegion();
//            sessionFactory.getCache().evictQueryRegions();
//        }

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
