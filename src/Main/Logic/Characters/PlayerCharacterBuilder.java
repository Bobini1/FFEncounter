package Main.Logic.Characters;

import Main.Control.UI;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;

import java.util.ArrayList;
import java.util.List;

public class PlayerCharacterBuilder {
    CharacterState state;
    List<AttackMethod> attackMethods;
    Sprite sprite;

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
    UI controllingUI;

    public PlayerCharacterBuilder(UI controllingUI) {
        this.controllingUI = controllingUI;
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
        return new PlayerCharacter(state.clone(), copiedAttackMethods, controllingUI, sprite.clone());
    }
}
