package app.restapi.servlets;

import app.restapi.models.Route;
import app.restapi.repository.RouteRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// "/routes"
public class RoutesServlet extends HttpServlet {

    private Gson gson;
    private RouteRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            repository = new RouteRepository(RouteRepository.getConnection());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // GET /routes - получение массива элементов, поддержка сортировки и
        // фильтрации по любой комбинации полей, постраничный вывод результатов
        // выборки с указанием размера и порядкового номера выводимой страницы

        try {
            ArrayList<Route> routes;

            String queryString = req.getQueryString();
            if (queryString == null) {
                routes = repository.getAllRoutes();

                resp.setStatus(200);
                resp.setContentType("application/json");

                PrintWriter printWriter = resp.getWriter();

                if (routes == null) {
                    printWriter.write("{}");
                    return;
                }

                printWriter.write(gson.toJson(routes));
                return;
            }

            Map<String, String> paramsMap = ServletUtils.splitQueryString(queryString);

            String[] tmp;
            Map<String, String> filters = null;
            Map<String, String> sorts = null;
            int pageSize = 0;
            int pageNumber = 0;

            if (paramsMap.get("filter_by") != null && !paramsMap.get("filter_by").isEmpty()) {
                filters = new HashMap<>();
                tmp = paramsMap.get("filter_by").split(",");
                String[] a;
                for (String filterStr : tmp) {
                    a = filterStr.split("=");
                    if (a.length > 1) {
                        filters.put(a[0], a[1]);
                    }
                }
            }

            if (paramsMap.get("sort_by") != null && !paramsMap.get("sort_by").isEmpty()) {
                sorts = new HashMap<>();
                tmp = paramsMap.get("sort_by").split(",");
                String[] a;
                for (String sortStr : tmp) {
                    a = sortStr.split(":");
                    if (a.length > 1) {
                        sorts.put(a[0], a[1]);
                    }
                }
            }

            if (paramsMap.get("page_size") != null && !paramsMap.get("page_size").isEmpty()) {
                pageSize = Integer.parseInt(paramsMap.get("page_size"));
                if (pageSize < 0) {
                    resp.setStatus(422);
                    return;
                }
            }

            if (paramsMap.get("page_number") != null && !paramsMap.get("page_number").isEmpty()) {
                pageNumber = Integer.parseInt(paramsMap.get("page_number"));
                if (pageNumber < 0) {
                    resp.setStatus(422);
                    return;
                }
            }

            System.out.println("Filters: " + filters);
            System.out.println("Sorts: " + sorts);

            try {
                routes = repository.getProcessedRoutes(filters, sorts, pageSize, pageNumber);

                resp.setStatus(200);
                resp.setContentType("application/json");

                PrintWriter printWriter = resp.getWriter();

                if (routes == null) {
                    printWriter.write("{}");
                    return;
                }

                printWriter.write(gson.toJson(routes));
            } catch (SQLException ex) {
                ex.printStackTrace();
                resp.setStatus(422);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // POST /routes - добавление нового элемента в базу

        try {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Route route = gson.fromJson(body, Route.class);
            route.preInsertValidate();

            repository.addRoute(route);
            resp.setStatus(200);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(400);
        }
    }
}
