package app.restapi.servlets;

import app.restapi.models.Route;
import app.restapi.repository.RouteRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

// "/higher-distance"
public class HigherDistanceServlet extends HttpServlet {

    private RouteRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            repository = new RouteRepository(RouteRepository.getConnection());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // GET /higher-distance - вернуть количество элементов, значение поля distance которых больше заданного

        String strDistance;
        try {
            strDistance = req.getParameter("distance");

            if (strDistance == null || strDistance.trim().isEmpty()) {
                resp.setStatus(422);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(422);
            return;
        }

        long distance;
        try {
            distance = Long.parseLong(strDistance);
        } catch (Exception ignored) {
            resp.setStatus(422);
            return;
        }

        try {
            ArrayList<Route> routes = repository.getHigherDistanceRoutes(distance);

            resp.setStatus(200);
            resp.setContentType("application/json");

            PrintWriter printWriter = resp.getWriter();

            if (routes == null) {
                printWriter.write("{\"number_of_elements\":");
                printWriter.write(String.valueOf(0));
                printWriter.write("}");
                return;
            }

            printWriter.write("{\"number_of_elements\":");
            printWriter.write(String.valueOf(routes.size()));
            printWriter.write("}");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(400);
        }
    }
}
