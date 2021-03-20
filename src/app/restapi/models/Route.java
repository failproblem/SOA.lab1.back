package app.restapi.models;

import java.util.Date;

public class Route {

    private int id;                     // > 0, auto generated, unique
    private String name;                // not null, not empty
    private Coordinates coordinates;    // not null
    private Date creationDate;          // not null, generated
    private Location from;              // not null
    private Location to;                // not null
    private long distance;              // > 1

    private static final long MIN_DISTANCE_VAL = 2;

    public Route(int id, String name, Coordinates coordinates, Date creationDate, Location from, Location to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.id = id;
        this.creationDate = creationDate;

        fullValidate();
    }

    public void preInsertValidate() {
        this.creationDate = this.creationDate == null ? new Date() : this.creationDate;

        validate();
    }

    public void preUpdateValidate() {
        if (id < 1) {
            throw new IllegalArgumentException("ID should not be less than 1");
        }

        validate();
    }

    public void fullValidate() {
        if (id < 1) {
            throw new IllegalArgumentException("ID should not be less than 1");
        }

        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date is null");
        }

        validate();
    }

    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name should not be empty");
        }

        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates are null");
        }
        coordinates.validate();

        if (from != null) {
            from.validate();
        }

        if (to != null) {
            to.validate();
        }

        if (distance < MIN_DISTANCE_VAL) {
            throw new IllegalArgumentException("Distance should not be less than " + MIN_DISTANCE_VAL);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public long getDistance() {
        return distance;
    }

    public void setId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID should not be less than 1");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name should not be empty");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates object is null");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date is null");
        }
        this.creationDate = creationDate;
    }

    public void setFrom(Location from) {
        if (from == null) {
            throw new IllegalArgumentException("Location is null");
        }
        this.from = from;
    }

    public void setTo(Location to) {
        if (to == null) {
            throw new IllegalArgumentException("Location is null");
        }
        this.to = to;
    }

    public void setDistance(long distance) {
        if (distance < MIN_DISTANCE_VAL) {
            throw new IllegalArgumentException("Distance should not be less than " + MIN_DISTANCE_VAL);
        }
        this.distance = distance;
    }

    public static long calculateDistance(Location from, Location to) {
        long x1 = from.getX();
        long x2 = to.getX();

        long y1 = from.getY();
        long y2 = to.getY();

        long z1 = from.getZ();
        long z2 = to.getZ();

        return (long) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }
}
