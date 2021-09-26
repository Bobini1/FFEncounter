package Main.Logic.Characters.Builders;

import Main.Control.UI;
import Main.Engine.Drawing.Sprites.AnimatedSprite;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Engine.Instance;
import Main.Logic.Characters.PlayerCharacter;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;

import java.util.ArrayList;
import java.util.List;

public class PlayerCharacterBuilder implements GameCharacterBuilder {
    CharacterState state;
    List<AttackMethod> attackMethods;
    AnimatedSprite sprite;
    Instance instance;
    UI controllingUI;

    @Override
    public void setState(CharacterState state) {
        this.state = state;
    }

    @Override
    public void setAttackMethods(List<AttackMethod> attackMethods) {
        this.attackMethods = attackMethods;
    }

    @Override
    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    public PlayerCharacterBuilder(UI controllingUI, Instance instance) {
        this.controllingUI = controllingUI;
        this.instance = instance;
    }

    public PlayerCharacter getResult() {
        if (state == null || attackMethods == null || controllingUI == null || sprite == null)
            throw new RuntimeException("Character field uninitialized in builder");

        return new PlayerCharacter(state, attackMethods, controllingUI, instance, sprite);
    }
}
