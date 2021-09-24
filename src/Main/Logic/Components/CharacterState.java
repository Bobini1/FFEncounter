package Main.Logic.Components;

import Main.Logic.Actions.ActionCommand;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.HashSet;
import java.util.Set;

public class CharacterState implements Cloneable{
    float initialHealth;
    float health;
    float defense;
    float strength;

    Set<StatusEffect> activeEffects;

    public CharacterState(float health, float defense, float strength)
    {
        this.health = health;
        this.initialHealth = health;
        this.defense = defense;
        this.strength = strength;
        this.activeEffects = new HashSet<>();
    }

    public float getHealth()
    {
        return health;
    }

    public float getInitialHealth()
    {
        return initialHealth;
    }

    public float getDefense()
    {
        return defense;
    }

    public float getStrength()
    {
        return strength;
    }

    public Set<StatusEffect> getActiveEffects() {
        return activeEffects;
    }

    public void addStatusEffect(StatusEffect effect)
    {
        activeEffects.add(effect);
    }

    public void giveAction(ActionCommand action) {
        float damage = action.getDamage();
        if (action.getDamage() < 0)
        {
            damage = Float.max(action.getDamage() - defense, 0f);
        }

        health = health - damage - action.getTrueDamage();
        if (health > initialHealth)
        {
            health = initialHealth;
        }
    }

    @Override
    public CharacterState clone() {
        try {
            CharacterState clone = (CharacterState) super.clone();
            clone.health = health;
            clone.initialHealth = initialHealth;
            clone.defense = defense;
            clone.strength = strength;
            clone.activeEffects = new HashSet<>();
            for (StatusEffect effect : activeEffects)
            {
                clone.activeEffects.add(effect.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
