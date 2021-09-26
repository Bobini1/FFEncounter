package Main.Logic.Actions;

import Main.Logic.Components.CharacterState;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActionCommand {
    float damage;
    float trueDamage;
    Set<StatusEffect> effects;

    public ActionCommand(float damage, float trueDamage, Set<StatusEffect> effects) {
        this.damage = damage;
        this.trueDamage = trueDamage;
        this.effects = new HashSet<>();
        for (StatusEffect effect : effects) {
            this.effects.add(effect.clone());
        }
    }

    public CharacterState state(CharacterState state) {
        return state;
    }

    public float getDamage() {
        return damage;
    }

    public float getTrueDamage() {
        return trueDamage;
    }

    public Set<StatusEffect> getEffects() {
        return effects;
    }
}
