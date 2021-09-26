package Main.Logic.Characters.Builders;

import Main.Control.UI;
import Main.Engine.Drawing.Sprites.AnimatedSprite;
import Main.Engine.Instance;
import Main.Logic.Characters.EnemyCharacter;
import Main.Logic.Characters.PlayerCharacter;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;

import java.util.List;

public class EnemyCharacterBuilder implements GameCharacterBuilder {
    CharacterState state;
    List<AttackMethod> attackMethods;
    AnimatedSprite sprite;
    Instance instance;

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

    public EnemyCharacterBuilder(Instance instance) {
        this.instance = instance;
    }

    public EnemyCharacter getResult() {
        if (state == null || attackMethods == null || sprite == null)
            throw new RuntimeException("Character field uninitialized in builder");

        return new EnemyCharacter(state, attackMethods, instance, sprite);
    }
}
