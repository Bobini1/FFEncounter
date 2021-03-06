package Main.Logic.Characters;

import Main.Engine.Actor;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Components.AttackDealer;
import Main.Logic.Components.AttackTaker;

public interface GameCharacter extends AttackDealer, AttackTaker {
    Sprite getSprite();
    double getEnergy();
    double getMaxEnergy();
}
