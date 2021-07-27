package fr.charlito33.fadeengine.sdk.math;

public class Vector2i {
    private int x;
    private int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2i vec) {
        this.x += vec.x;
        this.y += vec.y;
    }

    public void sub(Vector2i vec) {
        this.x -= vec.x;
        this.y -= vec.y;
    }

    public void mul(Vector2i vec) {
        this.x *= vec.x;
        this.y *= vec.y;
    }

    public void div(Vector2i vec) {
        this.x /= vec.x;
        this.y /= vec.y;
    }

    //Static
    public static Vector2i get(int x, int y) {
        return new Vector2i(x, y);
    }
}
