package Main.Logic.Actions;
import Main.Logic.Components.CharacterState;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.List;

public class ActionCommand
{
    float damage;
    float trueDamage;
    List<StatusEffect> effects;

    public ActionCommand(float damage, float trueDamage, List<StatusEffect> effects)
    {
        this.damage = damage;
        this.trueDamage = trueDamage;
        this.effects = effects;
    }

    public CharacterState state(CharacterState state)
    {
        return state;
    }

    public float getDamage() {
        return damage;
    }

    public float getTrueDamage()
    {
        return trueDamage;
    }

    public List<StatusEffect> getEffects()
    {
        return effects;
    }
}
