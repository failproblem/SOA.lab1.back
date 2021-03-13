package app.restapi.servlets;

import app.restapi.models.Coordinates;
import app.restapi.models.Location;
import app.restapi.models.Route;
import app.restapi.repository.RouteRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;

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
        gson = new Gson();
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
            resp.setStatus(400);
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
            String name = null;
            Coordinates coordinates = null;
            Location from = null;
            Location to = null;

            try {
                name = req.getParameter("name");
            } catch (Exception ignored) {
            }

            try {
                float x = Float.parseFloat(req.getParameter("x"));
                float y = Float.parseFloat(req.getParameter("y"));

                coordinates = new Coordinates(x, y);
            } catch (Exception ignored) {
            }

            try {
                long fromX = Long.parseLong(req.getParameter("from_x"));
                long fromY = Long.parseLong(req.getParameter("from_y"));
                long fromZ = Long.parseLong(req.getParameter("from_z"));
                String fromName = req.getParameter("from_name");

                from = new Location(fromX, fromY, fromZ, fromName);
            } catch (Exception ignored) {
            }

            try {
                long toX = Long.parseLong(req.getParameter("to_x"));
                long toY = Long.parseLong(req.getParameter("to_y"));
                long toZ = Long.parseLong(req.getParameter("to_z"));
                String toName = req.getParameter("to_name");

                to = new Location(toX, toY, toZ, toName);
            } catch (Exception ignored) {
            }

            repository.updateRoute(routeId, name, coordinates, from, to);
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
