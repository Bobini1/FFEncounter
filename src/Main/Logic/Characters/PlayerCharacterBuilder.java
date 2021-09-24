package Main.Logic.Characters;

import Main.Control.UI;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Engine.Instance;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;

import java.util.ArrayList;
import java.util.List;

public class PlayerCharacterBuilder {
    CharacterState state;
    List<AttackMethod> attackMethods;
    Sprite sprite;
    Instance instance;
    UI controllingUI;

    public CharacterState getState() {
        return state;
    }

    public List<AttackMethod> getAttackMethods() {
        return attackMethods;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public void setAttackMethods(List<AttackMethod> attackMethods) {
        this.attackMethods = attackMethods;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public PlayerCharacterBuilder(UI controllingUI, Instance instance) {
        this.controllingUI = controllingUI;
        this.instance = instance;
    }

    public PlayerCharacter getResult()
    {
        if (state == null || attackMethods == null || controllingUI == null || sprite == null)
            throw new RuntimeException("Character field uninitialized in builder");

        List<AttackMethod> copiedAttackMethods = new ArrayList<>();
        for (AttackMethod method : attackMethods)
        {
            copiedAttackMethods.add(method.clone());
        }
        return new PlayerCharacter(state.clone(), copiedAttackMethods, controllingUI, instance, sprite.clone());
    }
}
