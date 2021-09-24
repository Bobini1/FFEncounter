package Main.Engine.Drawing.Sprites;

import javafx.scene.image.Image;

public interface Sprite {
    Image getImage();
    double getX();
    double getY();
    int getZ();
    double getWidth();
    double getHeight();
}
