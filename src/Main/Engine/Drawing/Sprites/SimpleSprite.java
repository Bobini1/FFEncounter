package Main.Engine.Drawing.Sprites;

import javafx.scene.image.Image;

public class SimpleSprite implements Sprite {

    private final Image image;
    private int z;
    private double x;
    private double y;
    private double scale;

    public SimpleSprite(Image image, double x, double y, int z, float scale)
    {
        this.image = image;
        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
        this.x = x;
        this.y = y;
        this.z = z;
        this.scale = scale;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public double getWidth() {
        return image.getWidth() * scale;
    }

    @Override
    public double getHeight() {
        return image.getHeight() * scale;
    }
}
