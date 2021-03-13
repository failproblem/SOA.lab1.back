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

            long fromX = resultSet.getLong("from_x");
            long fromY = resultSet.getLong("from_y");
            long fromZ = resultSet.getLong("from_z");
            String fromName = resultSet.getString("from_name");

            long toX = resultSet.getLong("to_x");
            long toY = resultSet.getLong("to_y");
            long toZ = resultSet.getLong("to_z");
            String toName = resultSet.getString("to_name");

            long distance = resultSet.getLong("distance");

            Coordinates coordinates = new Coordinates(x, y);
            Location from = new Location(fromX, fromY, fromZ, fromName);
            Location to = new Location(toX, toY, toZ, toName);

            Route route = new Route(id, name, coordinates, creationDate, from, to, distance);

            routes.add(route);
        }
        return routes;
    }
}
