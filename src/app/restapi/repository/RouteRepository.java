package app.restapi.repository;

import app.restapi.models.Coordinates;
import app.restapi.models.Location;
import app.restapi.models.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RouteRepository {

    private final Connection connection;

    //private static final String DATABASE_STRING = "jdbc:postgresql://localhost:5432/studs";
    private static final String DATABASE_STRING = "jdbc:postgresql://pg:5432/studs?currentSchema=s207603";
    private static final String DATABASE_USERNAME = "s207603";
    private static final String DATABASE_PASSWORD = "udb647";

    private static final String SELECT_STATEMENT = "SELECT * FROM \"routes\"";
    private static final String INSERT_STATEMENT = "INSERT INTO \"routes\" (\"name\", x, y, \"from_x\", \"from_y\", \"from_z\", \"from_name\", \"to_x\", \"to_y\", \"to_z\", \"to_name\", \"distance\", \"creation_date\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_STATEMENT = "UPDATE \"routes\" SET ";
    private static final String DELETE_STATEMENT = "DELETE FROM \"routes\" WHERE ";
    private static final long MIN_DISTANCE_VAL = 2;
    public RouteRepository(Connection connection) {
        this.connection = connection;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }

        System.out.println("PostgreSQL JDBC Driver successfully loaded");
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_STRING, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            System.err.println("- - - - - Connection Failed - - - - -");
            e.printStackTrace();
            return null;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, "routes", null);
            if (!res.next()) {
                createTable(connection);
            }
        } else {
            System.err.println("Failed to make connection to database");
        }
        return connection;
    }

    private static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE routes " +
                "(id                    SERIAL                      PRIMARY KEY," +
                " name                  TEXT                        NOT NULL, " +
                " x                     REAL                        NOT NULL, " +
                " y                     REAL                        NOT NULL, " +
                " creation_date         TIMESTAMP WITH TIME ZONE    NOT NULL, " +
                " from_x                BIGINT                      NOT NULL, " +
                " from_y                BIGINT                      NOT NULL, " +
                " from_z                BIGINT                      NOT NULL, " +
                " from_name             TEXT                        NOT NULL, " +
                " to_x                  BIGINT                      NOT NULL, " +
                " to_y                  BIGINT                      NOT NULL, " +
                " to_z                  BIGINT                      NOT NULL, " +
                " to_name               TEXT                        NOT NULL, " +
                " distance              BIGINT                      NOT NULL)";
        statement.executeUpdate(sql);
        System.out.println("Создаём таблицу");
        statement.close();
    }

    public Route getRouteById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT + " WHERE \"id\"=" + id + ";")) {
            ArrayList<Route> routes = RepositoryUtils.extractResult(statement.executeQuery());
            if (routes.size() == 0)
                return null;
            return routes.get(0);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Route getMostFarRoute() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT + " WHERE to_x+to_y+to_z=(SELECT MAX(to_x + to_y + to_z) FROM \"routes\");")) {
            ArrayList<Route> routes = RepositoryUtils.extractResult(statement.executeQuery());
            if (routes.size() == 0)
                return null;
            return routes.get(0);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Route> getAllRoutes() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT + ";")) {
            ArrayList<Route> routes = RepositoryUtils.extractResult(statement.executeQuery());
            if (routes.size() == 0)
                return null;
            return routes;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Route> getProcessedRoutes(Map<String, String> filters, Map<String, String> sorts, int pageSize, int pageNumber) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder(SELECT_STATEMENT);

        if (filters != null) {
            stringBuilder.append(" WHERE ");
            int i = 0;
            if (filters.get("id") != null) {
                stringBuilder.append("\"id\"='").append(filters.get("id")).append("' ");
                i++;
            }
            if (filters.get("name") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"name\"='").append(filters.get("name")).append("' ");
                i++;
            }
            if (filters.get("x") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"x\"='").append(filters.get("x")).append("' ");
                i++;
            }
            if (filters.get("y") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"y\"='").append(filters.get("y")).append("' ");
                i++;
            }
            if (filters.get("creation_date") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"creation_date\"::text LIKE '%").append(filters.get("creation_date")).append("%' ");
                i++;
            }
            if (filters.get("from_x") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"from_x\"='").append(filters.get("from_x")).append("' ");
                i++;
            }
            if (filters.get("from_y") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"from_y\"='").append(filters.get("from_y")).append("' ");
                i++;
            }
            if (filters.get("from_z") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"from_z\"='").append(filters.get("from_z")).append("' ");
                i++;
            }
            if (filters.get("from_name") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"from_name\"='").append(filters.get("from_name")).append("' ");
                i++;
            }
            if (filters.get("to_x") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"to_x\"='").append(filters.get("to_x")).append("' ");
                i++;
            }
            if (filters.get("to_y") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"to_y\"='").append(filters.get("to_y")).append("' ");
                i++;
            }
            if (filters.get("to_z") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"to_z\"='").append(filters.get("to_z")).append("' ");
                i++;
            }
            if (filters.get("to_name") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"to_name\"='").append(filters.get("to_name")).append("' ");
                i++;
            }
            if (filters.get("distance") != null) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                stringBuilder.append(" \"distance\"='").append(filters.get("distance")).append("' ");
            }
        }

        if (sorts != null) {
            stringBuilder.append(" ORDER BY ");
            int i = 0;
            if (sorts.get("id") != null) {
                stringBuilder.append(" \"id\" ").append(sorts.get("id"));
                i++;
            }
            if (sorts.get("name") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"name\" ").append(sorts.get("name"));
                i++;
            }
            if (sorts.get("creation_date") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"creation_date\" ").append(sorts.get("creation_date"));
                i++;
            }
            if (sorts.get("x") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"x\" ").append(sorts.get("x"));
                i++;
            }
            if (sorts.get("y") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"y\" ").append(sorts.get("y"));
                i++;
            }
            if (sorts.get("from_x") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"from_x\" ").append(sorts.get("from_x"));
                i++;
            }
            if (sorts.get("from_y") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"from_y\" ").append(sorts.get("from_y"));
                i++;
            }
            if (sorts.get("from_z") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"from_z\" ").append(sorts.get("from_z"));
                i++;
            }
            if (sorts.get("from_name") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"from_name\" ").append(sorts.get("from_name"));
                i++;
            }
            if (sorts.get("to_x") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"to_x\" ").append(sorts.get("to_x"));
                i++;
            }
            if (sorts.get("to_y") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"to_y\" ").append(sorts.get("to_y"));
                i++;
            }
            if (sorts.get("to_z") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"to_z\" ").append(sorts.get("to_z"));
                i++;
            }
            if (sorts.get("to_name") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"to_name\" ").append(sorts.get("to_name"));
                i++;
            }
            if (sorts.get("distance") != null) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" \"distance\" ").append(sorts.get("distance"));
            }
        }

        if (pageSize > 0) {
            stringBuilder.append(" LIMIT ");
            stringBuilder.append(pageSize);
            stringBuilder.append(" OFFSET ");
            stringBuilder.append(pageNumber * pageSize);
        }

        stringBuilder.append(";");
        String sqlRequest = stringBuilder.toString();
        System.out.println("Sql: " + sqlRequest);
        PreparedStatement statement = connection.prepareStatement(sqlRequest);
        return RepositoryUtils.extractResult(statement.executeQuery());
    }

    public ArrayList<Route> getHigherDistanceRoutes(long distance) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT + " WHERE \"distance\" > " + distance + ";")) {
            ArrayList<Route> routes = RepositoryUtils.extractResult(statement.executeQuery());
            if (routes.size() == 0)
                return null;
            return routes;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Map<Integer, Coordinates> getRoutesGroupedByCoordinates() {
        HashMap<Integer, Coordinates> groups = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id), x, y FROM \"routes\" GROUP BY x, y;")) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int count;
                float x, y;

                count = result.getInt(1);
                x = result.getFloat("x");
                y = result.getFloat("y");

                Coordinates coordinates = new Coordinates(x, y);
                coordinates.validate();
                groups.put(count, coordinates);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return groups;
    }

    public void addRoute(Route route) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)) {

            statement.setString(1, route.getName());
            statement.setFloat(2, route.getCoordinates().getX());
            statement.setFloat(3, route.getCoordinates().getY());

            if (route.getFrom() != null) {
                statement.setLong(4, route.getFrom().getX());
                statement.setLong(5, route.getFrom().getY());
                statement.setLong(6, route.getFrom().getZ());
                statement.setString(7, route.getFrom().getName());
            } else {
                statement.setLong(4, 0);
                statement.setLong(5, 0);
                statement.setLong(6, 0);
                statement.setString(7, "");
            }

            if (route.getTo() != null) {
                statement.setLong(8, route.getTo().getX());
                statement.setLong(9, route.getTo().getY());
                statement.setLong(10, route.getTo().getZ());
                statement.setString(11, route.getTo().getName());
            } else {
                statement.setLong(8, 0);
                statement.setLong(9, 0);
                statement.setLong(10, 0);
                statement.setString(11, "");
            }

            statement.setLong(12, route.getDistance());
            statement.setTimestamp(13, new Timestamp(route.getCreationDate().getTime()));

            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateRoute(int id, Route route) {
        StringBuilder stringBuilder = new StringBuilder(UPDATE_STATEMENT);

        try {
            int counter = 0;
            if (route.getName() != null) {
                stringBuilder.append(" \"name\"= ? ");
                counter++;
            }

            if (route.getCoordinates() != null) {
                if (counter > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(" \"x\"= ?, \"y\"= ? ");
                counter++;
            }

            if (route.getFrom() != null) {
                if (counter > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(" \"from_x\" = ?, \"from_y\" = ?, \"from_z\" = ?, \"from_name\" = ? ");
                counter++;
            }

            if (route.getTo() != null) {
                if (counter > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(" \"to_x\" = ?, \"to_y\" = ?, \"to_z\" = ?, \"to_name\" = ? ");
                counter++;
            }

            if (counter > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(" \"distance\" = ? ");
            stringBuilder.append(" WHERE \"id\" = ?;");
            PreparedStatement statement = connection.prepareStatement(stringBuilder.toString());

            int params = 0;
            if (route.getName() != null) {
                statement.setString(++params, route.getName());
            }

            if (route.getCoordinates() != null) {
                statement.setFloat(++params, route.getCoordinates().getX());
                statement.setFloat(++params, route.getCoordinates().getY());
            }

            if (route.getFrom() != null) {
                statement.setLong(++params, route.getFrom().getX());
                statement.setLong(++params, route.getFrom().getY());
                statement.setLong(++params, route.getFrom().getZ());
                statement.setString(++params, route.getFrom().getName());
            }

            if (route.getTo() != null) {
                statement.setLong(++params, route.getTo().getX());
                statement.setLong(++params, route.getTo().getY());
                statement.setLong(++params, route.getTo().getZ());
                statement.setString(++params, route.getTo().getName());
            }

            statement.setLong(++params, route.getDistance());

            statement.setInt(++params, id);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteRoute(int id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT + "\"id\"=" + id + ";")) {
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
