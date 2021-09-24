package Main.Logic.Components;

import Main.Logic.StatusEffects.StatusEffect;

import java.util.List;

public class AttackMethod {
    private final double energy;
    final float damage;
    final float trueDamage;
    final String name;
    final List<StatusEffect> effects;

    public AttackMethod(float damage, float trueDamage, List<StatusEffect> effects, double energy, String name)
    {
        this.damage = damage;
        this.trueDamage = trueDamage;
        this.effects = effects;
        this.name = name;
        this.energy = energy;
    }

    public String getName()
    {
        return name;
    }

    public float getDamage()
    {
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

    public double getEnergy()
    {
        return energy;
    }
}
