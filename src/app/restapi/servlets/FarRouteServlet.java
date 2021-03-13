package app.restapi.servlets;

import app.restapi.models.Route;
import app.restapi.repository.RouteRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

// "/far-route"
public class FarRouteServlet extends HttpServlet {

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
        // GET /far-route - получение элемента, значение поля to которого является максимальным

        try {
            Route route = repository.getMostFarRoute();

            resp.setStatus(200);
            resp.setContentType("application/json");

            PrintWriter printWriter = resp.getWriter();

            if (route == null) {
                printWriter.write("{}");
            }

            printWriter.write(gson.toJson(route));
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(400);
        }
    }
}
