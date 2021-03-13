package app.restapi.models;

public class Coordinates {

    private float x; // not null
    private float y; // not null, max value: 342

    private static final float MAX_Y_VAL = 342.0f;

    public Coordinates(float x, float y) {
        this.x = x;

        if (y > MAX_Y_VAL) {
            throw new IllegalArgumentException("Y should be less or equal to " + MAX_Y_VAL);
        }
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        if (y > MAX_Y_VAL) {
            throw new IllegalArgumentException("Y should be less or equal to " + MAX_Y_VAL);
        }
        this.y = y;
    }
}
