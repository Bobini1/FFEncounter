package Main.Engine;

import Main.Engine.Drawing.Drawable;

public interface Actor extends TimeDependent, Drawable {
    double getPositionX();

    double getPositionY();

    double getWidth();

    double getHeight();

    boolean isRemoved();
}
