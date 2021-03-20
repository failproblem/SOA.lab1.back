package app.restapi.models;

public class Location {

    private long x;         // not null
    private long y;         // not null
    private long z;         // not null
    private String name;    // len <= 561, not null

    private static final int MAX_NAME_LEN = 561;

    public Location(long x, long y, long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.name = name;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void validate() {
        if (this.name == null || this.name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name should not be empty");
        }

        if (this.name.length() > MAX_NAME_LEN) {
            throw new IllegalArgumentException("Name length should not be more than 561");
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}
