package Main.Engine.Drawing.Sprites;

import Main.Logic.Components.Position;
import javafx.scene.image.Image;

public interface Sprite extends Cloneable {
    Image getImage();

    Position getPosition();

    int getZ();

    double getWidth();

    double getHeight();

    Sprite clone();
}
