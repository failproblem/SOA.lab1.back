package app.restapi.servlets;

import app.restapi.models.Coordinates;
import app.restapi.models.Location;
import app.restapi.models.Route;
import app.restapi.repository.RouteRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.stream.Collectors;

// "/routes/*"
public class RoutesByIdServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // GET /routes/{id} - получение элемента с указанным id

        String strRouteId;
        try {
            strRouteId = URLDecoder.decode(req.getRequestURL().toString().split("/routes/")[1], "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(422);
            return;
        }

        int routeId;
        try {
            routeId = Integer.parseInt(strRouteId);
        } catch (Exception ignored) {
            resp.setStatus(422);
            return;
        }

        if (routeId < 1) {
            resp.setStatus(422);
            return;
        }

        Route route = repository.getRouteById(routeId);
        if (route != null) {
            resp.setStatus(200);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(gson.toJson(route));
        } else {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        // PUT /routes/{id} - обновление информации об элементе с указанным id

        String strRouteId;
        try {
            strRouteId = URLDecoder.decode(req.getRequestURL().toString().split("/routes/")[1], "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(422);
            return;
        }

        int routeId;
        try {
            routeId = Integer.parseInt(strRouteId);
        } catch (Exception ignored) {
            resp.setStatus(422);
            return;
        }

        if (routeId < 1) {
            resp.setStatus(422);
            return;
        }

        try {

            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Route route = gson.fromJson(body, Route.class);
            route.setId(routeId);
            route.preUpdateValidate();

            repository.updateRoute(routeId, route);
            resp.setStatus(200);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        // DELETE /routes/{id} - удаление элемента с указанным id

        String strRouteId;
        try {
            strRouteId = URLDecoder.decode(req.getRequestURL().toString().split("/routes/")[1], "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(422);
            return;
        }

        int routeId;
        try {
            routeId = Integer.parseInt(strRouteId);
        } catch (Exception ignored) {
            resp.setStatus(422);
            return;
        }

        if (routeId < 1) {
            resp.setStatus(422);
            return;
        }

        Route route = repository.getRouteById(routeId);
        if (route != null) {
            repository.deleteRoute(routeId);
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
        }
    }
}
