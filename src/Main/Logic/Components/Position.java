package Main.Logic.Components;

public class Position implements Cloneable {
    private final double x;
    private final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Position clone() {
        return new Position(x, y);
    }
}
