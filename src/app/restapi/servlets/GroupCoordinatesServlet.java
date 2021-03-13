package app.restapi.servlets;

import app.restapi.models.Coordinates;
import app.restapi.repository.RouteRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

// "/group-coordinates"
public class GroupCoordinatesServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // GET /group-coordinates - сгруппировать элементы по значению поля
        // coordinates, вернуть количество элементов в каждой группе

        try {
            Map<Integer, Coordinates> results = repository.getRoutesGroupedByCoordinates();

            resp.setStatus(200);
            resp.setContentType("application/json");

            PrintWriter printWriter = resp.getWriter();

            if (results == null) {
                printWriter.write("{}");
            }

            printWriter.write(gson.toJson(results));
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(400);
        }
    }
}
