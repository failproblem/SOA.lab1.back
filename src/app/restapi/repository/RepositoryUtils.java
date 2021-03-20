package app.restapi.repository;

import app.restapi.models.Coordinates;
import app.restapi.models.Location;
import app.restapi.models.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class RepositoryUtils {

    public static ArrayList<Route> extractResult(ResultSet resultSet) throws SQLException {
        ArrayList<Route> routes = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");

            float x = resultSet.getFloat("x");
            float y = resultSet.getFloat("y");

            Date creationDate = resultSet.getDate("creation_date");

            Location from;
            long fromX = 0;
            long fromY = 0;
            long fromZ = 0;
            String fromName = null;
            boolean skipFrom = false;
            try {
                fromX = resultSet.getLong("from_x");
                fromY = resultSet.getLong("from_y");
                fromZ = resultSet.getLong("from_z");
                fromName = resultSet.getString("from_name");
            } catch (Exception ignore) {
                skipFrom = true;
            }
            from = skipFrom ? null : fromName == null || fromName.isEmpty() ? null : new Location(fromX, fromY, fromZ, fromName);

            Location to;
            long toX = 0;
            long toY = 0;
            long toZ = 0;
            String toName = null;
            boolean skipTo = false;
            try {
                toX = resultSet.getLong("to_x");
                toY = resultSet.getLong("to_y");
                toZ = resultSet.getLong("to_z");
                toName = resultSet.getString("to_name");
            } catch (Exception ignore) {
                skipTo = true;
            }
            to = skipTo ? null : toName == null || toName.isEmpty() ? null : new Location(toX, toY, toZ, toName);

            long distance = resultSet.getLong("distance");

            Coordinates coordinates = new Coordinates(x, y);
            Route route = new Route(id, name, coordinates, creationDate, from, to, distance);

            routes.add(route);
        }
        return routes;
    }
}
