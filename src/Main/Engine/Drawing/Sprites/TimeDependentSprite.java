package Main.Engine.Drawing.Sprites;

import Main.Engine.TimeDependent;

public interface TimeDependentSprite extends TimeDependent, Sprite {
    TimeDependentSprite clone();
}
