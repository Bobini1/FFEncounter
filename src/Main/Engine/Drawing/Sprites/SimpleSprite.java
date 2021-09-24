package Main.Engine.Drawing.Sprites;

import Main.Logic.Components.Position;
import javafx.scene.image.Image;

public class SimpleSprite implements Sprite {

    private final Image image;
    private final int z;
    private Position position;
    private double scale;

    public SimpleSprite(Image image, Position position, int z, double scale)
    {
        this.image = image;
        this.position = position;
        this.z = z;
        this.scale = scale;
    }

    public SimpleSprite(Image image, Position position, int z)
    {
        this(image, position, z, 1D);
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Position getPosition()
    {
        return position;
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

    @Override
    public SimpleSprite clone() {
        return new SimpleSprite(image, position.clone(), z, scale);
    }
}
