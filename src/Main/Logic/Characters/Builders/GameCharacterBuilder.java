package Main.Logic.Characters.Builders;

import Main.Engine.Drawing.Sprites.AnimatedSprite;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;

import java.util.List;

public interface GameCharacterBuilder {
    void setState(CharacterState state);

    void setAttackMethods(List<AttackMethod> attackMethods);

    void setSprite(AnimatedSprite sprite);
}
