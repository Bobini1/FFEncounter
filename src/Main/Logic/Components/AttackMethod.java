package Main.Logic.Components;

import Main.Logic.StatusEffects.StatusEffect;

import java.util.List;

public class AttackMethod implements Cloneable {
    private double energy;
    float damage;
    float trueDamage;
    String name;
    List<StatusEffect> effects;

    public AttackMethod(float damage, float trueDamage, List<StatusEffect> effects, double energy, String name) {
        this.damage = damage;
        this.trueDamage = trueDamage;
        this.effects = effects;
        this.name = name;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    public float getTrueDamage() {
        return trueDamage;
    }

    public List<StatusEffect> getEffects() {
        return effects;
    }

    public double getEnergy() {
        return energy;
    }

    @Override
    public AttackMethod clone() {
        try {
            AttackMethod clone = (AttackMethod) super.clone();
            clone.damage = damage;
            clone.trueDamage = trueDamage;
            clone.effects = effects;
            clone.name = name;
            clone.energy = energy;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
